package com.nicootech.nytmaster.models;

import android.os.Parcel;
import android.os.Parcelable;

import static com.nicootech.nytmaster.utils.Constants.IMAGE_PREFIX;

public class MultiMedia implements Parcelable
{

    private String url;
    private String subtype;

    public MultiMedia(String url, String subtype) {
        this.url = url;
        this.subtype = subtype;
    }

    public MultiMedia() {
    }

    public String getUrl() {
        return IMAGE_PREFIX+url;
    }
    public void setUrl(String url) {
        this.url = url;
    }


    public String getSubtype() {
        return subtype;
    }
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    protected MultiMedia(Parcel in) {
        url = in.readString();
        subtype = in.readString();
    }

    public static final Creator<MultiMedia> CREATOR = new Creator<MultiMedia>() {
        @Override
        public MultiMedia createFromParcel(Parcel in) {
            return new MultiMedia(in);
        }

        @Override
        public MultiMedia[] newArray(int size) {
            return new MultiMedia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(subtype);
    }

}

