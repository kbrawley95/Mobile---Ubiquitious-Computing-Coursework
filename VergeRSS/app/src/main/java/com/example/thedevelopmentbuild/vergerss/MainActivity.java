package com.example.thedevelopmentbuild.vergerss;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String ITEM_KEY="item_key";

    public static ArrayList<RSSItem> rssItemsArray;
    public static RSSAdapter rssAdapter;
    private ListView rssItemsList;


    private String rssUrl="http://www.theverge.com/2016/11/1/13484656/verge-5th-anniversary-relaunch-2016";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String path="https://youtu.be/YoV4HbMFAZI";

        Log.i("RSSFeed", "starting download Task");
        DownloadFile();

        //Get Reference to Listview
        rssItemsList=(ListView)findViewById(R.id.rssList);

        //Set click listener to launch the browser when a row is clicked.
        rssItemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RSSItem rssItem = rssAdapter.getItem(position);
                Intent intent= new Intent(MainActivity.this, ArticleActivity.class);
                intent.putExtra(ITEM_KEY, rssItem);
                startActivity(intent);
            }
        });
    }

    private void DownloadFile() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    rssItemsArray = (ArrayList) RSSDownloader.DownloadFromURL("http://www.theverge.com/rss/index.xml");
                    Log.i("Results", rssItemsArray.size() + "");

                    if (rssItemsArray.size() > 0) {
                        String[] stuff = new String[rssItemsArray.size()];
                        for (int i = 0; i < stuff.length; i++) {
                            stuff[i]=rssItemsArray.get(i).getPublicationDate();
                            stuff[i] = rssItemsArray.get(i).getTitle();
                            stuff[i] = rssItemsArray.get(i).getImage();
                            stuff[i]=rssItemsArray.get(i).getDescription();
                            stuff[i]=rssItemsArray.get(i).getLink();
                        }
                        rssAdapter=new RSSAdapter(MainActivity.this, rssItemsArray);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                                rssItemsList = (ListView) findViewById(R.id.rssList);
                                rssItemsList.setAdapter(rssAdapter);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Results", "ERROR" + e);
                }
            }
        });
        thread.start();
    }
}

