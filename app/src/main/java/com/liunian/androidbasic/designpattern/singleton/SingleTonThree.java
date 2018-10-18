package com.liunian.androidbasic.designpattern.singleton;

/**
 * Created by dell on 2018/4/18.
 * 单例模式：懒汉式同步锁
 * 懒汉式同步锁是为了解决懒汉式模式线程安全的问题，在懒汉式模式上加上线程同步锁，确保只生成一个单例对象
 */

public class SingleTonThree {
    private static SingleTonThree singleTonThree = null;

    private SingleTonThree() {

    }

    public static SingleTonThree getInstance() {
        synchronized (SingleTonThree.class) { // 加上线程同步锁，确保每次只有一个线程进入
            if (singleTonThree == null) {
                singleTonThree = new SingleTonThree();
            }
        }
        return singleTonThree;
    }
}
