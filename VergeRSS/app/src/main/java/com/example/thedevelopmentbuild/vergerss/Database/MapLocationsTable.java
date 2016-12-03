package com.example.thedevelopmentbuild.vergerss.Database;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kiera on 03/12/2016.
 */

public class MapLocationsTable {

    public static final  String TABLES_ITEMS="items";
    public static final String COLUMN_ID = "itemId";
    public static final String COLUMN_NAME="itemName";
    public static final String COLUMN_ADDRESS="itemAddress";
    public static final String COLUMN_LAT="itemLat";
    public static final String COLUMN_LONG="itemLong";


    public static final String[] ALL_COLUMNS=
            {COLUMN_ID, COLUMN_NAME, COLUMN_ADDRESS,COLUMN_LAT,COLUMN_LONG};

    public static final String SQL_CREATE=
            "CREATE TABLE " + TABLES_ITEMS + "(" +
                COLUMN_ID + " TEXT PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_ADDRESS + " TEXT," +
                COLUMN_LAT + " REAL," +
                COLUMN_LONG + " REAL" + ");";

    public static final String SQL_DELETE=
            "DROP TABLE " + TABLES_ITEMS;


}
