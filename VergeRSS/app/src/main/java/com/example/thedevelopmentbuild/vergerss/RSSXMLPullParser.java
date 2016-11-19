package com.example.thedevelopmentbuild.vergerss;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Kieran Brawley. Matric No:S1433740
 */

public class RSSXMLPullParser {


    static final String KEY_PUBLICATION_DATE="published";
    static final String KEY_TITLE="title";
   // static final String KEY_DESCRIPTION="content";
    static final String KEY_IMAGE="content";
    static final String KEY_LINK="link";
    static final String KEY_AUTHOR="name";
    private static final String myTag="Results";

    public static List<RSSItem>getRSSItemsFromFile(XmlPullParser vergeParser){
        List<RSSItem> result= new ArrayList<>();
        int event;
        String text=null;
        String textChar;

        //Tag for Log statements

        try
        {
            event = vergeParser.getEventType();
            int counter=0;
            while(event!=XmlPullParser.END_DOCUMENT)
            {

                String name = vergeParser.getName();
                switch(event)
                {
                    case XmlPullParser.TEXT:
                        text = vergeParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (name.equals(KEY_PUBLICATION_DATE))
                        {
                            result.get(counter).setPublicationDate(text);
                            counter++;
                        }
                        else if (name.equals(KEY_TITLE))
                        {
                            RSSItem temp = new RSSItem("", "","","","","","");
                            result.add(temp);
                            result.get(counter).setTitle(text);
                        }
                        else if (name.equals(KEY_IMAGE)) {

                            //Full Text
                            Log.i("Info", text);

                            String[] parts=text.split("src=\"");
                            String[]finalParts=parts[1].split("\" />");

                            String finalContents=finalParts[0];
                            Log.i("Stuff","Img:" + finalContents);

                            String description[]=finalParts[1].split("Continue reading");
                            Log.i("Description","\nDescription:" + description[0]);


                            result.get(counter).setImage(finalContents);

                            versionBasedEscapedHTML(result, KEY_IMAGE,description[0],counter);
                        }
                        else if (name.equals(KEY_AUTHOR))
                        {
                            versionBasedEscapedHTML(result, KEY_AUTHOR,text,counter);
                        }
                        else if (name.equals(KEY_LINK))
                        {
                            String relType=vergeParser.getAttributeValue(null,"rel");
                            if(relType.equals("alternate")){

                                versionBasedEscapedHTML(result,KEY_LINK,vergeParser
                                        .getAttributeValue(null,"href"),counter);
                            }
                        }
                        break;
                }

                event = vergeParser.next();
            }
        }
        catch(Exception e){
            Log.e(myTag, "Couldn't do xmlParsing " + e);
        }

        Log.i(myTag,result.toString());
        return result;
    }


    public static List<RSSItem> versionBasedEscapedHTML(List<RSSItem> result,String htmlTag, String
            htmlText, int counter){

        switch(htmlTag)
        {
            case KEY_PUBLICATION_DATE:

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result.get(counter).setPublicationDate(Html.fromHtml(htmlText,Html.FROM_HTML_MODE_COMPACT)
                            .toString
                            ());
                } else {
                    result.get(counter).setPublicationDate(Html.fromHtml(htmlText).toString());
                }
                break;

            case KEY_TITLE:

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result.get(counter).setTitle(Html.fromHtml(htmlText,Html.FROM_HTML_MODE_COMPACT)
                            .toString
                                    ());
                } else {
                    result.get(counter).setTitle(Html.fromHtml(htmlText).toString());
                }
                break;

            case KEY_IMAGE:

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result.get(counter).setDescription(Html.fromHtml(htmlText,Html.FROM_HTML_MODE_COMPACT)
                            .toString
                                    ());
                } else {
                    result.get(counter).setDescription(Html.fromHtml(htmlText).toString());
                }
                break;

            case KEY_LINK:

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result.get(counter).setLink(htmlText);
                } else {
                    result.get(counter).setLink(htmlText);
                }
                break;

            case KEY_AUTHOR:

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    result.get(counter).setAuthor(Html.fromHtml("By " + htmlText,Html
                            .FROM_HTML_MODE_COMPACT)
                            .toString
                                    ());
                } else {
                    result.get(counter).setAuthor(Html.fromHtml("By " + htmlText).toString());
                }
                break;


        }

        return result;
    }






}
