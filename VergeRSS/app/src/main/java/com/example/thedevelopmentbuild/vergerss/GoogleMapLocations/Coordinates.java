package com.example.thedevelopmentbuild.vergerss.GoogleMapLocations;

import com.example.thedevelopmentbuild.vergerss.Model.DataItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Sample data set used to populate the google map view*/
public class Coordinates {

    public static List<DataItem> dataItemList;

    static {
        dataItemList = new ArrayList<>();

        addItem(new DataItem(null, "Coffee Chocolate and Tea", "944 Argyle St, Glasgow G3 8YJ",
                55.863258,-4.279226));
        addItem(new DataItem(null, "Artisan Roast", "15-17 Gibson St, Glasgow G12 8NU",
                55.872412,-4.282285));
        addItem(new DataItem(null, "Bocadillo Coffee Shop & Bistro", "569, Sauchiehall St, " +
                "Glasgow G3 7PQ", 55.866010,-4.273508));
        addItem(new DataItem(null, "Jelly Hill Cafe", "195 Hyndland Rd, Glasgow G12 9HT",
                55.875025,-4.304213));
        addItem(new DataItem(null, "Papercup Coffee Company", "603 Great Western Rd, Glasgow G12 " +
                "8HX",55.876448,-4.285229));
        addItem(new DataItem(null, "Coffee Republic", "Cineworld Cinema: 7 Renfrew St, Glasgow G2" +
                " 3AB",55.876448, -4.285229));
        addItem(new DataItem(null, "Starbucks", "136-140 Buchanan St, Glasgow G1 2JA",
                55.861433,-4.253535));
        addItem(new DataItem(null, "Costa", "8-10 Royal Exchange Square, City Centre, Glasgow G1 " +
                "3AB",51.513610,-0.087579));
        addItem(new DataItem(null, "Starbucks", "Glasgow Central Station, 1 Gordon St, Glasgow G1" +
                " 3SL",55.859196,-4.258185));
        addItem(new DataItem(null, "Gordon Street Coffee", "Glasgow Central Station, 79 Gordon " +
                "St, Glasgow G1 3SQ",55.860625,-4.258538));
        addItem(new DataItem(null, "AMT Coffee", "Glasgow Central Station, Glasgow G1 3SL",
                55.859112,-4.258109));

    }

    public static void addItem(DataItem item){
        dataItemList.add(item);
    }

}
