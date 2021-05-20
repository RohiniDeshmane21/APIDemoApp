package com.example.apidemoapp;

import java.io.Serializable;

public class BookNewsInfo implements Serializable {

    private String AuthorOrPublisher;
    private String Title;
    private String Descr;
    private String UrlToImage;
    private String PublishedDate;

    public BookNewsInfo(String author, String title, String descr, String urlToImage, String publishedAt)
    {
        AuthorOrPublisher = author;
        Title=title;
        Descr=descr;
        UrlToImage=urlToImage;
        PublishedDate=publishedAt;

    }

    public String getAuthor() {
        return AuthorOrPublisher;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescr() {
        return Descr;
    }

    public String getUrlToImage() {
        return UrlToImage;
    }

    public String getPublishedAt() {
        return PublishedDate;
    }
}
