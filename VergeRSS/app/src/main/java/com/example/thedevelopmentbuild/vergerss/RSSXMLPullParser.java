package com.example.thedevelopmentbuild.vergerss;

import android.content.Context;
import android.text.Html;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kieran Brawley. Matric No:S1433740
 */

public class RSSXMLPullParser {

    static final String KEY_PUBLICATION_DATE="published";
    static final String KEY_TITLE="title";
    static final String KEY_DESCRIPTION="content";
    static final String KEY_IMAGE="img";
    static final String KEY_LINK="link";
    static final String KEY_AUTHOR="author";

    public static List<RSSItem>getRSSItemsFromFile(XmlPullParser vergeParser){
        List<RSSItem> result= new ArrayList<RSSItem>();
        int event;
        String text=null;
        String textChar;

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
                            RSSItem temp = new RSSItem("", "","","", "","");
                            result.add(temp);
                            result.get(counter).setTitle(text);
                        }
                        else if (name.equals(KEY_DESCRIPTION)) {
                            result.get(counter).setDescription(Html.fromHtml(text).toString());
                        }
                        else if (name.equals(KEY_IMAGE)) {
                            result.get(counter).setDescription(Html.fromHtml(text).toString());
                        }
                        else if (name.equals(KEY_LINK))
                        {
                            result.get(counter).setLink(text);
                        }
                        break;
                }

                event = vergeParser.next();
            }
        }
        catch(Exception e){
            Log.e("Results", "Couldn't do xmlParsing " + e);
        }

        return result;
    }


}
