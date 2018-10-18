package com.liunian.androidbasic.dynamictest;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dell on 2018/9/13.
 */

public class DynamicProxy implements InvocationHandler {
    private Object needProxyObject;

    public Object getProxyObject(Object needProxyObject) {
        this.needProxyObject = needProxyObject;
        return Proxy.newProxyInstance(needProxyObject.getClass().getClassLoader(), needProxyObject.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i("liunianprint:", "代购开始买东西了");
        return method.invoke(this.needProxyObject, args);
    }
}
