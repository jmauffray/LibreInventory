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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_INVENTORY = "table_inventory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ARTICLE_ID = "article_id";
    public static final String COLUMN_QUANTITE = "quantite";
    public static final String COLUMN_QUANTITE_VENTE = "quantite_vente";
    public static final String COLUMN_LOCALISATION = "localisation";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_ETIQUETTE_NB = "etiquette_nb";

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 3;

    // Database creation sql statement
    private static final String TABLE_INVENTORY_CREATE = "create table "
            + TABLE_INVENTORY + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_ARTICLE_ID + " integer not null,"
            + COLUMN_QUANTITE + " integer not null,"
            + COLUMN_QUANTITE_VENTE + " integer not null,"
            + COLUMN_LOCALISATION + " string not null,"
            + COLUMN_COMMENT + " string not null,"
            + COLUMN_ETIQUETTE_NB + " integer not null"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(TABLE_INVENTORY_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);

        onCreate(db);
    }

}