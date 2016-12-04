package com.example.thedevelopmentbuild.vergerss.Model;

import android.content.ContentValues;

import com.example.thedevelopmentbuild.vergerss.Database.MapLocationsTable;

import java.util.UUID;

/**
 * Created by kiera on 03/12/2016.
 */

public class DataItem {

    private String itemId;
    private String itemName;
    private String itemAddress;
    private double itemLat;
    private double itemLong;

    public DataItem(){

    }
    public DataItem(String id, String name, String address, double latitude, double longitude){
        if(id==null){
            id=UUID.randomUUID().toString();
        }
        itemId=id;
        itemName=name;
        itemAddress = address;
        itemLat=latitude;
        itemLong=longitude;
    }


    public String getItemAddress() {
        return itemAddress;
    }

    public double getItemLat() {
        return itemLat;
    }

    public void setItemLat(double itemLat) {
        this.itemLat = itemLat;
    }

    public double getItemLong() {
        return itemLong;
    }

    public void setItemLong(double itemLong) {
        this.itemLong = itemLong;
    }

    public void setItemAddress(String itemAddress) {

        this.itemAddress = itemAddress;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ContentValues toValues(){
        ContentValues values = new ContentValues(5);

        values.put(MapLocationsTable.COLUMN_ID,itemId);
        values.put(MapLocationsTable.COLUMN_NAME,itemName);
        values.put(MapLocationsTable.COLUMN_ADDRESS,itemAddress);
        values.put(MapLocationsTable.COLUMN_LAT,itemLat);
        values.put(MapLocationsTable.COLUMN_LONG,itemLong);

        return values;
    }


    @Override
    public String toString() {
        return "DataItem{" +
                "itemAddress='" + itemAddress + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemLat=" + itemLat +
                ", itemLong=" + itemLong +
                '}';
    }
}
