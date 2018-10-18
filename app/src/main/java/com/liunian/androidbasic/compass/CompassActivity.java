package com.liunian.androidbasic.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;

import com.liunian.androidbasic.R;

import static android.hardware.Sensor.TYPE_ORIENTATION;

public class CompassActivity extends AppCompatActivity {
    private Compass mCompassView; // 自定义指南针View，用来绘制指南针
    private float mCurrentDirection; // 当前方向
    private AccelerateInterpolator mInterpolator; // 转动指南针时使用的插值器
    private float mLastDirection;
    private Sensor mOrientationSensor; // 方向传感器
    private MZSensorEventListener mSensorListener; // 方向传感器监听对象
    private SensorManager mSensorManager; // 传感器管理对象
    private boolean mStopDrawing = false; // 记录是否刷新界面，当界面可见的时候才刷新界面
    private float mTargetDirection; // 目标方向

    // 方向传感器监听类
    private class MZSensorEventListener implements SensorEventListener {
        private MZSensorEventListener() {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            int type = sensorEvent.sensor.getType();
            if (type == TYPE_ORIENTATION) { // 如果是方向变化了
                CompassActivity.this.mTargetDirection = CompassActivity.this.normalizeDegree(sensorEvent.values[0]); // 获得目标方向
                if (CompassActivity.this.mCompassView != null && !CompassActivity.this.mStopDrawing) {
                    float targetDirection = CompassActivity.this.mTargetDirection;
                    // 去除无用的转动
                    if (targetDirection - CompassActivity.this.mCurrentDirection > 180.0f) {
                        targetDirection -= 360.0f;
                    } else if (targetDirection - CompassActivity.this.mCurrentDirection < -180.0f) {
                        targetDirection += 360.0f;
                    }
                    float directionInv = targetDirection - CompassActivity.this.mCurrentDirection; // 计算需要转动的间隔
                    float directionPre = directionInv;
                    if (Math.abs(directionPre) > 0.1f) {
                        directionPre = directionPre > 0.0f ? 0.1f : -0.1f;
                    }
                    CompassActivity.this.mCurrentDirection = CompassActivity.this.normalizeDegree((CompassActivity.this.mInterpolator.getInterpolation(
                            Math.abs(directionPre) >= 0.1f ? 0.4f : 0.3f) * (directionInv)) + CompassActivity.this.mCurrentDirection); // 这里采用加速插值器，让转动看起来更加流畅
                    if (((double) Math.abs(CompassActivity.this.mLastDirection - CompassActivity.this.mCurrentDirection)) > 0.05d) { // 如果需要转动的角度大于0.05，则刷新界面更新UI
                        CompassActivity.this.mCompassView.updateDirection(CompassActivity.this.mCurrentDirection);
                        CompassActivity.this.mLastDirection = CompassActivity.this.mCurrentDirection;
                    }
                }
            }
        }

        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_compass);
        getWindow().setBackgroundDrawable(null);
        init();
    }

    private void init() {
        this.mCurrentDirection = 0.0f;
        this.mTargetDirection = 0.0f;
        this.mStopDrawing = true;
        this.mInterpolator = new AccelerateInterpolator();
        this.mCompassView = (Compass) findViewById(R.id.compass);
        this.mSensorListener = new MZSensorEventListener();
        this.mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        this.mOrientationSensor = this.mSensorManager.getDefaultSensor(TYPE_ORIENTATION);
    }

    protected void onResume() {
        super.onResume();
        if (this.mOrientationSensor != null) {
            this.mSensorManager.registerListener(this.mSensorListener, this.mOrientationSensor, 0);
        }

        this.mStopDrawing = false;
    }

    protected void onPause() {
        super.onPause();
        this.mStopDrawing = true;
        if (!(this.mOrientationSensor == null)) {
            this.mSensorManager.unregisterListener(this.mSensorListener);
        }
    }

    // 处理传感器传过来方向的方法，确保方向参数总在0-360度之间
    private float normalizeDegree(float f) {
        return (f + 360.0f) % 360.0f;
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}