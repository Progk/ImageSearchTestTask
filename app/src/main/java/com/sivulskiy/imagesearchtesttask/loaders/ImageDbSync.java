package com.sivulskiy.imagesearchtesttask.loaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.sivulskiy.imagesearchtesttask.db.ImageDbHelper;
import com.sivulskiy.imagesearchtesttask.db.ThumbImage;
import com.sivulskiy.imagesearchtesttask.model.Image;
import com.sivulskiy.imagesearchtesttask.model.Images;

/**
 * @author Sivulskiy Sergey
 */
public class ImageDbSync extends AsyncTaskLoader<Void> {

    public static final int SYNC_NEW_IMAGE = 0;
    public static final int SYNC_OLD_IMAGE = 1;

    private static final String LOG_TAG = ImageDbSync.class.getSimpleName();

    private Images mImages;
    private long mSearchId;
    private Context mContext;
    private int mType;


    public ImageDbSync(Context context, Images images, long searchId, int type) {
        super(context);
        mContext = context;
        mImages = images;
        mSearchId = searchId;
        mType = type;
    }

    @Override
    public Void loadInBackground() {
        Log.d(LOG_TAG, "addNewImages");
        SQLiteDatabase db = null;
        if (mType == SYNC_NEW_IMAGE) {
            db = ImageDbHelper.getInstance(mContext).getWritableDatabase();
            try {
                for (Image image : mImages.getmPhotoList()) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(ThumbImage.SEARCH_ID, mSearchId);
                    contentValues.put(ThumbImage.THUMB_URL, image.getUrlThumbnail());
                    contentValues.put(ThumbImage.ORIGINAL_URL, image.getUrlOriginal());
                    contentValues.put(ThumbImage.IMAGE_TITLE, image.getTitle());
                    db.insertOrThrow(ThumbImage.TABLE_NAME, null, contentValues);
                }
            } catch (SQLException e) {
                Log.d(LOG_TAG, "image exist");
            } finally {
                if (db != null) {
                    db.close();
                }
            }
        } else {
            ImageDbHelper.getInstance(mContext).readImageUrl(mImages, mSearchId);
        }

        return null;
    }
}
