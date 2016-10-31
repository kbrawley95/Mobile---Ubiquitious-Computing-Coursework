package com.example.thedevelopmentbuild.vergerss;

/**
 * Created by kieran on 26/10/2016.
 */

public class RSSItem {

    private String publicationDate;
    private String title;
    private String description;
    private String image;
    private String link;
    private String author;

    public RSSItem(String publicationDate, String title, String description, String image, String
            link, String author){

        this.publicationDate=publicationDate;
        this.title=title;
        this.description=description;
        this.image=image;
        this.link=link;
        this.author=author;
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

    @Override
    public String toString() {
        return "VergeArticle [name=" + title + ", description=" +description+ ", link="
                + link + ", publication date="  + publicationDate + "]";
    }

}
