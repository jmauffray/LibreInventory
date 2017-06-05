package com.libreinventory.libreinventory.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jm on 31/05/2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_INVENTORY = "table_inventory";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ARTICLE_ID = "article_id";
    public static final String COLUMN_QUANTITE = "quantite";
    public static final String COLUMN_LOCALISATION = "localisation";

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String TABLE_INVENTORY_CREATE = "create table "
            + TABLE_INVENTORY + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_ARTICLE_ID + " integer not null,"
            + COLUMN_QUANTITE + " integer not null,"
            + COLUMN_LOCALISATION + " string not null"
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