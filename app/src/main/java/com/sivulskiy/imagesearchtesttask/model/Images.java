package com.sivulskiy.imagesearchtesttask.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sivulskiy Sergey
 */
public class Images {

    @SerializedName("page")
    private int mPage;

    @SerializedName("pages")
    private int mPages;

    @SerializedName("perpage")
    private int mPerpage;

    @SerializedName("total")
    private int mTotal;

    @SerializedName("photo")
    private List<Image> mPhotoList;

    public Images() {
        mPhotoList = new ArrayList<>();
    }

    public int getmPage() {
        return mPage;
    }

    public int getmPages() {
        return mPages;
    }

    public int getmPerpage() {
        return mPerpage;
    }

    public int getmTotal() {
        return mTotal;
    }

    public List<Image> getmPhotoList() {
        return mPhotoList;
    }
}
