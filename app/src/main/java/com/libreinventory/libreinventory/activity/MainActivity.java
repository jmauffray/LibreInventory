package com.libreinventory.libreinventory.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.libreinventory.libreinventory.worker.ExportInventory;
import com.libreinventory.libreinventory.worker.ImportProduct;
import com.libreinventory.libreinventory.R;
import com.libreinventory.libreinventory.db.InventoryItemDAO;
import com.libreinventory.libreinventory.worker.SendInventory;

import java.sql.SQLException;


public class MainActivity extends Activity {

    private View mView;

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

        mView = view;
        new AlertDialog.Builder(this)
                .setTitle("Suppresion de l'inventaire")
                .setMessage("Voulez vous vraiment supprimer l'inventaire ?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        int nb = delInventoryImpl(MainActivity.this.mView);
                        Toast.makeText(MainActivity.this, "Inventaire supprimé, " + String.valueOf(nb) + " articles inventoriés.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    private int delInventoryImpl(View view) {
        InventoryItemDAO dao = new InventoryItemDAO(view.getContext());
        try {
            dao.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao.delInventory();
    }

    public void exportInventory(View view) {

        ExportInventory ip = new ExportInventory(view.getContext());
        ip.execute();
    }

    public void sendInventory(View view) {

        SendInventory ip = new SendInventory(view.getContext(), this);
        ip.execute();
    }

    public void importProduct(View view) {

        ImportProduct ip = new ImportProduct(view.getContext());
        ip.execute();
    }

}
