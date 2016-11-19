package com.example.thedevelopmentbuild.vergerss;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import static com.example.thedevelopmentbuild.vergerss.MainActivity.ITEM_KEY;
import static com.example.thedevelopmentbuild.vergerss.MainActivity.rssAdapter;
import static com.example.thedevelopmentbuild.vergerss.MainActivity.rssItemsArray;


public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_main);

        RSSItem rssItem= getIntent().getExtras().getParcelable(ITEM_KEY);


        TextView articleTitle = (TextView)findViewById(R.id.articleTitle);
        TextView articleDate = (TextView)findViewById(R.id.articlePubDate);
        TextView articleDescription = (TextView)findViewById(R.id.articleDescription);
        TextView articleLink = (TextView)findViewById(R.id.articleLink);
        TextView articleAuthor = (TextView)findViewById(R.id.articleAuthor);

        if (rssItem != null) {
            new DownloadImageTask((ImageView)findViewById(R.id.articleImage))
                    .execute(rssItem.getImage());
            articleTitle.setText(rssItem.getTitle());
            articleDescription.setText(rssItem.getDescription());

            articleLink.setText("Continue Reading");
            articleLink.setOnClickListener(this);

            articleAuthor.setText(rssItem.getAuthor());
            articleDate.setText(rssItem.getPublicationDate());
        }else{
            Toast.makeText(this, "Didn't Received any data", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {

        RSSItem rssItem= getIntent().getExtras().getParcelable(ITEM_KEY);

//        String url="";
//        for(RSSItem r: rssItemsArray){
//            url=r.getLink();
//            Log.i("Keys",url);
//        }

        Intent intent =new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(rssItem.getLink()));
        startActivity(intent);

    }
}
