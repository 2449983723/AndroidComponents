package com.liunian.androidbasic.addbigimage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liunian.androidbasic.R;
import com.liunian.androidbasic.addbigimage.view.BigImageView;

import java.io.IOException;

public class AddBigImageActivity extends AppCompatActivity {
    BigImageView mBigImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_big_image);

        mBigImageView = (BigImageView) findViewById(R.id.big_image_view);
        try {
            mBigImageView.setInputStream(getAssets().open("ditu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
