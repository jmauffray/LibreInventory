package com.libreinventory.libreinventory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.libreinventory.libreinventory.db.InventoryItemDAO;

import java.io.File;
import java.sql.SQLException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void writeInventory(View view) {

        //run activity
        Intent intent = new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    public void delInventory(View view) {

        InventoryItemDAO dao = new InventoryItemDAO(view.getContext());
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.delInventory();
    }

    public void exportInventory(View view) {

        Context context = view.getContext();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.csv");

        ExportInventory ip = new ExportInventory(context);
        ip.execute();
    }

    public void importProduct(View view) {

        Context context = view.getContext();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.csv");

        ImportProduct ip = new ImportProduct(context);
        ip.execute();
    }

}
