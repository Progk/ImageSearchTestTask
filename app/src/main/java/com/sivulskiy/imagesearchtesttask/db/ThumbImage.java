package com.sivulskiy.imagesearchtesttask.db;

import android.provider.BaseColumns;

/**
 * @author Sivulskiy Sergey
 */
public class ThumbImage implements BaseColumns {

    public static final String TABLE_NAME = "THUMB_IMAGE";
    public static final String SEARCH_ID = "SEARCH_ID";
    public static final String THUMB_URL = "THUMB_URL";
    public static final String ORIGINAL_URL = "ORIGINAL_URL";
    public static final String IMAGE_TITLE = "IMAGE_TITLE";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SEARCH_ID + " TEXT NOT NULL, " +
                    THUMB_URL + " TEXT NOT NULL, " +
                    ORIGINAL_URL + " TEXT, " +
                    IMAGE_TITLE + " TEXT, " +
                    "UNIQUE(" + SEARCH_ID + "," + THUMB_URL + "));";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static final String SQL_SELECT_IMAGES =
            "SELECT *" +
                    " FROM " + TABLE_NAME +
                    " WHERE " + SEARCH_ID + " = ?;";

    public ThumbImage() {

    }
}
