package com.sivulskiy.imagesearchtesttask.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.sivulskiy.imagesearchtesttask.R;
import com.sivulskiy.imagesearchtesttask.model.Image;
import com.sivulskiy.imagesearchtesttask.model.Images;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Sivulskiy Sergey
 */
public class GridImageAdapter extends BaseAdapter {

    private Images mImages;
    private Context mContext;
    private Target mTarget;


    public GridImageAdapter(Images mImages, Context mContext) {
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mImages.getmPhotoList().size();
    }

    @Override
    public Object getItem(int position) {
        return mImages.getmPhotoList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Image image = mImages.getmPhotoList().get(position);

        final GridItemViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.fragment_grid_item, parent, false);
            viewHolder = new GridItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GridItemViewHolder) convertView.getTag();
        }

        viewHolder.getTextView().setText(image.getTitle());
        viewHolder.getCheckBox().setChecked(false);

        /*Picasso.with(mContext)
                .setIndicatorsEnabled(true);*/
        Picasso.with(mContext)
                .load(mImages.getmPhotoList().get(position).getUrlThumbnail())
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_placeholder)
                .into(viewHolder.getImageView());

        return convertView;
    }


    private static class GridItemViewHolder {
        private ImageView mImageView;
        private CheckBox mCheckBox;
        private TextView mTextView;

        public GridItemViewHolder(View rootView) {
            mImageView = (ImageView) rootView.findViewById(R.id.grid_image_view);
            mCheckBox = (CheckBox) rootView.findViewById(R.id.grid_checkbox);
            mTextView = (TextView) rootView.findViewById(R.id.grid_text);
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public CheckBox getCheckBox() {
            return mCheckBox;
        }

        public TextView getTextView() {
            return mTextView;
        }
    }
}
