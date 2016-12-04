package com.example.thedevelopmentbuild.vergerss.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kiera on 03/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME ="bean.db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MapLocationsTable.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MapLocationsTable.SQL_DELETE);
        onCreate(db);
    }

}
