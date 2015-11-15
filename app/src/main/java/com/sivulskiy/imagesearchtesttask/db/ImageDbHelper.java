package com.sivulskiy.imagesearchtesttask.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sivulskiy.imagesearchtesttask.model.Image;
import com.sivulskiy.imagesearchtesttask.model.Images;

/**
 * @author Sivulskiy Sergey
 */
public class ImageDbHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = ImageDbHelper.class.getSimpleName();

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ThumbImage.db";

    private static volatile ImageDbHelper mInstance;
    private Context mContext;

    public static ImageDbHelper getInstance(Context context) {
        ImageDbHelper localInstance = mInstance;
        if (localInstance == null) {
            synchronized (ImageDbHelper.class) {
                localInstance = mInstance;
                if (localInstance == null) {
                    mInstance = localInstance = new ImageDbHelper(context);
                }
            }
        }
        return localInstance;
    }


    private ImageDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SearchText.SQL_CREATE_TABLE);
        db.execSQL(ThumbImage.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SearchText.SQL_DROP_TABLE);
        db.execSQL(ThumbImage.SQL_DROP_TABLE);
        onCreate(db);
    }


    /**
     * Return existing or new search Id
     *
     * @param searchText
     * @return
     */
    public long insertNewSearchText(String searchText) {
        Log.d(LOG_TAG, "addSearch");
        SQLiteDatabase db = null;
        long rowId = -1;
        try {
            db = getInstance(mContext).getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(SearchText.SEARCH_TEXT, searchText);
            rowId = db.insertOrThrow(SearchText.TABLE_NAME, null, contentValues);
            db.close();
        } catch (SQLException e) {
            Log.d(LOG_TAG, "text exist");
            db = getInstance(mContext).getReadableDatabase();
            Cursor cursor = db.rawQuery(SearchText.SQL_SELECT_TEXT, new String[]{searchText});
            if (cursor.moveToFirst()) {
                rowId = cursor.getInt(cursor.getColumnIndexOrThrow(SearchText._ID));
            }
            cursor.close();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        Log.d(LOG_TAG, "row_id = " + String.valueOf(rowId));
        return rowId;
    }

    public void readImageUrl(Images images, long rowID) {
        Log.d(LOG_TAG, "addNewImage");
        SQLiteDatabase db = null;
        try {
            db = getInstance(mContext).getReadableDatabase();
            Cursor cursor = db.rawQuery(ThumbImage.SQL_SELECT_IMAGES, new String[]{String.valueOf(rowID)});
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(ThumbImage.THUMB_URL));
                    String originalImageUrl = cursor.getString(cursor.getColumnIndexOrThrow(ThumbImage.ORIGINAL_URL));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(ThumbImage.IMAGE_TITLE));
                    images.getmPhotoList().add(new Image(title, null, null, originalImageUrl, imageUrl));
                    cursor.moveToNext();
                }
            }
        } catch (SQLException e) {
            Log.d(LOG_TAG, e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}
