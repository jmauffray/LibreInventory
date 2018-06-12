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
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.libreinventory.libreinventory.db.InventoryItemDAO;
import com.libreinventory.libreinventory.model.InventoryItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExportInventory extends AsyncTask<String, String, String> {

    final static String CSV_SEP = ",";

    Context mContext;
    protected ProgressDialog dialog;
    protected File mFile;

    public ExportInventory(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(mContext);
        dialog.setTitle("Export de l'inventaire en cours");
        dialog.setMessage("Patience...");
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        InventoryItemDAO dao = new InventoryItemDAO(mContext);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<InventoryItem> inventories = dao.getInventory();

        String filename = new SimpleDateFormat("'inventory_'yyyy-MM-dd_hh-mm-ss'.csv'").format(new Date());
        mFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename);
        try {
            FileOutputStream stream = new FileOutputStream(mFile);

            //write header
            stream.write("reference".getBytes());
            stream.write(CSV_SEP.getBytes());

            stream.write("quantite totale".getBytes());
            stream.write(CSV_SEP.getBytes());

            stream.write("quantite vente".getBytes());
            stream.write(CSV_SEP.getBytes());

            stream.write("localisation".getBytes());
            stream.write(CSV_SEP.getBytes());

            stream.write("commentaire".getBytes());
            stream.write(CSV_SEP.getBytes());

            stream.write("etiquettes".getBytes());

            stream.write("\n".getBytes());

            //write data
            for (InventoryItem i : inventories) {

                stream.write(String.valueOf(i.getArticleId()).getBytes());
                stream.write(CSV_SEP.getBytes());

                stream.write(String.valueOf(i.getQuantite()).getBytes());
                stream.write(CSV_SEP.getBytes());

                stream.write(String.valueOf(i.getQuantiteVente()).getBytes());
                stream.write(CSV_SEP.getBytes());

                stream.write(i.getLocalisation().getBytes());
                stream.write(CSV_SEP.getBytes());

                stream.write(i.getComment().getBytes());
                stream.write(CSV_SEP.getBytes());

                stream.write(String.valueOf(i.getEtiquetteNb()).getBytes());

                stream.write("\n".getBytes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.valueOf(inventories.size());
    }

    protected void onPostExecute(String data) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (data.length() != 0) {
            Toast.makeText(mContext, "Téléchargement de l'inventaire terminé!" + "\n" + data + " produits inventoriés.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Téléchargement de l'inventaire échoué", Toast.LENGTH_SHORT).show();
        }
    }

}