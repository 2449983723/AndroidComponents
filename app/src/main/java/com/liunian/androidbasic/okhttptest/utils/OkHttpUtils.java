package com.liunian.androidbasic.okhttptest.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by dell on 2018/9/12.
 */

public class OkHttpUtils {
    private static OkHttpClient okHttpClient;

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient();
                }
            }
        }
        return okHttpClient;
    }

    private static Executor mainThreadExecutor;

    private static Executor getMainThreadExecutor() {
        if (mainThreadExecutor == null) {
            synchronized (MainThreadExecutor.class) {
                if (mainThreadExecutor == null) {
                    mainThreadExecutor = new MainThreadExecutor();
                }
            }
        }
        return mainThreadExecutor;
    }

    public static void sendGetRequest(String url, final OkHttpCallback callback) {
        OkHttpClient client = getOkHttpClient();
        Request request = new Request.Builder().url(url)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(final okhttp3.Call call, final IOException e) {
                if (callback != null) {
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(final okhttp3.Call call, final okhttp3.Response response) throws IOException {
                if (callback != null) {
                    final String responseString = response.body().string();
                    getMainThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResponse(responseString);
                        }
                    });
                }
            }
        });
    }

    public interface OkHttpCallback {
        void onFailure(IOException e);

        void onResponse(String responseString);
    }

    static class MainThreadExecutor implements Executor {
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            handler.post(command);
        }
    }
}
