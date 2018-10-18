package com.liunian.androidbasic.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liunian.androidbasic.R;
import com.liunian.androidbasic.retrofittest.api.TranslateApi;
import com.liunian.androidbasic.retrofittest.bean.TranslateBean;
import com.liunian.androidbasic.retrofittest.utils.RetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RetrofitTestActivity extends AppCompatActivity {
    EditText mInputEditText;
    Button mTranlateButton;
    TextView mOutputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);

        mInputEditText = (EditText) findViewById(R.id.input_text);
        mOutputText = (TextView) findViewById(R.id.output_text);
        mTranlateButton = (Button) findViewById(R.id.translate_button);
        mTranlateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTranslate();
            }
        });
    }

    private void requestTranslate() {
        Retrofit retrofit = RetrofitUtil.getRetrofitInstance();
        TranslateApi translateApi = retrofit.create(TranslateApi.class);
//        Call<TranslateBean> call = translateApi.translateRequest(mInputEditText.getText().toString());
//        call.enqueue(new Callback<TranslateBean>() {
//            @Override
//            public void onResponse(Call<TranslateBean> call, Response<TranslateBean> response) {
//                TranslateBean translateBean = response.body();
//                if (translateBean.getContent().getOut() != null) {
//                    mOutputText.setText(translateBean.getContent().getOut());
//                } else {
//                    mOutputText.setText(translateBean.getContent().getWordMeanString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TranslateBean> call, Throwable t) {
//                mOutputText.setText("翻译出错了！");
//            }
//        });

        Observable<TranslateBean> observable = translateApi.translateRxRequest(mInputEditText.getText().toString());
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<TranslateBean>() {

            @Override
            public void onError(Throwable throwable) {
                mOutputText.setText("翻译出错了！");
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(TranslateBean translateBean) {
                if (translateBean.getContent().getOut() != null) {
                    mOutputText.setText(translateBean.getContent().getOut());
                } else {
                    mOutputText.setText(translateBean.getContent().getWordMeanString());
                }
            }
        });
    }
}
