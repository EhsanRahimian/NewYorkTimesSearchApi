package com.nicootech.nytmaster.models;


import org.parceler.Parcel;

import static com.nicootech.nytmaster.util.Constants.IMAGE_PREFIX;

@Parcel
public class ArticleMedia {


     String url;
    public String getUrl() {
        return IMAGE_PREFIX + url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}