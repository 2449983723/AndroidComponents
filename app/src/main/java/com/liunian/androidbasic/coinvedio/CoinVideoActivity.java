package com.liunian.androidbasic.coinvedio;


import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;

import com.liunian.androidbasic.R;

import java.util.Random;

public class CoinVideoActivity extends AppCompatActivity {
    private View mEmptyView;
    private boolean mIsPause = true;
    private TextView mTextView;
    private VideoView mVideoView;

    private class OnCoinClickListener implements OnClickListener {
        private OnCoinClickListener() {
        }

        public void onClick(View view) {
            CoinVideoActivity.this.startAnimation(); // 播放翻转硬币视频
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_coin_vedio);
        // 设置默认的窗口背景为空，减少绘制
        getWindow().setBackgroundDrawable(null);
        // 隐藏标题栏和状态栏
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.mIsPause = true;
        init();
    }

    private void init() {
        this.mTextView = (TextView) findViewById(R.id.coin_desc); // 提示文字
        this.mVideoView = (VideoView) findViewById(R.id.video_view); // 播放视频控件
        this.mVideoView.setOnCompletionListener(new OnCompletionListener() { // 视频播放完成后的监听
            public void onCompletion(MediaPlayer mediaPlayer) {
                CoinVideoActivity.this.mEmptyView.setOnClickListener(new OnCoinClickListener()); // 视频播放完成后再次给mEmptyView绑定监听事件
            }
        });
        this.mEmptyView = findViewById(R.id.empty_view); // mEmptyView是一个透明的View，通过点击它开始播放视频
        this.mEmptyView.setOnClickListener(new OnCoinClickListener());
    }

    private void startInitAnimation() { // 设置mVideoView最开始的视频（很短，相当于一张图片）
        this.mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.coininit));
        this.mVideoView.start();
    }

    // 播放翻转硬币视频
    private void startAnimation() {
        String str;
        if (new Random().nextInt(2) == 0) { // 生成0或1的随机数，如果为0则播放脸朝上的视频
            str = "android.resource://" + getPackageName() + "/" + R.raw.coinvideo1;
        } else { // 如果为1则表示字朝上，播放字朝上的视频
            str = "android.resource://" + getPackageName() + "/" + R.raw.coinvideo2;
        }
        this.mVideoView.setVideoURI(Uri.parse(str));
        this.mVideoView.start();
        this.mEmptyView.setOnClickListener(null); // 开始播放视频或将mEmptyView的点击事件设置为空，避免重复播放视频
    }

    protected void onResume() {
        super.onResume();
        if (this.mIsPause) {
            this.mTextView.setText(getResources().getString(R.string.coin_click_text));
            startInitAnimation();
            this.mIsPause = false;
        }
    }

    protected void onPause() {
        super.onPause();
        this.mIsPause = true;
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(new ContextWrapper(context) {
            public Object getSystemService(String str) {
                if ("audio".equals(str)) {
                    return getApplicationContext().getSystemService(str); // 使用ApplicationContext来获得服务，避免内存泄露
                }
                return super.getSystemService(str);
            }
        });
    }
}