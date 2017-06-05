package com.libreinventory.libreinventory;

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

    Context mContext;
    private ProgressDialog dialog;

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
        Log.e("export", "c");

        InventoryItemDAO dao = new InventoryItemDAO(mContext);
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<InventoryItem> ii = dao.getInventory();

        String filename = new SimpleDateFormat("'inventory_'yyyy-MM-dd_hh-mm-ss'.csv'").format(new Date());
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + filename);
        try {
            FileOutputStream stream = new FileOutputStream(file);

            for (InventoryItem i : ii) {
                String id = String.valueOf(i.getArticleId());
                String q = String.valueOf(i.getQuantite());

                stream.write(id.getBytes());
                stream.write(";".getBytes());
                stream.write(q.getBytes());
                stream.write(";".getBytes());
                stream.write(i.getLocalisation().getBytes());
                stream.write("\n".getBytes());
                Log.e("inv", i.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return String.valueOf(ii.size());
    }

    protected void onPostExecute(String data) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (data.length() != 0) {
            Toast.makeText(mContext, "Export de l'inventaire terminé!" + "\n" + data + " produits inventoriés.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Export de l'inventaire échoué", Toast.LENGTH_SHORT).show();
        }
    }

}