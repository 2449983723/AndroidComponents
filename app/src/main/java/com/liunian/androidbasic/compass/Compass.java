package com.liunian.androidbasic.compass;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.liunian.androidbasic.R;

import java.text.DecimalFormat;

public class Compass extends View {
    private static final DecimalFormat mDirectionFormat = new DecimalFormat("##0°");
    private static final String[] mDirectionStringArray = new String[4];
    private static final String[] mNumberDirectionStringArray = new String[4];
    private int mHalfWidth;
    private float mDirection;
    private String mDirectionString;
    private String mDirectionDetialString;
    private Paint h;
    private Paint i;
    private Paint j;
    private Paint k;
    private Paint l;
    private Drawable mBoundaryDrawable;
    private String mDueWestString;
    private String mDueEastString;
    private String mDueNorthString;
    private String mDueSouthString;
    private String mNorthEastString;
    private String mNorthWestString;
    private String mSouthEestString;
    private String mSouthWestString;
    private Drawable mReferenceDrawable;
    private int w;
    private int mMarginTop;
    private TextPaint x;

    public Compass(Context context) {
        this(context, null);
    }

    public Compass(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Compass(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDirectionString = "";
        this.mDirectionDetialString = "";
        init();
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        onSizeChanged();
    }

    private void init() {
        Resources resources = getResources();
        mDirectionStringArray[0] = resources.getString(R.string.direction_north);
        mDirectionStringArray[1] = resources.getString(R.string.direction_east);
        mDirectionStringArray[2] = resources.getString(R.string.direction_south);
        mDirectionStringArray[3] = resources.getString(R.string.direction_west);
        for (int i = 0; i < 4; i++) {
            mNumberDirectionStringArray[i] = " " + (i * 90) + "°";
        }
        this.mDueWestString = resources.getString(R.string.direction_due_west);
        this.mDueEastString = resources.getString(R.string.direction_due_east);
        this.mDueNorthString = resources.getString(R.string.direction_due_north);
        this.mDueSouthString = resources.getString(R.string.direction_due_south);
        this.mNorthEastString = resources.getString(R.string.direction_north_east);
        this.mNorthWestString = resources.getString(R.string.direction_north_west);
        this.mSouthEestString = resources.getString(R.string.direction_south_east);
        this.mSouthWestString = resources.getString(R.string.direction_south_west);
    }

    private void onSizeChanged() {
        this.mHalfWidth = getWidth() / 2;
        this.h = new Paint();
        this.h.setTextSize(c(28.0f));
        this.h.setAntiAlias(true);
        this.h.setColor(-1);
        this.h.setTypeface(Typeface.create("sans-serif-medium", 0));
        this.i = new Paint();
        this.i.setTextSize(c(14.0f));
        this.i.setAntiAlias(true);
        this.i.setColor(0x80FFFFFF);
        this.j = new Paint();
        this.j.setTextSize(c(18.0f));
        this.j.setAntiAlias(true);
        this.k = new Paint();
        this.k.setTextSize(c(16.0f));
        this.k.setAntiAlias(true);
        this.k.setColor(16777215);
        this.l = new Paint();
        this.x = new TextPaint();
        this.x.setARGB(76, 255, 255, 255);
        this.x.setAntiAlias(true);
        this.x.setTextSize(c(12.0f));
        mBoundaryDrawable = getResources().getDrawable(R.mipmap.compass_boundary); // 最外层的图片
        mBoundaryDrawable.setBounds(0, 0, mBoundaryDrawable.getIntrinsicWidth(), mBoundaryDrawable.getIntrinsicHeight());
        mReferenceDrawable = getResources().getDrawable(R.mipmap.compass_reference);
        mReferenceDrawable.setBounds(0, 0, mReferenceDrawable.getIntrinsicWidth(), mReferenceDrawable.getIntrinsicHeight());
        this.w = getResources().getDimensionPixelOffset(R.dimen.compass_content_margin_top) + (mBoundaryDrawable.getIntrinsicHeight() / 2);
        this.mMarginTop = getResources().getDimensionPixelOffset(R.dimen.compass_content_margin_top);
    }

    public void updateDirection(float direction) {
        this.mDirection = direction;
        this.mDirectionString = mDirectionFormat.format((double) this.mDirection);
        updateDirectionDetialString(direction);
        postInvalidate();
    }

    private void updateDirectionDetialString(float f) {
        if (f >= 355.0f || f < 5.0f) {
            this.mDirectionDetialString = this.mDueNorthString;
        } else if (f >= 5.0f && f < 85.0f) {
            this.mDirectionDetialString = this.mNorthEastString;
        } else if (f >= 85.0f && f <= 95.0f) {
            this.mDirectionDetialString = this.mDueEastString;
        } else if (f >= 95.0f && f < 175.0f) {
            this.mDirectionDetialString = this.mSouthEestString;
        } else if (f >= 175.0f && f <= 185.0f) {
            this.mDirectionDetialString = this.mDueSouthString;
        } else if (f >= 185.0f && f < 265.0f) {
            this.mDirectionDetialString = this.mSouthWestString;
        } else if (f >= 265.0f && f < 275.0f) {
            this.mDirectionDetialString = this.mDueWestString;
        } else if (f >= 275.0f && f < 355.0f) {
            this.mDirectionDetialString = this.mNorthWestString;
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制最外面的边界，是一个Drawable，这里注意利用translate和rotate函数来进行位移和旋转
        canvas.save();
        canvas.translate(this.mHalfWidth - mBoundaryDrawable.getIntrinsicWidth() / 2, this.mMarginTop);
        canvas.rotate(-this.mDirection, (float) mBoundaryDrawable.getIntrinsicWidth() / 2, (float) mBoundaryDrawable.getIntrinsicHeight() / 2);
        mBoundaryDrawable.draw(canvas);
        canvas.restore();

        // 绘制中间的红色固定不动的部分，也是一个Drawable
        canvas.save();
        canvas.translate(this.mHalfWidth - mReferenceDrawable.getIntrinsicWidth() / 2, this.mMarginTop + (mBoundaryDrawable.getIntrinsicHeight() - mReferenceDrawable.getIntrinsicHeight()) / 2);
        mReferenceDrawable.draw(canvas);
        canvas.restore();

        // 绘制东南西北
        canvas.save();
        float descent = (((this.j.descent() - this.j.ascent()) / 2.0f) * 2.0f) - this.j.descent();
        int i = 0;
        canvas.rotate((-this.mDirection), (float) this.mHalfWidth, (float) this.w);
        while (i < 4) {
            this.j.setColor(i == 0 ? 0xFFF15238 : -1);
            float measureText = this.j.measureText(mDirectionStringArray[i]);
            if (i != 0) {
                canvas.rotate(90, (float) this.mHalfWidth, (float) this.w); // 每次绘制一个字完后位移90度
            }
            canvas.drawText(mDirectionStringArray[i], ((float) this.mHalfWidth) - (measureText / 2.0f), (b(39.0f) + this.mMarginTop) + descent, this.j);
            i++;
        }
        canvas.restore();

        // 绘制中间方位数和文字描述
        canvas.save();
        canvas.drawText(this.mDirectionString, ((float) this.mHalfWidth) - (this.h.measureText(this.mDirectionString) / 2.0f), (((((this.h.descent() - this.h.ascent()) / 2.0f) * 2.0f) - this.h.descent()) + b(130.0f)) + this.mMarginTop, this.h);
        canvas.drawText(this.mDirectionDetialString, ((float) this.mHalfWidth) - (this.i.measureText(this.mDirectionDetialString) / 2.0f), ((((this.i.descent() - this.i.ascent()) / 2.0f) * 2.0f) - this.i.descent()) + (this.mMarginTop + b(162.0f)), this.i);
    }

    public float b(float f) {
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    public float c(float f) {
        return TypedValue.applyDimension(2, f, getResources().getDisplayMetrics());
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 128) {
            setContentDescription(this.mDirectionDetialString + "," + this.mDirectionString);
        }
        super.onInitializeAccessibilityEvent(accessibilityEvent);
    }
}