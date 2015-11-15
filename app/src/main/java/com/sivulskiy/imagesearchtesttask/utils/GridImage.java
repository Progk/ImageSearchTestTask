package com.sivulskiy.imagesearchtesttask.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author Sivulskiy Sergey
 */
public class GridImage extends ImageView {
    public GridImage(Context context) {
        super(context);
    }

    public GridImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GridImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
