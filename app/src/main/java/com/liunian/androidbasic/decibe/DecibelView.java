package com.liunian.androidbasic.decibe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.liunian.androidbasic.R;

public class DecibelView extends View {
    private int maxDb;
    static int minDb;
    private float lineRoundStart;
    private float startAngle;
    private Matrix colorRoundMatrix = new Matrix();
    private int[]  colorRoundColors = new int[]{0xFF009AD6, 0xFF3CDF5F, 0xFFCDD513, 0xFFFF4639, 0xFF26282C};
    private SweepGradient colorRoundSweepGradient;
    Paint currentValuePaint;
    Paint lineRoundRadiusPaint;
    Paint lineRoundRadiusBgPaint;
    Paint outsideRoundRadiusPaint;
    Paint colorRoundPaint;
    Paint currentLineRoundPaint;
    Paint dbTextPaint;
    RectF colorRoundRect;
    float degrees = 0.0f;
    int currentDb = 0;
    int width;
    int height;
    int halfWidth;
    int centerY;
    private Bitmap triangleBitmap;
    private float outsideRoundRadius;
    private float colorRoundRadius;
    private float lineRoundLength;
    private float currentLineRoundLength;
    private float currentValueTextSize;
    private int lineRoundRadiusColor;
    private int lineRoundRadiusBgColor;
    private static float DB_VALUE_PER_SCALE = 1.22f; // 规定每一刻度等于1.22分贝
    private static float DEGRESS_VALUE_PER_SCALE = 3.0f; // 规定每一刻度的大小为3度
    private static float MAX_SCALE = 96; // 最大的刻度数

    public DecibelView(Context context) {
        this(context, null);
    }

