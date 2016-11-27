package com.example.thedevelopmentbuild.vergerss;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/*Class that represents an article extracted from the target RSS Feed*/

public class RSSItem implements Parcelable {

    private String publicationDate;
    private String title;
    private String description;
    private String image;
    private String link;
    private String author;
    private String itemId;

    public RSSItem(String publicationDate, String title, String description, String image, String
            link, String author, String itemId){

        if(itemId==null){
            itemId= UUID.randomUUID().toString();
        }

        this.publicationDate=publicationDate;
        this.title=title;
        this.description=description;
        this.image=image;
        this.link=link;
        this.author=author;
        this.itemId=itemId;
    }


    /*
                            PROPERTIES(GETTERS & SETTERS)
    ================================================================================
     */
    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getItemId(){
        return itemId;
    }

    @Override
    public String toString() {
        return "VergeArticle [name=" + title + ", description=" +description +  ", link="
                + link + ", image=" + image + ", publication date="  + publicationDate + "]";
    }

      /*
                            Parcelable Plugin Constructor/Methods
    ================================================================================
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.publicationDate);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeString(this.link);
        dest.writeString(this.author);
        dest.writeString(this.itemId);
    }

    protected RSSItem(Parcel in) {
        this.publicationDate = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.link = in.readString();
        this.author = in.readString();
        this.itemId = in.readString();
    }

    public static final Parcelable.Creator<RSSItem> CREATOR = new Parcelable.Creator<RSSItem>() {
        @Override
        public RSSItem createFromParcel(Parcel source) {
            return new RSSItem(source);
        }

        @Override
        public RSSItem[] newArray(int size) {
            return new RSSItem[size];
        }
    };
}
