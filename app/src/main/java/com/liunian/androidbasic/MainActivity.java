package com.liunian.androidbasic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liunian.androidbasic.addbigimage.AddBigImageActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button mAddBigPic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddBigPic = (Button) findViewById(R.id.addBigPic);
        mAddBigPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            int resId = view.getId();
            Intent intent = new Intent();
            switch (resId) {
                case R.id.addBigPic:
                    intent.setClass(MainActivity.this, AddBigImageActivity.class);
                    break;
            }
            if (intent != null) {
                this.startActivity(intent);
            }
        }
    }
}
