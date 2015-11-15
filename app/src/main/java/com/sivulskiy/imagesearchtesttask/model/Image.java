package com.sivulskiy.imagesearchtesttask.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Sivulskiy Sergey
 */
public class Image {

    @SerializedName("id")
    private long mID;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("latitude")
    private String mLatitude;

    @SerializedName("longitude")
    private String mLongitude;

    @SerializedName("url_o")
    private String mUrlOriginal;

    @SerializedName("url_q")
    private String mUrlThumbnail;

    public Image(String mTitle, String mLatitude, String mLongitude, String mUrlOriginal, String mUrlThumbnail) {
        this.mTitle = mTitle;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mUrlOriginal = mUrlOriginal;
        this.mUrlThumbnail = mUrlThumbnail;
    }

    public long getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public String getUrlOriginal() {
        return mUrlOriginal;
    }

    public String getUrlThumbnail() {
        return mUrlThumbnail;
    }
}