    public DecibelView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DecibelView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        obtainStyledAttributes(context, attributeSet, i);
        initView();
    }

    private void obtainStyledAttributes(Context context, AttributeSet attributeSet, int i) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DecibelView, i, 0);
            this.outsideRoundRadius = obtainStyledAttributes.getDimension(R.styleable.DecibelView_outsideRoundRadius, 402.0f);
            this.colorRoundRadius = obtainStyledAttributes.getDimension(R.styleable.DecibelView_colorRoundRadius, 325.0f);
            this.currentLineRoundLength = obtainStyledAttributes.getDimension(R.styleable.DecibelView_currentLineRoundLength, 339.0f);
            this.lineRoundLength = obtainStyledAttributes.getDimension(R.styleable.DecibelView_lineRoundLength, 342.0f);
            this.lineRoundStart = obtainStyledAttributes.getDimension(R.styleable.DecibelView_lineRoundStart, 525.0f);
            this.currentValueTextSize = obtainStyledAttributes.getDimension(R.styleable.DecibelView_currentValueTextSize, toSp(60.0f));
            this.lineRoundRadiusColor = obtainStyledAttributes.getColor(R.styleable.DecibelView_lineRoundRadiusColor, 0x33FFFFFF);
            this.lineRoundRadiusBgColor = obtainStyledAttributes.getColor(R.styleable.DecibelView_lineRoundRadiusBgColor, 0xBFFFFFF);
            this.startAngle = obtainStyledAttributes.getFloat(R.styleable.DecibelView_startAngle, 125.0f);
            this.triangleBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.decibel_max_value)).getBitmap();
            obtainStyledAttributes.recycle();
        }
    }

    private void initView() {
        this.currentValuePaint = new Paint();
        this.currentValuePaint.setTextSize(this.currentValueTextSize);
        this.currentValuePaint.setAntiAlias(true);
        this.currentValuePaint.setStyle(Style.STROKE);
        this.currentValuePaint.setColor(-1);
        this.currentValuePaint.setTypeface(Typeface.create("sans-serif-medium", 0));
        this.lineRoundRadiusPaint = new Paint();
        this.lineRoundRadiusPaint.setAntiAlias(true);
        this.lineRoundRadiusPaint.setStyle(Style.STROKE);
        this.lineRoundRadiusPaint.setColor(this.lineRoundRadiusColor);
        this.lineRoundRadiusPaint.setStrokeWidth(5.0f);
        this.lineRoundRadiusBgPaint = new Paint();
        this.lineRoundRadiusBgPaint.setAntiAlias(true);
        this.lineRoundRadiusBgPaint.setStyle(Style.STROKE);
        this.lineRoundRadiusBgPaint.setColor(this.lineRoundRadiusBgColor);
        this.lineRoundRadiusBgPaint.setStrokeWidth(5.0f);
        this.outsideRoundRadiusPaint = new Paint();
        this.outsideRoundRadiusPaint.setAntiAlias(true);
        this.outsideRoundRadiusPaint.setStyle(Style.STROKE);
        this.outsideRoundRadiusPaint.setColor(this.lineRoundRadiusBgColor);
        this.outsideRoundRadiusPaint.setStrokeWidth(toDip(3.0f));
        this.currentLineRoundPaint = new Paint();
        this.currentLineRoundPaint.setAntiAlias(true);
        this.currentLineRoundPaint.setStyle(Style.STROKE);
        this.currentLineRoundPaint.setStrokeWidth(10.0f);
        this.dbTextPaint = new Paint();
        this.dbTextPaint.setAntiAlias(true);
        this.dbTextPaint.setStyle(Style.STROKE);
        this.dbTextPaint.setColor(-1);
        this.dbTextPaint.setTextSize(toSp(20.0f));
        this.dbTextPaint.setTypeface(Typeface.create("sans-serif-medium", 0));
        this.colorRoundPaint = new Paint();
        this.colorRoundPaint.setAntiAlias(true);
        this.colorRoundPaint.setStyle(Style.STROKE);
        this.colorRoundPaint.setStrokeWidth(toDip(4.0f));
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        // 在onSizeChanged中设置和控件大小相关的值，这个时候控件已经测量完成了
        this.width = getWidth();
        this.halfWidth = this.width / 2;
        this.height = getHeight();
        this.centerY = (int) (toDip(180.0f) + this.colorRoundRadius);
        initColorRoundRect();
        initColorRoundSweepGradient();
    }

    private void initColorRoundRect() {
        this.colorRoundRect = new RectF();
        this.colorRoundRect.left = ((float) this.halfWidth) - this.colorRoundRadius;
        this.colorRoundRect.top = ((float) this.centerY) - this.colorRoundRadius;
        this.colorRoundRect.right = ((float) this.halfWidth) + this.colorRoundRadius;
        this.colorRoundRect.bottom = ((float) this.centerY) + this.colorRoundRadius;
    }

    private void initColorRoundSweepGradient() {
        this.colorRoundSweepGradient = new SweepGradient((float) this.halfWidth, (float) this.centerY, this.colorRoundColors, null);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制最外面的圆环
        canvas.drawCircle((float) this.halfWidth, (float) this.centerY, this.outsideRoundRadius, this.outsideRoundRadiusPaint);
        canvas.save(); // 保存画布状态

        // 绘制刻度的背景线
        canvas.rotate((-this.startAngle) + 1.0f, (float) this.halfWidth, (float) this.centerY); // 先将画布旋转至起始角度
        for (int i = 0; i <= MAX_SCALE; i++) { // 循环绘制完所有背景线
            canvas.drawLine((float) this.halfWidth, (((float) this.centerY) - this.lineRoundStart) - this.lineRoundLength, (float) this.halfWidth, ((float) this.centerY) - this.lineRoundStart, this.lineRoundRadiusBgPaint);
            canvas.rotate(DEGRESS_VALUE_PER_SCALE, (float) this.halfWidth, (float) this.centerY); // 每次绘制完后将画布旋转一个刻度对应的角度
        }

        canvas.restore(); // 还原画布状态到上一次调用save方法时
        canvas.save();
        // 绘制分贝值达到部分的刻度线
        canvas.rotate((-this.startAngle) + 1.0f, (float) this.halfWidth, (float) this.centerY);  // 先将画布旋转至起始角度
        float tmpDegress = 0;
        while (tmpDegress <= ((int) this.degrees)) { // 循环绘制分贝值达到部分的刻度线
            if (this.degrees - tmpDegress >= DEGRESS_VALUE_PER_SCALE / 2) { // 这里判断一下，如果差值超过一半才绘制当前的刻度线
                canvas.drawLine((float) this.halfWidth, (((float) this.centerY) - this.lineRoundStart) - this.lineRoundLength, (float) this.halfWidth, ((float) this.centerY) - this.lineRoundStart, this.lineRoundRadiusPaint);
            }
            canvas.rotate(DEGRESS_VALUE_PER_SCALE, (float) this.halfWidth, (float) this.centerY);
            tmpDegress = DEGRESS_VALUE_PER_SCALE + tmpDegress;
        }

        // 绘制颜色渐变的半圆环
        canvas.restore();
        canvas.save();
        this.colorRoundMatrix.setRotate(270 - startAngle, (float) this.halfWidth, (float) this.centerY);
        this.colorRoundSweepGradient.setLocalMatrix(this.colorRoundMatrix);
        this.colorRoundPaint.setShader(this.colorRoundSweepGradient);
        canvas.drawArc(this.colorRoundRect, 270 - startAngle, 2 * startAngle, false, this.colorRoundPaint);
        this.colorRoundPaint.setShader(null);

        // 绘制分贝标志线，这根线的颜色会随着分贝的变化而变化
        canvas.restore();
        canvas.save();
        canvas.rotate((-this.startAngle) + 1.0f + this.degrees, (float) this.halfWidth, (float) this.centerY);
        this.colorRoundMatrix.setRotate(270  * (1 - this.degrees / (2 * startAngle)), (float) this.halfWidth, (float) this.centerY);
        this.colorRoundSweepGradient.setLocalMatrix(this.colorRoundMatrix);
        this.currentLineRoundPaint.setShader(this.colorRoundSweepGradient);
        canvas.drawLine((float) this.halfWidth, (((float) this.centerY) - this.lineRoundStart) - this.currentLineRoundLength, (float) this.halfWidth, ((float) this.centerY) - this.lineRoundStart, this.currentLineRoundPaint);
        this.currentLineRoundPaint.setShader(null);

        // 绘制半圆环的左边角
        canvas.restore();
        canvas.save();
        this.colorRoundPaint.setColor(0xFF009AD6);
        canvas.rotate((float) (((double) ((-this.startAngle) + 1.0f)) + 0.2d), (float) this.halfWidth, (float) this.centerY);
        canvas.drawLine((float) this.halfWidth, toDip(8.0f) + (((float) this.centerY) - toDip(95.0f)), (float) this.halfWidth, ((float) this.centerY) - toDip(95.0f), this.colorRoundPaint);
        canvas.restore();
        canvas.save();

        // 绘制半圆环的右边角
        this.colorRoundPaint.setColor(0xFFCF4036);
        canvas.rotate((float) (((double) (((-this.startAngle) + 1.0f) + 288.0f)) - 0.2d), (float) this.halfWidth, (float) this.centerY);
        canvas.drawLine((float) this.halfWidth, toDip(8.0f) + (((float) this.centerY) - toDip(95.0f)), (float) this.halfWidth, ((float) this.centerY) - toDip(95.0f), this.colorRoundPaint);
        canvas.restore();
        canvas.save();

        // 绘制标记最大分贝的小三角
        canvas.rotate((-this.startAngle) + ((((float) maxDb) / DB_VALUE_PER_SCALE) * DEGRESS_VALUE_PER_SCALE), (float) this.halfWidth, (float) this.centerY);
        canvas.drawBitmap(this.triangleBitmap, (float) this.halfWidth, (((float) this.centerY) - this.outsideRoundRadius) - toDip(10.0f), this.currentValuePaint);
        canvas.restore();
        canvas.save();

        // 绘制标记最小分贝的小三角
        canvas.rotate((-this.startAngle) + ((((float) minDb) / DB_VALUE_PER_SCALE) * DEGRESS_VALUE_PER_SCALE), (float) this.halfWidth, (float) this.centerY);
        canvas.drawBitmap(this.triangleBitmap, (float) this.halfWidth, (((float) this.centerY) - this.outsideRoundRadius) - toDip(10.0f), this.currentValuePaint);
        canvas.restore();

        // 绘制描述分贝的文字
        float descent = - this.currentValuePaint.ascent();
        float measureText = this.currentValuePaint.measureText(this.currentDb + "");
        canvas.drawText(this.currentDb + "", (((float) this.halfWidth) - (measureText / 2.0f)) - 10.0f, (((float) this.centerY) + (descent / 2.0f)) - 20.0f, this.currentValuePaint);
        canvas.drawText("dB", (measureText / 2.0f) + ((float) this.halfWidth), ((descent / 2.0f) + ((float) this.centerY)) - 20.0f, this.dbTextPaint);
    }

    // 设置最大的分贝值
    public void setMaxDb(int maxDb) {
        this.maxDb = maxDb;
        invalidate();
    }

    // 设置最小的分贝值
    public void setMinDb(int minDb) {
        this.minDb = minDb;
        invalidate();
    }

    // 设置当前分贝值
    public void setDb(float db) {
        this.currentDb = (int) db;
        this.degrees = ((db / DB_VALUE_PER_SCALE) * DEGRESS_VALUE_PER_SCALE); // 规定1刻度等于1.22分贝
        invalidate();
    }

    private float toDip(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private float toSp(float value) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }
}
