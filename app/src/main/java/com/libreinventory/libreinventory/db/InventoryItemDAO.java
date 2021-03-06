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

package com.libreinventory.libreinventory.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.libreinventory.libreinventory.model.InventoryItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemDAO {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_ARTICLE_ID,
            MySQLiteHelper.COLUMN_QUANTITE,
            MySQLiteHelper.COLUMN_QUANTITE_VENTE,
            MySQLiteHelper.COLUMN_LOCALISATION,
            MySQLiteHelper.COLUMN_COMMENT,
            MySQLiteHelper.COLUMN_ETIQUETTE_NB
    };

    public InventoryItemDAO(Context context) {

        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {

        database = dbHelper.getWritableDatabase();
    }

    public void close() {

        dbHelper.close();
    }

    public void createVente(InventoryItem aVente) {

        ContentValues values = getContentValues(aVente);
        database.insert(MySQLiteHelper.TABLE_INVENTORY, null, values);
    }

    public List<InventoryItem> getInventory() {

        List<InventoryItem> comments = new ArrayList<InventoryItem>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_INVENTORY,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            InventoryItem aVente = cursorToItemVente(cursor);
            comments.add(aVente);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return comments;
    }

    public int delInventory() {

        return database.delete(MySQLiteHelper.TABLE_INVENTORY, "", new String[]{});
    }

    public ContentValues getContentValues(InventoryItem aVente) {

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_ARTICLE_ID, aVente.getArticleId());
        values.put(MySQLiteHelper.COLUMN_QUANTITE, aVente.getQuantite());
        values.put(MySQLiteHelper.COLUMN_QUANTITE_VENTE, aVente.getQuantiteVente());
        values.put(MySQLiteHelper.COLUMN_LOCALISATION, aVente.getLocalisation());
        values.put(MySQLiteHelper.COLUMN_COMMENT, aVente.getComment());
        values.put(MySQLiteHelper.COLUMN_ETIQUETTE_NB, aVente.getEtiquetteNb());

        return values;
    }

    private InventoryItem cursorToItemVente(Cursor cursor) {
        InventoryItem aVente = new InventoryItem();

        //aVente.setId((int)cursor.getLong(0));
        aVente.setArticleId((int) cursor.getLong(1));
        aVente.setQuantite(cursor.getInt(2));
        aVente.setQuantiteVente(cursor.getInt(3));
        aVente.setLocalisation(cursor.getString(4));
        aVente.setComment(cursor.getString(5));
        aVente.setEtiquetteNb(cursor.getInt(6));

        return aVente;
    }

}