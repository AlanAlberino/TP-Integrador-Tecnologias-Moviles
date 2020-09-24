package com.iua.alanalberino;

public class Movie {

    private String title;
    private String imageURL;

    public Movie(String titulo, String imageURL){
        this.title=titulo;
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
