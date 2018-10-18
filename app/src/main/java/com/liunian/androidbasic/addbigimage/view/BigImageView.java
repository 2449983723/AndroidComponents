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
        // 指定图片的解码格式为RGB_565
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
    }

    // 传入需要加载的图片的inputStream
    public void setInputStream(InputStream inputStream) {
        try {
            // 根据图片对应的BitmapRegionDecoder对象
            mBitmapRegionDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            // 获得图片的宽高
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true; // 将inJuestDecodeBounds设为true，这样BitmapFactory只会解析图片的原始宽/高信息，并不会真正的去加载图片
            BitmapFactory.decodeStream(inputStream, null, tmpOptions); // 解析图片获得图片的宽高
            mImageWidth = tmpOptions.outWidth; // 保存图片的宽
            mImageHeight = tmpOptions.outHeight; // 保存图片的搞

            requestLayout(); // 这里调用requestLayout方法，请求重新布局，触发调用控件的onMeasure，初始化加载区域
            invalidate(); // 调用invalidate方法，请求重绘
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 保存上一次事件的发生位置
    float lastEventX = 0;
    float lastEventY = 0;
    // 保存当前事件的发生位置
    float eventX = 0;
    float eventY = 0;

    // 重载控件的onTouchEvent方法，监控控件的事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 这里只处理了控件的Move事件，如果有更复杂需求，比如说根据手势缩放图片，可以将事件交由给GestureDetector处理
        if (event != null) {
            // 得到当前事件的发生位置
            eventX = event.getX();
            eventY = event.getY();
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: // move事件
                    // 根据当前事件的发生位置和上一次事件的发生位置计算出用户的滑动距离，并调整图片的加载区域
                    move((int) (lastEventX - eventX), (int) (lastEventY - eventY));
                    break;
            }
            // 保存上一次事件的发生位置
            lastEventX = event.getX();
            lastEventY = event.getY();
        }
        return true;
    }

    // 根据滑动距离调整图片的加载区域
    private void move(int moveX, int moveY) {
        // 只有当图片的高大于控件的高，控件纵向显示不下图片时，才需要调整加载区域的上下位置
        if (mImageHeight > getHeight()) {
            mRect.top = mRect.top + moveY;
            mRect.bottom = mRect.top + getHeight();
        }
        // 只有当图片的宽大于控件的宽，控件横向显示不下图片时，才需要调整加载区域的左右位置
        if (mImageWidth > getWidth()) {
            mRect.left = mRect.left + moveX;
            mRect.right = mRect.left + getWidth();
        }

        invalidate();
    }

    // 重写onDraw方法，绘制图片的局部
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmapRegionDecoder != null) {
            // 检查绘制区域的宽高，以免绘制到图片以为的区域
            checkHeight();
            checkWidth();
            // 加载图片的指定区域
            Bitmap bitmap = mBitmapRegionDecoder.decodeRegion(mRect, mOptions);
            // 绘制图片的局部到控件上
            if (bitmap != null) {
                canvas.drawBitmap(bitmap, 0, 0, null);
            }
        }
    }

    /**
     * 检查加载区域是否超出图片范围
     */
    private void checkWidth() {
        // 只有当图片的宽大于控件的宽，控件横向显示不下图片时，才需要调整加载区域的左右位置
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

    /**
     * 检查加载区域是否超出图片范围
     */
    private void checkHeight() {
        // 只有当图片的高大于控件的高，控件纵向显示不下图片时，才需要调整加载区域的上下位置
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

    /**
     * 重写测量控件大小的方法，初始化图片的加载区域
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 得到控件的宽高
        int width = getMeasuredWidth();
        int height = getHeight();

        // 初始化图片的加载区域为图片的中心，可以自行根据需求调整
        mRect.left = (mImageWidth - width) / 2;
        mRect.right = mRect.left + width;
        mRect.top = (mImageHeight - height) / 2;
        mRect.bottom = mRect.top + height;
    }
}
