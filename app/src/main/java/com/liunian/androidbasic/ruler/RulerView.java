package com.liunian.androidbasic.ruler;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;

import com.liunian.androidbasic.R;

import java.text.DecimalFormat;

public class RulerView extends View {
    private static final DecimalFormat c = new DecimalFormat("0.00");
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    private float G;
    private RectF H;
    private boolean I;
    private Matrix J;
    private Matrix K;
    private int L;
    private float M;
    private float N;
    private boolean O;
    private float P;
    private float Q;
    private float Z;
    private float S;
    private float T;
    private float U;
    private ValueAnimator V;
    private ValueAnimator W;
    boolean a;
    private a aa;
    private float ab;
    private float ac;
    private float ad;
    private float ae;
    boolean b;
    private Paint d;
    private Paint e;
    private Paint f;
    private Paint g;
    private Paint h;
    private Paint i;
    private Paint j;
    private int k;
    private int l;
    private float m;
    private float n;
    private int o;
    private int p;
    private float q;
    private float r;
    private float s;
    private float t;
    private float u;
    private float v;
    private float w;
    private float x;
    private int y;
    private int z;

    public interface a {
        void a();

        void b();
    }

    public RulerView(Context context) {
        this(context, null);
    }

    public RulerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RulerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.o = 20;
        this.p = 20;
        this.H = new RectF();
        this.I = false;
        this.J = new Matrix();
        this.K = new Matrix();
        this.L = 0;
        this.M = 52.0f;
        this.O = false;
        this.a = false;
        this.b = false;
        this.ab = a(5.0f);
        this.ac = a(10.5f);
        a(context, attributeSet, i);
        e();
    }

    private void a(Context context, AttributeSet attributeSet, int defStyleAttr) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RulerView);
        this.F = obtainStyledAttributes.getColor(R.styleable.RulerView_rulerPanelColor, 0xFF888888);
        this.o = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_scaleStart, 10);
        this.p = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_scaleEnd, 10);
        this.q = (float) getResources().getDimensionPixelSize(R.dimen.ruler_xdpi);
        this.y = obtainStyledAttributes.getColor(R.styleable.RulerView_cursorTextColor, -1);
        this.z = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_cursorTextSize, 64);
        this.A = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_cursorTextLineSpace, 30);
        this.B = obtainStyledAttributes.getColor(R.styleable.RulerView_rulerScaleTextColor, -1);
        this.C = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_rulerScaleTextSize, 48);
        this.G = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_roundRadius, 10);
        this.D = obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_handlerSize, 35);
        this.t = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_scaleLengthShortInch, 60);
        this.u = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_scaleLengthLongInch, 80);
        this.v = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_scaleLengthShortCm, 60);
        this.w = (float) obtainStyledAttributes.getDimensionPixelSize(R.styleable.RulerView_scaleLengthLongCm, 80);
        this.N = (float) ViewConfiguration.get(context).getScaledTouchSlop();
        this.E = obtainStyledAttributes.getInt(R.styleable.RulerView_defaultValue, 52);
        obtainStyledAttributes.recycle();

        this.r = this.q / 2.54f;
        this.s = this.q;
    }

    private void e() {
        this.d = new Paint();
        this.d.setAntiAlias(true);
        this.d.setDither(true);
        this.d.setColor(this.B);
        this.d.setStrokeWidth(2.0f);
        this.e = new Paint();
        this.e.setAntiAlias(true);
        this.e.setDither(true);
        this.e.setTextAlign(Align.CENTER);
        this.e.setColor(this.B);
        this.e.setTextSize((float) this.C);
        this.f = new Paint();
        this.f.setAntiAlias(true);
        this.f.setDither(true);
        this.f.setTypeface(Typeface.DEFAULT_BOLD);
        this.f.setTextSize((float) this.z);
        this.f.setColor(this.y);
        this.f.setTextAlign(Align.LEFT);
        this.h = new Paint(1);
        this.h.setStyle(Style.STROKE);
        this.h.setStrokeWidth(4.0f);
        this.h.setColor(1728053247);
        this.g = new Paint();
        this.g.setAntiAlias(true);
        this.g.setDither(true);
        this.g.setColor(this.F);
        this.i = new Paint();
        this.i.setAntiAlias(true);
        this.i.setDither(true);
        this.i.setStrokeWidth(3.0f);
        this.i.setColor(this.y);
        this.j = new Paint();
        this.j.setColor(869481002);
        this.x = ((float) (this.A * 2)) + a(this.f);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            a(i, i2, i3, i4);
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!this.I) {
            canvas.getMatrix(this.J);
            this.I = true;
        }
        canvas.drawRoundRect(this.H, this.G, this.G, this.g);
        a(canvas);
        b(canvas);
        c(canvas);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled()) {
            return false;
        }
        boolean z = true;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                if (a(motionEvent)) {
                    this.S = motionEvent.getX();
                    this.T = motionEvent.getY();
                    this.P = motionEvent.getY();
                    b(motionEvent);
                    break;
                }
                return false;
            case 1:
                if (this.O) {
                    c(motionEvent);
                    b();
                    f();
                    setPressed(false);
                } else {
                    a();
                    c(motionEvent);
                    b();
                }
                if (((double) Math.abs(x - this.S)) <= 1.0E-4d && ((double) Math.abs(y - this.T)) <= 1.0E-4d) {
                    z = false;
                    break;
                }
                invalidate();
                break;
            case 2:
                if (!this.O) {
                    if (Math.abs(motionEvent.getY() - this.P) > this.N) {
                        b(motionEvent);
                    }
                    z = false;
                    break;
                }
                c(motionEvent);
                invalidate();
                z = false;
                break;
            case 3:
                if (this.O) {
                    b();
                    f();
                    setPressed(false);
                }
                invalidate();
                break;
        }
        return z;
    }

    private boolean a(MotionEvent motionEvent) {
        int round = Math.round(motionEvent.getX());
        int round2 = Math.round(motionEvent.getY());
        return ((float) round) > this.H.left && ((float) round) < this.H.right && ((float) round2) > this.H.top + ((float) this.o) && ((float) round2) < this.H.bottom - ((float) this.p);
    }

    private void b(MotionEvent motionEvent) {
        this.Q = 0.0f;
        setPressed(true);
        a();
        c(motionEvent);
        g();
    }

    private void f() {
        this.Z += this.Q;
        this.Q = 0.0f;
    }

    void a() {
        this.O = true;
    }

    void b() {
        this.O = false;
    }

    private void g() {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void c(MotionEvent motionEvent) {
        this.Q = ((float) Math.round(motionEvent.getY())) - this.P;
    }

    public void c() {
        float f = 0.0f;
        if (isEnabled() && !this.a && !this.b) {
            float paddingTop = this.Z - ((float) (getPaddingTop() + this.o));
            if (paddingTop >= 0.0f) {
                f = paddingTop;
            }
            paddingTop = ((this.H.bottom - this.H.top) - ((float) this.o)) - ((float) this.p);
            if (f > paddingTop) {
                f = paddingTop;
            }
            a(Math.round((f / this.r) * 100.0f));
        }
    }

    public void d() {
        if (isEnabled() && !this.a && !this.b) {
            this.P = 0.0f;
            h();
        }
    }

    private void a(int i, int i2, int i3, int i4) {
        this.k = i3 - i;
        this.l = i4 - i2;
        float paddingStart = this.x + ((float) getPaddingStart());
        float paddingTop = (float) getPaddingTop();
        float paddingEnd = (((float) this.k) - this.x) - ((float) getPaddingEnd());
        float paddingBottom = (float) (this.l - getPaddingBottom());
        this.m = (float) (getPaddingTop() + this.o);
        this.n = (float) ((this.l - getPaddingBottom()) - this.p);
        this.Z = ((((float) this.E) * this.r) * 0.1f) + this.m;
        this.H.set(paddingStart, paddingTop, paddingEnd, paddingBottom);
    }

    private void a(Canvas canvas) {
        float f = this.r * 0.2f;
        float f3 = this.H.right;
        float f4 = this.H.top;
        float f5 = this.m;
        int i = (int) ((this.n - f5) / f);
        for (int i2 = 0; i2 <= i; i2++) {
            float f6 = (((float) i2) * f) + f5;
            if (f6 >= ((float) this.o) + f4) {
                int i3;
                float f7;
                if (i2 % 5 == 0) {
                    float f8 = this.w;
                    if (i2 == 0) {
                        a(canvas, "cm", (f3 - f8) - 60.0f, f6, this.e);
                        i3 = 4;
                        f7 = f8;
                    } else {
                        a(canvas, String.valueOf(i2 / 5), (f3 - f8) - 60.0f, f6, this.e);
                        i3 = 4;
                        f7 = f8;
                    }
                } else {
                    i3 = 2;
                    f7 = this.v;
                }
                this.d.setStrokeWidth((float) i3);
                canvas.drawLine(f3 - f7, f6, f3, f6, this.d);
            }
        }
    }

    private void b(Canvas canvas) {
        float f = this.s * 0.2f;
        float f2 = this.H.left;
        float f3 = this.H.right;
        float f4 = this.H.top;
        f3 = this.H.bottom;
        float f5 = this.m;
        int i = (int) ((this.n - f5) / f);
        for (int i2 = 0; i2 <= i; i2++) {
            float f6 = (((float) i2) * f) + f5;
            if (f6 >= ((float) this.o) + f4) {
                int i3;
                float f7;
                if (i2 % 5 == 0) {
                    float f8 = this.u;
                    if (i2 == 0) {
                        a(canvas, "in", (f2 + f8) + 60.0f, f6, this.e);
                        i3 = 4;
                        f7 = f8;
                    } else {
                        a(canvas, String.valueOf(i2 / 5), (f2 + f8) + 60.0f, f6, this.e);
                        i3 = 4;
                        f7 = f8;
                    }
                } else {
                    i3 = 2;
                    f7 = this.t;
                }
                this.d.setStrokeWidth((float) i3);
                canvas.drawLine(f2, f6, f2 + f7, f6, this.d);
            }
        }
    }

    private void c(Canvas canvas) {
        float f;
        float f2 = this.r;
        float f3 = this.Z + this.Q;
        if (f3 < this.m) {
            f3 = this.m;
        } else if (f3 > this.n) {
            f3 = this.n;
        }
        if (f3 < this.H.top + ((float) this.o)) {
            f = this.H.top + ((float) this.o);
        } else {
            f = f3;
        }
        Canvas canvas2 = canvas;
        canvas2.drawLine(0.0f, ((float) this.o) + this.H.top, (float) this.k, ((float) this.o) + this.H.top, this.i);
        canvas2 = canvas;
        float f4 = f;
        canvas2.drawRect(this.H.left, ((float) this.o) + this.H.top, this.H.right, f4, this.j);
        canvas.drawLine(0.0f, f, (float) this.k, f, this.i);
        int i = this.k / 2;
        canvas.drawCircle((float) i, f, (float) this.D, this.i);
        float f5 = this.ab;
        float f6 = this.ac;
        this.h.setStrokeWidth(5.0f);
        canvas2 = canvas;
        canvas2.drawLine(((float) i) - (f6 / 2.0f), f - f5, (f6 / 2.0f) + ((float) i), f - f5, this.h);
        canvas.drawLine(((float) i) - (f6 / 2.0f), f, ((float) i) + (f6 / 2.0f), f, this.h);
        canvas2 = canvas;
        canvas2.drawLine(((float) i) - (f6 / 2.0f), f + f5, (f6 / 2.0f) + ((float) i), f + f5, this.h);
        f2 = (f - this.m) / f2;
        a(canvas, c.format((double) f2) + " cm", ((float) this.k) - (this.x / 2.0f), f / 2.0f, this.f);
        a(canvas, c.format((double) (f2 / 2.54f)) + " in", this.x / 2.0f, f / 2.0f, this.f);
    }

    private float a(Paint paint) {
        FontMetrics fontMetrics = paint.getFontMetrics();
        return fontMetrics.descent - fontMetrics.ascent;
    }

    private void a(Canvas canvas, String str, float f, float f2, Paint paint) {
        this.K.setRotate(90.0f, f, f2);
        canvas.setMatrix(this.K);
        canvas.drawText(str, f, a(paint, f2), paint);
        canvas.setMatrix(this.J);
        this.K.reset();
    }

    private float a(Paint paint, float f) {
        FontMetrics fontMetrics = paint.getFontMetrics();
        return f - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f);
    }

    private void a(int i) {
        final float f = this.r * 0.01f;
        if (this.V == null) {
            this.V = new ValueAnimator();
            this.V.setDuration(400);
            this.V.setInterpolator(new AccelerateInterpolator());
            this.V.addUpdateListener(new AnimatorUpdateListener() {

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RulerView.this.m = RulerView.this.U - (((float) ((Integer) valueAnimator.getAnimatedValue()).intValue()) * f);
                    RulerView.this.invalidate();
                }
            });
            this.V.addListener(new AnimatorListener() {

                public void onAnimationStart(Animator animator) {
                    RulerView.this.setEnabled(false);
                    RulerView.this.a = true;
                    if (RulerView.this.aa != null) {
                        RulerView.this.aa.a();
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    RulerView.this.setEnabled(true);
                    RulerView.this.a = false;
                    if (RulerView.this.aa != null) {
                        RulerView.this.aa.b();
                    }
                }

                public void onAnimationCancel(Animator animator) {
                    RulerView.this.setEnabled(true);
                    RulerView.this.a = false;
                    if (RulerView.this.aa != null) {
                        RulerView.this.aa.b();
                    }
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
        }
        this.V.setIntValues(new int[]{0, i});
        this.U = this.m;
        this.V.start();
    }

    private void h() {
        this.U = this.H.top + ((float) this.o);
        this.ad = this.U - this.m;
        final float f = ((float) this.o) + (((((float) this.E) * this.r) * 0.1f) + this.H.top);
        float f2 = this.Z + this.Q;
        if (f2 < this.m) {
            f2 = this.m;
        } else if (f2 > this.n) {
            f2 = this.n;
        }
        this.ae = f2 - f;
        if (this.ad != 0.0f || this.ae != 0.0f) {
            if (this.W == null) {
                this.W = new ValueAnimator();
                this.W.setDuration(400);
                this.W.setInterpolator(new AccelerateInterpolator());
                this.W.addUpdateListener(new AnimatorUpdateListener() {

                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        RulerView.this.m = RulerView.this.U - (RulerView.this.ad * floatValue);
                        RulerView.this.Z = (floatValue * RulerView.this.ae) + f;
                        RulerView.this.invalidate();
                    }
                });
                this.W.addListener(new AnimatorListener() {

                    public void onAnimationStart(Animator animator) {
                        RulerView.this.setEnabled(false);
                        RulerView.this.b = true;
                    }

                    public void onAnimationEnd(Animator animator) {
                        RulerView.this.setEnabled(true);
                        RulerView.this.b = false;
                        RulerView.this.Q = 0.0f;
                        RulerView.this.m = RulerView.this.H.top + ((float) RulerView.this.o);
                        RulerView.this.Z = ((((float) RulerView.this.E) * RulerView.this.r) * 0.1f) + RulerView.this.m;
                    }

                    public void onAnimationCancel(Animator animator) {
                        RulerView.this.setEnabled(true);
                        RulerView.this.b = false;
                        RulerView.this.Q = 0.0f;
                        RulerView.this.m = RulerView.this.H.top + ((float) RulerView.this.o);
                        RulerView.this.Z = ((((float) RulerView.this.E) * RulerView.this.r) * 0.1f) + RulerView.this.m;
                    }

                    public void onAnimationRepeat(Animator animator) {
                    }
                });
            }
            this.W.setFloatValues(new float[]{1.0f, 0.0f});
            this.W.start();
        }
    }

    public float a(float f) {
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }
}