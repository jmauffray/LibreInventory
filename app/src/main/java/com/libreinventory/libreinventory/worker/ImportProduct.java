package com.libreinventory.libreinventory.worker;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Pair;
import android.widget.Toast;

import com.libreinventory.libreinventory.config.Config;
import com.libreinventory.libreinventory.model.Article;
import com.libreinventory.libreinventory.util.CsvUtil;

import java.io.File;
import java.util.List;

public class ImportProduct extends AsyncTask<String, String, Pair<Boolean, String>> {

    final static String CSV_FILENAME = "products.csv";

    Context mContext;
    private ProgressDialog mDialog;

    public ImportProduct(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(mContext);
        mDialog.setTitle("Import des produits en cours");
        mDialog.setMessage("Patience...");
        mDialog.setCancelable(false);
        mDialog.setIcon(android.R.drawable.ic_dialog_info);
        mDialog.show();
    }

    @Override
    protected Pair<Boolean, String> doInBackground(String... params) {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + CSV_FILENAME);
        if (!file.exists()) {
            return Pair.create(false, "Fichier non trouvé:" + file.getAbsolutePath());
        }

        List<Article> articles = CsvUtil.parseCsvArticles(file.getPath());
        if (articles.isEmpty()) {
            return Pair.create(false, "0 produit importé du fichier:" + file.getAbsolutePath());
        }

        //update Config articles and cache
        Config.articles = articles;
        for (Article article : Config.articles) {
            Config.articlesCache.add(
                    new Pair<Article, String>(article, article.toString().toLowerCase()));
        }

        return Pair.create(true, String.valueOf(articles.size()));
    }

    protected void onPostExecute(Pair<Boolean, String> data) {

        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }

        if (data.first) {
            Toast.makeText(mContext, "Import des produits terminé!" + "\n" + data.second + " produits importés",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Import des produits échoué" + "\n" + data.second,
                    Toast.LENGTH_LONG).show();
        }
    }

}
