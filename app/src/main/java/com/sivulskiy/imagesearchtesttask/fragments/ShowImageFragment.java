package com.sivulskiy.imagesearchtesttask.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sivulskiy.imagesearchtesttask.R;
import com.sivulskiy.imagesearchtesttask.adapters.GridImageAdapter;
import com.sivulskiy.imagesearchtesttask.api.ImageApi;
import com.sivulskiy.imagesearchtesttask.loaders.ImageDbSync;
import com.sivulskiy.imagesearchtesttask.model.Images;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ShowImageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Void> {

    private static final String LOG_TAG = ShowImageFragment.class.getSimpleName();

    public final static String SHOW_IMAGES_TAG = "fragment_show_images";

    private static final String SEARCH_TEXT = "search_text";
    private static final String SEARCH_ID = "search_id";
    private static final String SYNC_TYPE = "sync_type";
    private static final int SYNC_LOADER_ID = 0;

    private GridView mImageGridView;
    private String mSearchText;
    private Images mImages;
    private long mSearchId;
    private boolean isOldSync;


    public static ShowImageFragment newInstance(String searchText, long searchId) {
        ShowImageFragment fragment = new ShowImageFragment();
        Bundle args = new Bundle();
        args.putString(SEARCH_TEXT, searchText);
        args.putLong(SEARCH_ID, searchId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mImages = new Images();
        if (getArguments() != null) {
            mSearchText = getArguments().getString(SEARCH_TEXT);
            mSearchId = getArguments().getLong(SEARCH_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_image, container, false);
        mImageGridView = (GridView) view.findViewById(R.id.gridView);
        ImageApi.getService().getImageList(mSearchText, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObjects, Response response) {
                Log.d(LOG_TAG, "load new page success");
                mImages = new Gson().fromJson(jsonObjects.getAsJsonObject().get("photos"), Images.class);
                Bundle bundle = new Bundle();
                bundle.putInt(SYNC_TYPE, ImageDbSync.SYNC_NEW_IMAGE);
                mImageGridView.setAdapter(new GridImageAdapter(mImages, getContext()));
                syncDb(bundle);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(LOG_TAG, "load new page failure ");
                Toast.makeText(getActivity(), "No internet connection!", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt(SYNC_TYPE, ImageDbSync.SYNC_OLD_IMAGE);
                isOldSync = true;
                syncDb(bundle);

            }
        });
        return view;
    }

    private void syncDb(Bundle bundle) {
        if (getLoaderManager().getLoader(SYNC_LOADER_ID) == null)
            getLoaderManager().initLoader(SYNC_LOADER_ID, bundle, this).forceLoad();
        else
            getLoaderManager().restartLoader(SYNC_LOADER_ID, bundle, this).forceLoad();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new ImageDbSync(getContext(), mImages, mSearchId, args.getInt(SYNC_TYPE));
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        Log.d(LOG_TAG, "sync finished");
        if (isOldSync)
            mImageGridView.setAdapter(new GridImageAdapter(mImages, getContext()));
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }
}
