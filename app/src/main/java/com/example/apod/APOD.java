package com.example.apod;

import android.graphics.Bitmap;

public class APOD {

    private static APOD instance;

    private String title;
    private String copyright;
    private String description;
    private String mediaType;
    private Bitmap image;

    private String rawResponse;

    private APOD() {}

    public static APOD getInstance() {
        if (instance == null) {
            instance = new APOD();
        }
        return instance;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public String getRawResponse() {
        return this.rawResponse;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaType() {
        return this.mediaType;
    }
}
