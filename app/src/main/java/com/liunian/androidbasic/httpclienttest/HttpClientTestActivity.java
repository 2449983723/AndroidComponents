package com.liunian.androidbasic.httpclienttest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.liunian.androidbasic.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;

public class HttpClientTestActivity extends AppCompatActivity {
    Button mSendRequestButton;
    TextView mTestTextView;
    MyHandler mHandler = new MyHandler();

    public class MyHandler extends Handler {
        public final static int REQUEST_OK = 1;

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == REQUEST_OK) {
                String response = (String) msg.obj;
                mTestTextView.setText(response);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_client_test);
        mSendRequestButton = (Button) findViewById(R.id.send_request);
        mTestTextView = (TextView) findViewById(R.id.test_text_view);
        mSendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest(); // 发送请求
            }
        });
    }

    private void sendRequest() {
        new Thread() {
            @Override
            public void run() {
                // 创建一个HttpClient对象
//                HttpClient httpClient = new DefaultHttpClient();
//                // 创建一个代表请求的对象
//                HttpGet httpGet = new HttpGet("http://www.baidu.com");
//
//                try {
//                    // 执行请求
//                    HttpResponse httpResponse = httpClient.execute(httpGet);
//                    // 根据返回结果的状态判断是否正常获得数据
//                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                        HttpEntity entity = httpResponse.getEntity(); // 获得返回数据
//                        String response = EntityUtils.toString(entity, "utf-8"); // 将返回数据转换为字符串
//                        Log.i("liunianprint:", response);
//                        // 将返回数据通过Handler发送给主线程并更新界面
//                        Message message = Message.obtain();
//                        message.what = MyHandler.REQUEST_OK;
//                        message.obj = response;
//                        mHandler.sendMessage(message);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
                try {
                    URL url = new URL("https://www.baidu.com");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setDoInput(true);
//                    connection.connect();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream is = connection.getInputStream();
                        String response = convertStreamToString(is);
                        Log.i("liunianprint:", response);
                        Message message = Message.obtain();
                        message.what = MyHandler.REQUEST_OK;
                        message.obj = response;
                        mHandler.sendMessage(message);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
