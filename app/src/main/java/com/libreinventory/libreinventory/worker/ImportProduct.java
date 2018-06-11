/*
 * Libre Inventory is a simple inventory android application
 *
 * Copyright (c) 2017 "Jean-Marie Auffray,"
 *
 * This file is part of Libre Inventory.
 *
 * Libre Inventory is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.libreinventory.libreinventory.worker;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Pair;
import android.widget.Toast;

import com.libreinventory.libreinventory.config.Config;
import com.libreinventory.libreinventory.model.Article;
import com.libreinventory.libreinventory.util.CsvUtil;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class ImportProduct extends AsyncTask<String, String, Pair<Boolean, String>> {

    Context mContext;
    private ProgressDialog mDialog;
    private ContentResolver mContentResolver;
    private Uri mUri;

    public ImportProduct(Context context, ContentResolver contentResolver, Uri uri) {
        this.mContext = context;
        this.mContentResolver = contentResolver;
        this.mUri = uri;
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

        InputStream is = null;
        try {
            is = mContentResolver.openInputStream(mUri);
        } catch (FileNotFoundException e) {
            return Pair.create(false, "Erreur pour ouvrir le fichier" );
        }

        List<Article> articles = CsvUtil.parseCsvArticles(is);
        if (articles.isEmpty()) {
            return Pair.create(false, "0 produit importé" );
        }

        //update Config articles and cache
        Config.articles = articles;
        //clear cache
        Config.articlesCache.clear();
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
