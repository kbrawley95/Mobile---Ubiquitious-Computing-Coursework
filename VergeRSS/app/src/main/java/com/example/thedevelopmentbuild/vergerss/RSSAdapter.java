package com.example.thedevelopmentbuild.vergerss;

import android.content.Context;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;




/**
 * Created by Kieran Brawley. Matric No:S1433740
 */

public class RSSAdapter extends ArrayAdapter<RSSItem> {

    ImageLoader imageLoader;
    DisplayImageOptions options;


    public RSSAdapter(Context context, ArrayList<RSSItem> results){
        super(context,0,results);

        //Setup for the ImageLoader that'll be used to display images.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        //Setup options for ImageLoader for cache handling
        options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row=(RelativeLayout)convertView;

        //Check if an existing view is being reused, otherwise inflate the view
        if(row==null){
            LayoutInflater inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=(RelativeLayout)inflater.inflate(R.layout.article_main, null);
        }

        //Lookup view for data population
        TextView articleTitle = (TextView)row.findViewById(R.id.articleTitleText);
        final ImageView articleImage= (ImageView) row.findViewById(R.id.articleImage);
        TextView link = (TextView)row.findViewById(R.id.articleLink);
        TextView date = (TextView)row.findViewById(R.id.publicationDateText);



        //Setup a listener to switch from the loading indicator to the Image once it's ready
        ImageLoadingListener listener = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                articleImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };

        imageLoader.displayImage(getItem(pos).getImage(), articleImage, options, listener);

        //Populate the data in the template view using the data object.
        articleTitle.setText(getItem(pos).getTitle());
        link.setText(getItem(pos).getLink());
        date.setText(getItem(pos).getPublicationDate());

        return row;

    }

}
