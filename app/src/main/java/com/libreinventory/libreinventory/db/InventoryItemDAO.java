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

/**
 * Created by jm on 31/05/2017.
 */

public class InventoryItemDAO {

        // Database fields
        private SQLiteDatabase database;
        private MySQLiteHelper dbHelper;
        private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
                MySQLiteHelper.COLUMN_ARTICLE_ID,
                MySQLiteHelper.COLUMN_QUANTITE,
                MySQLiteHelper.COLUMN_LOCALISATION
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

        public void delInventory() {

            int num = database.delete(MySQLiteHelper.TABLE_INVENTORY, "", new String[]{});
            Log.e("delete", String.valueOf(num));

        }

        public ContentValues getContentValues(InventoryItem aVente) {
            ContentValues values = new ContentValues();

            values.put(MySQLiteHelper.COLUMN_ARTICLE_ID, aVente.getArticleId());
            values.put(MySQLiteHelper.COLUMN_QUANTITE, aVente.getQuantite());
            values.put(MySQLiteHelper.COLUMN_LOCALISATION, aVente.getLocalisation());

            return values;
        }

        private InventoryItem cursorToItemVente(Cursor cursor) {
            InventoryItem aVente = new InventoryItem();

            //aVente.setId((int)cursor.getLong(0));
            aVente.setArticleId((int)cursor.getLong(1));
            aVente.setQuantite(cursor.getInt(2));
            aVente.setLocalisation(cursor.getString(3));

            return aVente;
        }
    }