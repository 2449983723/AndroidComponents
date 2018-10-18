package com.liunian.androidbasic.dynamictest;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by dell on 2018/9/13.
 */

public class DynamicProxyUtil {
    public static <T> T getProxyObject(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.i("liunianprint:", "这里要执行" + method.getDeclaringClass().getName() + "类" + "的" + method.getName() + "方法对应的逻辑，参数如下：");
                if (args != null) {
                    for (int i = 0; i < args.length; i++) {
                        Log.i("liunianprint:", args[i] + "");
                    }
                }
                return null;
            }
        });
    }
}
