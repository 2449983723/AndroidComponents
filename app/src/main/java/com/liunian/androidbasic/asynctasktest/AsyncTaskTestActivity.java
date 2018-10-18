package com.liunian.androidbasic.asynctasktest;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.liunian.androidbasic.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AsyncTaskTestActivity extends AppCompatActivity {
    private ImageView mImageView;
    private Button mButton;
    private ProgressDialog mProgressDialog;
    private DownloadImageAsyncTask mDownloadImageAsyncTask; // 不能声明为AsyncTask，否则会报类型转换错误

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_test);
        initView();
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.imageView);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imagePath = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1641460231,985790943&fm=27&gp=0.jpg";
                startAsyncTask(imagePath);
            }
        });

        HandlerThread handlerThread = new HandlerThread("HandlerThreadTest"); // 构造一个HandlerThread对象
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 1) {
                    Log.i("aaaaaaa", "HandlerThread handlerMessage");
                }
            }
        };
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }

    private void startAsyncTask(String imagePath) {
        if (mDownloadImageAsyncTask != null && !mDownloadImageAsyncTask.isCancelled()) {
            mDownloadImageAsyncTask.cancel(true);
        }
        mDownloadImageAsyncTask = new DownloadImageAsyncTask();
        mDownloadImageAsyncTask.execute(imagePath);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在界面销毁后取消异步任务，以免发生内存泄露
        if (mDownloadImageAsyncTask != null && !mDownloadImageAsyncTask.isCancelled()) {
            mDownloadImageAsyncTask.cancel(true);
            mDownloadImageAsyncTask = null;
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setTitle("提示信息");
            mProgressDialog.setMessage("正在下载中，请稍后......");
            // 设置setCancelable(false); 表示我们不能取消这个弹出框，等下载完成之后再让弹出框消失
            mProgressDialog.setCancelable(false);
            // 设置ProgressDialog样式为水平的样式
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        }
        mProgressDialog.show();
    }

    public void setLoadProgress(int progress) {
        if (mProgressDialog != null) {
            mProgressDialog.setProgress(progress);
        }
    }

    public void dimissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void downloadImageSuccessCallBack(byte[] bytes) {
        if (bytes != null && mImageView != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            mImageView.setImageBitmap(bitmap);
        }
        dimissProgressDialog();
    }

    /**
     * 下载任务
     */
    public class DownloadImageAsyncTask extends AsyncTask<String, Integer, byte[]> {
        // 在UI线程中执行，任务开始前需要进行的UI操作，比如弹出加载框
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        // 在异步线程中执行耗时任务，比如请求网络数据
        @Override
        protected byte[] doInBackground(String... params) {
            //    通过Apache的HttpClient来访问请求网络中的一张图片
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(params[0]);
            byte[] image = new byte[]{};
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = null;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                if (httpEntity != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    //    得到文件的总长度
                    long file_length = httpEntity.getContentLength();
                    //    每次读取后累加的长度
                    long total_length = 0;
                    int length = 0;
                    //    每次读取1024个字节
                    byte[] data = new byte[1024];
                    inputStream = httpEntity.getContent();
                    while (-1 != (length = inputStream.read(data))) {
                        //    每读一次，就将total_length累加起来
                        total_length += length;
                        //    边读边写到ByteArrayOutputStream当中
                        byteArrayOutputStream.write(data, 0, length);
                        //    得到当前图片下载的进度
                        int progress = ((int) (total_length / (float) file_length) * 100);
                        //    时刻将当前进度更新给onProgressUpdate方法
                        publishProgress(progress);
                        if (isCancelled()) {
                            break;
                        }
                    }
                }
                image = byteArrayOutputStream.toByteArray();
                inputStream.close();
                byteArrayOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
            return image;
        }

        // 任务取消是调用
        @Override
        protected void onCancelled() {
            super.onCancelled();
            dimissProgressDialog();
        }

        // 在UI线程中执行，在异步线程中调用publishProgress(Progress)后会立即回调onPregressUpdate方法，可以在该方法中更新UI界面上的任务执行进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            setLoadProgress(values[0]);
        }

        // 任务执行完毕调用该方法，在UI线程中执行，更新数据到UI界面
        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            downloadImageSuccessCallBack(bytes);
        }
    }
}
