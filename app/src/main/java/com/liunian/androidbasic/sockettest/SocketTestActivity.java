package com.liunian.androidbasic.sockettest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liunian.androidbasic.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketTestActivity extends AppCompatActivity {
    private ExecutorService mThreadPool;
    Button mSendButton;
    EditText mSendEditText;
    TextView mReceiveTextView;
    Socket mSocket;
    OutputStream mOutputStream;
    BufferedReader mBufferedReader;
    MyHandler mMyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_test);
        mMyHandler = new MyHandler(this);
        mSendButton = (Button) findViewById(R.id.send_button);
        mSendEditText = (EditText) findViewById(R.id.send_edit_text);
        mReceiveTextView = (TextView) findViewById(R.id.receive_text_view);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendText = mSendEditText.getText().toString();
                mSendEditText.setText("");
                sendData(sendText);
            }
        });
        mThreadPool = Executors.newCachedThreadPool(); // 创建一个线程池处理异步任务
        createConnection();
    }

    private void createConnection() { // 连接服务端
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    mSocket = new Socket("10.41.3.17", 9999); // 创建一个Socket，指定服务端的IP和端口
                    if (mSocket.isConnected()) { // 如果连接成功则在线程池中执行接收服务端数据的任务
                        receiveData();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void receiveData() { // 在线程池中执行接收服务端数据的任务
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (true) { // 循环等待服务端发送数据
                    if (mSocket != null && mSocket.isConnected()) {
                        InputStream inputStream = null;
                        try {
                            inputStream = mSocket.getInputStream();
                            InputStreamReader isr = new InputStreamReader(inputStream);
                            mBufferedReader = new BufferedReader(isr);
                            // 通过输入流读取器对象 接收服务器发送过来的数据
                            String responseString = mBufferedReader.readLine();
                            if (responseString != null) { // 将收到的服务端数据通过Handler发送给主线程
                                Message message = Message.obtain();
                                message.what = MyHandler.RECEIVE_MESSAGE_TYPE;
                                message.obj = responseString;
                                mMyHandler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }
        });
    }

    private void sendData(final String sendText) { // 在线程池中执行发送数据任务
        if (sendText != null && !sendText.contentEquals("")) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    if (mSocket != null && mSocket.isConnected()) {
                        try {
                            mOutputStream = mSocket.getOutputStream();
                            mOutputStream.write(sendText.getBytes());
                            mOutputStream.flush();
                            if (sendText.contentEquals("exit")) {
                                closeConnection();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void closeConnection() { // 关闭连接
        if (mSocket != null) {
            try {
                mSocket.shutdownInput();
                mSocket.shutdownOutput();
                mSocket.close();
                mSocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mBufferedReader != null) {
            try {
                mBufferedReader.close();
                mBufferedReader = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
                mOutputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendData("exit");
    }

    // Handler类，声明为static，避免持有外部类的直接引用，防止内存泄露
    private static class MyHandler extends Handler {
        public static final int RECEIVE_MESSAGE_TYPE = 1;
        private WeakReference<SocketTestActivity> activityWeakReference; // 持有外部类的弱引用

        public MyHandler(SocketTestActivity activity) {
            activityWeakReference = new WeakReference<SocketTestActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyHandler.RECEIVE_MESSAGE_TYPE: // 将接收到的服务端数据更新到UI界面上
                    SocketTestActivity activity = activityWeakReference.get();
                    if (activity != null) {
                        String responseString = (String) msg.obj;
                        if (!TextUtils.isEmpty(responseString)) {
                            activity.mReceiveTextView.setText(responseString);
                        }
                    }
                    break;
            }
        }
    }
}
