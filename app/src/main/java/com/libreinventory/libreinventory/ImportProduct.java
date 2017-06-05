package com.libreinventory.libreinventory;

import java.io.File;
        import java.io.FileReader;
import java.util.List;

import android.Manifest;
import android.app.Activity;
        import android.app.ProgressDialog;
        import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.util.Pair;
import android.util.StringBuilderPrinter;
import android.widget.Toast;

import com.libreinventory.libreinventory.config.Config;
import com.libreinventory.libreinventory.model.Article;
import com.libreinventory.libreinventory.util.CsvUtil;
import com.opencsv.CSVReader;

public class ImportProduct extends AsyncTask<String, String, String> {

    Activity activity;
    Context context;
    File file=null;
    private ProgressDialog dialog;

    public ImportProduct(Context context, Activity activity,File file) {
        this.context=context;
        this.activity=activity;
        this.file=file;
    }

    @Override
    protected void onPreExecute()
    {
        dialog=new ProgressDialog(context);
        dialog.setTitle("Importing Data into SecureIt DataBase");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.csv");

        Log.e("article", file.getPath());

        StringBuilder sb = new StringBuilder();
        sb.append("id;name;code barre\n");
        //String csv = "id;name;code barre\n1;camelia;123456\n2;buis;1234567\n";
        for(int i = 0; i < 10000; ++i)
        {
            sb.append(i);
            sb.append(";camelia;123456\n");
        }
        String csv = sb.toString();
        CsvUtil.saveCsv(csv, file.getPath(), context);

        List<Article> articles = CsvUtil.parseCsvArticles(file.getPath());

        for(Article article: articles)
        {
            Log.e("article", article.toString());
        }


        Config.articles = articles;
        for (Article article : Config.articles) {
            Config.articlesCache.add(
                    new Pair<Article, String>(article, article.toString().toLowerCase()));
        }



        return String.valueOf(articles.size());
    }

    protected void onPostExecute(String data)
    {

        if (dialog.isShowing())
        {
            dialog.dismiss();
        }

        if (data.length()!=0)
        {
            Toast.makeText(context, "File is built Successfully!"+"\n"+data, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "File fail to build", Toast.LENGTH_SHORT).show();
        }
    }

}