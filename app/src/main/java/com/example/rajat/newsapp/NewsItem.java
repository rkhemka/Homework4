package com.example.rajat.newsapp;

/**
 * Created by rajat on 6/29/2017.
 */

public class NewsItem {

    private String title;
    private String author;
    private String description;
    private String published;
    private String imageUrl;



    public NewsItem(String title, String description, String imageUrl, String author, String published) {
        this.title = title;
        this.description = description;

        this.imageUrl = imageUrl;
        this.author=author;
        this.published=published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
