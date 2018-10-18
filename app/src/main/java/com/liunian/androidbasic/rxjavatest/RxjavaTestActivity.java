package com.liunian.androidbasic.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.liunian.androidbasic.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxjavaTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_test);

        Log.i("liunianprint:", "mainthreadid " + Thread.currentThread().getId());

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Exception {
                Log.i("liunianprint:", "subscribe threadid " + Thread.currentThread().getId());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("我是事件" + integer + "拆分后的子事件" + i);
                }
                return Observable.fromIterable(list);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("liunianprint:", "onSubscribe" + Thread.currentThread().getId());
            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i("liunianprint:", "onNext" + s + " " + Thread.currentThread().getId());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("liunianprint:", "onError");
            }

            @Override
            public void onComplete() {
                Log.i("liunianprint:", "onComplete");
            }
        });

    }
}
