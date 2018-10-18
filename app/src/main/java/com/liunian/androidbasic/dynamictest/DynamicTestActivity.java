package com.liunian.androidbasic.dynamictest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liunian.androidbasic.R;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_test);

//        Buyer1 buyer1 = new Buyer1();
//        Subject proxyObjectOne = (Subject) new DynamicProxy().getProxyObject(buyer1);
//        proxyObjectOne.buy();
//
//        Buyer2 buyer2 = new Buyer2();
//        Subject proxyObjectTwo = (Subject) new DynamicProxy().getProxyObject(buyer2);
//        proxyObjectTwo.buy();

        Subject subject = DynamicProxyUtil.getProxyObject(Subject.class);
        subject.buy("苹果");
    }
}
