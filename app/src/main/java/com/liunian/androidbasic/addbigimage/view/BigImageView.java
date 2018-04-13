package com.liunian.androidbasic.addbigimage.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by dell on 2018/4/12.
 */

public class BigImageView extends View {
    private Context mContext;
    private BitmapRegionDecoder mBitmapRegionDecoder;
    private Rect mRect = new Rect();
    private int mImageWidth = 0;
    private int mImageHeight = 0;
    private BitmapFactory.Options mOptions;

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    public void setInputStream(InputStream inputStream) {
        try {
            mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;

            requestLayout();
            invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    float lastEventX = 0;
    float lastEventY = 0;
    float eventX = 0;
    float eventY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            eventX = event.getX();
            eventY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    move((int)(lastEventX - eventX), (int)(lastEventY - eventY));
                    break;
            }
            lastEventX = event.getX();
            lastEventY = event.getY();
        }
        return true;
    }

    private void move(int moveX, int moveY) {
        if (mImageHeight > getHeight()) {
            mRect.top = mRect.top + moveY;
            mRect.bottom = mRect.top + getHeight();
        }
        if (mImageWidth > getWidth()) {
            mRect.left = mRect.left + moveX;
            mRect.right = mRect.left + getWidth();
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapRegionDecoder != null) {
            checkHeight();
            checkWidth();
            Bitmap bitmap = mBitmapRegionDecoder.decodeRegion(mRect, mOptions);
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
        }
    }

    private void checkWidth() {
        if (mImageWidth > getWidth()) {
            if (mRect.right > mImageWidth) {
                mRect.right = mImageWidth;
                mRect.left = mRect.right - getWidth();
            }

            if (mRect.left < 0) {
                mRect.left = 0;
                mRect.right = getWidth();
            }
        } else {
            mRect.left = (mImageWidth - getWidth()) / 2;
            mRect.right = mRect.left + getWidth();
        }
    }

    private void checkHeight() {
        if (mImageHeight > getHeight()) {
            if (mRect.bottom > mImageHeight) {
                mRect.bottom = mImageHeight;
                mRect.top = mRect.bottom - getHeight();
            }
            if (mRect.top < 0) {
                mRect.top = 0;
                mRect.bottom = getHeight();
            }
        } else {
            mRect.top = (mImageHeight - getHeight()) / 2;
            mRect.bottom = mRect.top + getHeight();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 将图片的初始化位置设在中心
        int width = getMeasuredWidth();
        int height = getHeight();

        mRect.left = (mImageWidth - width) / 2;
        mRect.right = mRect.left + width;
        mRect.top = (mImageHeight - height) / 2;
        mRect.bottom = mRect.top + height;
    }
}
