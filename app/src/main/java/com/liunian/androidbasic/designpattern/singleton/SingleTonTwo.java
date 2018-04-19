package com.liunian.androidbasic.designpattern.singleton;

/**
 * Created by dell on 2018/4/18.
 * 单例模式：懒汉式
 * 懒汉式相比于饿汉式的最大区别在于，单例对象的生成是在需要单例对象时才去构造的，而不是在应用启动的时候构造，
 * 如果应用中没有使用到单例对象，是不会构造单例对象的。
 *
 * 优点：单例对象的生成是在应用需要使用单例对象时才去构造，可以提高应用的启动速度
 * 缺点：不是线程安全的，如果多个线程同时调用getInstance方法，那么可能会生成多个单例对象
 * 适用：单例对象功能复杂，占用内存大，对应用的启动速度有要求
 * 不适用：多线程同时使用
 */

public class SingleTonTwo {
    private static SingleTonTwo singleTonTwo = null;

    private SingleTonTwo() {}

    public static SingleTonTwo getInstance() {
        if (singleTonTwo == null) { // 首次生成单例对象时，如果多个线程同时调用getInstance方法，这个条件针对多个线程同时成立，那么就会生成多个单例对象
            singleTonTwo = new SingleTonTwo();
        }
        return singleTonTwo;
    }
}
