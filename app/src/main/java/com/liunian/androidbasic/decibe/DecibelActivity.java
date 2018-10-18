package com.liunian.androidbasic.decibe;

import android.Manifest;
import android.animation.ValueAnimator;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.liunian.androidbasic.R;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class DecibelActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    public static final int MAX_LENGTH = 600000;
    private double db = 0.0d;
    private int BASE = 1;
    DecibelView decibelView;
    private String filePath;
    Handler handler = new Handler();
    double lastDb = 0.0d;
    MediaRecorder mMediaRecorder;
    ValueAnimator valueAnimator;
    private int maxDb;
    private int minDb = 0;
    private static int RECORD_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decibe);


        getWindow().setBackgroundDrawable(null);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }

    private void init() {
        this.decibelView = (DecibelView) findViewById(R.id.decibel_view);
        this.filePath = "/dev/null";
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        String[] perms = {Manifest.permission.RECORD_AUDIO};
        if (!EasyPermissions.hasPermissions(this, perms)) { // 如果没有该权限，弹出弹框提示用户申请权限
            EasyPermissions.requestPermissions(this, "为了正常使用，请允许麦克风权限!", RECORD_AUDIO_PERMISSION_CODE, perms);
        } else { // 如果有权限，直接开始录音
            startRecord();
        }
    }


    // 用来更新分贝值的runnable
    Runnable update = new Runnable() {
        public void run() {
            DecibelActivity.this.updateMicStatus();
        }
    };

    // 获得两次调用该方法时间内的最大声压值
    private float getMaxAmplitude() {
        if (this.mMediaRecorder == null) {
            return 5.0f;
        }
        try {
            return (float) this.mMediaRecorder.getMaxAmplitude(); // 获得两次调用该方法时间内的最大声压值
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    // 根据采集的声音更新分贝值，并在500毫秒后再次更新
    private void updateMicStatus() {
        if (this.mMediaRecorder != null) {
            double maxAmplitude = ((double) getMaxAmplitude()) / ((double) this.BASE); // 获得这500毫秒内最大的声压值
            if (maxAmplitude > 1.0d) {
                db = Math.log10(maxAmplitude) * 20.0d; //将声压值转为分贝值
                if (this.minDb == 0) {
                    this.minDb = (int) db;
                }
                startAnimator(); // 开始动画更新分贝值
                this.lastDb = db;
            }
            this.handler.postDelayed(this.update, 500); // 通过handler向主线程发送消息，500毫秒后执行this.update，再次更新分贝值
        }
    }

    private void cancelAnimator() {
        if (valueAnimator != null && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
    }

    // 动画更新分贝值
    private void startAnimator() {
        cancelAnimator();
        valueAnimator = ValueAnimator.ofFloat(new float[]{((float) this.lastDb), (float) (this.db)});
        valueAnimator.setDuration(400); // 设置动画时长为400
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                DecibelActivity.this.decibelView.setDb(floatValue);
                int intValue = (int) (floatValue);
                // 更新到目前为止的最大音量
                if (intValue > DecibelActivity.this.maxDb) {
                    DecibelActivity.this.maxDb = intValue;
                    DecibelActivity.this.decibelView.setMaxDb(DecibelActivity.this.maxDb);
                }
                // 更新到目前为止的最小音量
                if (intValue < DecibelActivity.this.minDb) {
                    DecibelActivity.this.minDb = intValue;
                    DecibelActivity.this.decibelView.setMinDb(DecibelActivity.this.minDb);
                }
            }
        });
        valueAnimator.start();
    }

    // 开始录音
    private void startRecord() {
        try {
            // 创建MediaRecorder并初始化参数
            if (this.mMediaRecorder == null) {
                this.mMediaRecorder = new MediaRecorder();
            }
            this.mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            this.mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            this.mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            this.mMediaRecorder.setOutputFile(this.filePath);
            this.mMediaRecorder.setMaxDuration(MAX_LENGTH);
            // 开始录音
            this.mMediaRecorder.prepare();
            this.mMediaRecorder.start();
            updateMicStatus(); // 更新分贝值
        } catch (Exception e) {
            showAppSettingsDialog();
        }
    }

    // 停止录音
    private void stopRecord() {
        if (this.mMediaRecorder != null) {
            try {
                this.mMediaRecorder.stop();
                this.mMediaRecorder.reset();
                this.mMediaRecorder.release();
                this.mMediaRecorder = null;
            } catch (Exception e) {
                this.mMediaRecorder = null;
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission(); // 这里在onStart方法中检查权限，如果有权限则直接开始录音
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopRecord(); // 在onStop方法中开始录音
    }

    @Override
    protected void onDestroy() {
        this.handler.removeCallbacks(this.update); // 移除handler中未执行完的消息，避免内存泄露
        cancelAnimator(); // 取消动画，避免内存泄露
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //将请求结果传递EasyPermission库处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    // 申请权限成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i("liunianprint:", "onPermissionsGranted");
        startRecord(); // 在onStart方法中开始录音
    }

    // 申请权限失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i("liunianprint:", "onPermissionsDenied");
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            showAppSettingsDialog();
        }
    }

    private void showAppSettingsDialog() {
        new AppSettingsDialog.Builder(this).setTitle("该功能需要麦克风权限").setRationale("该功能需要麦克风权限，请在设置里面开启！").build().show();
    }
}
