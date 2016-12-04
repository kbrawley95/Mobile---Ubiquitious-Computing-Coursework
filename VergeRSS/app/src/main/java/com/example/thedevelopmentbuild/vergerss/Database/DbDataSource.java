package com.example.thedevelopmentbuild.vergerss.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.thedevelopmentbuild.vergerss.Model.DataItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kiera on 03/12/2016.
 */

public class DbDataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;


    public DbDataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        mDatabase=mDbHelper.getWritableDatabase();
    }

    public void open(){
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close(){
        mDbHelper.close();
    }

    public DataItem createItem(DataItem item){
        ContentValues values = item.toValues();
        mDatabase.insert(MapLocationsTable.TABLES_ITEMS, null,values);
        return item;
    }

    public Long getDataItemsCount(){
        return DatabaseUtils.queryNumEntries(mDatabase, MapLocationsTable.TABLES_ITEMS);
    }

    public void seedDatabase(List<DataItem> dataItemList){
        long numItems=getDataItemsCount();

        if(numItems==0) {
            for (DataItem item : dataItemList) {
                try {
                    createItem(item);

                } catch (SQLiteException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(mContext, "Data Inserted!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(mContext, "Data Already Inserted!", Toast.LENGTH_SHORT).show();
        }
    }

    public List<DataItem> getAllItems(){
        List<DataItem>dataItems = new ArrayList<>();

        Cursor cursor =mDatabase.query(MapLocationsTable.TABLES_ITEMS,
                MapLocationsTable.ALL_COLUMNS, null, null, null, null, null);

        while(cursor.moveToNext()){

            DataItem dataItem = new DataItem();
            dataItem.setItemId(cursor.getString(cursor.getColumnIndex(MapLocationsTable
                    .COLUMN_ID)));
            dataItem.setItemName(cursor.getString(cursor.getColumnIndex(MapLocationsTable
                    .COLUMN_NAME)));
            dataItem.setItemAddress(cursor.getString(cursor.getColumnIndex(MapLocationsTable
                    .COLUMN_ADDRESS)));
            dataItem.setItemLat(cursor.getDouble(cursor.getColumnIndex(MapLocationsTable
                    .COLUMN_LAT)));
            dataItem.setItemLong(cursor.getDouble(cursor.getColumnIndex(MapLocationsTable
                    .COLUMN_LONG)));

            dataItems.add(dataItem);
        }

        return dataItems;
    }


}
