package com.liunian.androidbasic.addbigimage;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liunian.androidbasic.R;
import com.liunian.androidbasic.addbigimage.view.BigImageView;
import com.liunian.androidbasic.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

public class AddBigImageActivity extends BaseActivity {
    BigImageView mBigImageView;

    @Override
    protected void setUpViewAndData(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_big_image);

        mBigImageView = (BigImageView) findViewById(R.id.big_image_view);
        try {
            mBigImageView.setInputStream(getAssets().open("ditu.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
