package com.example.thedevelopmentbuild.vergerss;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
        TextView author =(TextView)row.findViewById(R.id.articleAuthor);
        TextView date = (TextView)row.findViewById(R.id.publicationDateText);

        //Populate View
        articleTitle.setText(getItem(pos).getTitle());
        new DownloadImageTask((ImageView) row.findViewById(R.id.articleImage))
                .execute(getItem(pos).getImage());
        author.setText(getItem(pos).getAuthor());
        date.setText(getItem(pos).getPublicationDate());
        //row.setBackgroundColor(getContext().getResources().getColor(R.color.background));

        //Alter Initial View Image
        if(pos==0){

            articleTitle.setText("");
            new DownloadImageTask((ImageView) row.findViewById(R.id.articleImage))
                    .execute("https://s3.amazonaws.com/static.oculus.com/website/2014/07/verge_news_logo.png");
            date.setText("© The Verge, 2016. All Rights Reserved");

        }


        return row;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }



}
