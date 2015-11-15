package com.sivulskiy.imagesearchtesttask.db;

import android.provider.BaseColumns;

/**
 * @author Sivulskiy Sergey
 */
public class SearchText implements BaseColumns {

    public static final String TABLE_NAME = "SEARCH_TABLE";
    public static final String SEARCH_TEXT = "SEARCH_TEXT";

    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SEARCH_TEXT + " TEXT NOT NULL, " +
                    "UNIQUE(" + SEARCH_TEXT + "));";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String SQL_SELECT_TEXT =
                    "SELECT *" +
                    " FROM " + SearchText.TABLE_NAME +
                    " WHERE " + SearchText.SEARCH_TEXT + " = ?;";

    public SearchText() {

    }
}
