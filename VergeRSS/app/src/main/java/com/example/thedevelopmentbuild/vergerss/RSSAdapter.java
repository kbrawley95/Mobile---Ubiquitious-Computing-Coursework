package com.example.thedevelopmentbuild.vergerss;

import android.content.Context;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



public class RSSAdapter extends ArrayAdapter<RSSItem> {

    /*Constructor */
    public RSSAdapter(Context context, ArrayList<RSSItem> results){
        super(context,0,results);

    }

    /*Inflater/Generates the listview of Articles for the MainActivity layout*/
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row=(RelativeLayout)convertView;

        //Check if an existing view is being reused, otherwise inflate the view
        if(row==null){
            LayoutInflater inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=(RelativeLayout)inflater.inflate(R.layout.item_main, null);
        }

        //Lookup view for data population
        TextView articleTitle = (TextView)row.findViewById(R.id.itemTitle);
        TextView articleDate = (TextView)row.findViewById(R.id.itemDate);
        final ImageView articleImage= (ImageView) row.findViewById(R.id.itemImage);
//        TextView author =(TextView)row.findViewById(R.id.articleAuthor);
//        TextView date = (TextView)row.findViewById(R.id.publicationDateText);

        //Populate View
        articleTitle.setText(getItem(pos).getTitle());
        new DownloadImageTask((ImageView) row.findViewById(R.id.itemImage))
                .execute(getItem(pos).getImage());
//        author.setText(getItem(pos).getAuthor());
        articleDate.setText(getItem(pos).getPublicationDate());
        articleDate.setTextColor(getContext().getResources().getColor(R.color.colorPrimary));
        //row.setBackgroundColor(getContext().getResources().getColor(R.color.background));

        //Alter Initial View Image
        if(pos==0){

            articleTitle.setText("");
            new DownloadImageTask((ImageView) row.findViewById(R.id.itemImage))
                    .execute("https://s3.amazonaws.com/static.oculus.com/website/2014/07/verge_news_logo.png");
//            date.setText("© The Verge, 2016. All Rights Reserved");

        }


        return row;

    }





}

