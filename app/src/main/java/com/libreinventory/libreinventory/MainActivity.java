package com.libreinventory.libreinventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.util.Log;

import com.libreinventory.libreinventory.db.InventoryItemDAO;
import com.libreinventory.libreinventory.model.Article;
import com.libreinventory.libreinventory.model.InventoryItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Some text here");

        Log.e("write---------------", "fsdghgfsdjhgfsdhg");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void writeInventory(View view) {
        Log.e("write", "a");

        InventoryItemDAO dao = new InventoryItemDAO(view.getContext());
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        InventoryItem i = new InventoryItem();
        i.setArticleId(1);
        i.setQuantite(10);
        i.setLocalisation("a4");
        dao.createVente(i);
        dao.createVente(i);

        //run activity
        Intent intent = new Intent(this, InventoryActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

        // Kabloey
    }

    public void delInventory(View view) {
        Log.e("del", "b");
        InventoryItemDAO dao = new InventoryItemDAO(view.getContext());
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.delInventory();
        // Kabloey
    }

    public void exportInventory(View view) {
        Log.e("export", "c");
        // Kabloey

        InventoryItemDAO dao = new InventoryItemDAO(view.getContext());
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<InventoryItem> ii = dao.getInventory();

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/inventory.csv");
        try {
            FileOutputStream stream = new FileOutputStream(file);

            for(InventoryItem i: ii)
            {
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

    }

    public void importProduct(View view) {
        Log.e("import", "d");

        Context context = view.getContext();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.csv");

        ImportProduct ip = new ImportProduct(context, this, file);
        ip.execute();
    }

}
