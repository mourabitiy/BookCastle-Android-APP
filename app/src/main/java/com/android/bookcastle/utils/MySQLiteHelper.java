package com.android.bookcastle.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_BOOKS = "books";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image_path";
    public static final String COLUMN_AUTHOR = "author";
    public static final String COLUMN_CATEGORY = "category";
    private static final String DATABASE_NAME = "books.db";
    private static final int DATABASE_VERSION = 1;
    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_BOOKS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " text not null, " + COLUMN_DESCRIPTION
            + " text not null, " + COLUMN_IMAGE
            + " text not null, " + COLUMN_AUTHOR
            + " text not null, " + COLUMN_CATEGORY
            + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }
}