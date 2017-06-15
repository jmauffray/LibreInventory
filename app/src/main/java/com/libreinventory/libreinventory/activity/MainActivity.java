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
