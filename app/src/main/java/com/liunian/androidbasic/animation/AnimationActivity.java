package com.liunian.androidbasic.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

import com.liunian.androidbasic.R;

public class AnimationActivity extends AppCompatActivity {
    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        mStartButton = (Button) findViewById(R.id.start);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationTest();
            }
        });
    }

    private void animationTest() {
        WindowManager wm = this.getWindowManager();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, wm.getDefaultDisplay().getWidth() - mStartButton.getWidth());
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float) animation.getAnimatedValue();
                Log.i("liunianprint:", "curValue: " + curValue);
                mStartButton.setX(curValue);
            }
        });
        valueAnimator.start();

//        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
//        scaleAnimation.setDuration(2000);
////        scaleAnimation.setFillAfter(true);
//        mStartButton.startAnimation(scaleAnimation);
    }
}
