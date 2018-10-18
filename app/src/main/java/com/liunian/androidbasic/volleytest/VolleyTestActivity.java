package com.liunian.androidbasic.volleytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.liunian.androidbasic.R;
import com.liunian.androidbasic.asynctasktest.BitmapCache;
import com.liunian.jnitestlib.MyLib;

import org.json.JSONException;
import org.json.JSONObject;

public class VolleyTestActivity extends AppCompatActivity {
    RequestQueue mRequestQueue;
    private ImageView mImageView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        mImageView = (ImageView) findViewById(R.id.imageView);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String imagePath = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1641460231,985790943&fm=27&gp=0.jpg";
//                ImageRequest imageRequest = new ImageRequest(imagePath, new Response.Listener<Bitmap>() {
//                    @Override
//                    public void onResponse(Bitmap response) {
//                        mImageView.setImageBitmap(response);
//                    }
//                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.i("liunianprint:", error.getMessage() + error.toString());
//                    }
//                });
//                mRequestQueue.add(imageRequest);
                ImageLoader imageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(mImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                String imagePath = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1641460231,985790943&fm=27&gp=0.jpg";
                imageLoader.get(imagePath, listener);
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("liunianprint:", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("liunianprint:", error.getMessage() + error.toString());
                    }
                });
        mRequestQueue.add(stringRequest);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("seeType", 1);
            jsonObject.put("source", 1);
            jsonObject.put("userId", 30);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://sitapp.2ncai.com/lotto/android/v1.0/order-group/queryOrderGroupPersonInfo",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("liunianprint:", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("liunianprint:", error.getMessage(), error);
            }
        });

        mRequestQueue.add(jsonObjectRequest);

        Log.i("liunianprint:", MyLib.add(1, 2) + "");
    }
}
