package com.liunian.androidbasic.designpattern.singleton;

/**
 * Created by dell on 2018/4/18.
 * 单例模式：静态内部类
 * 利用静态内部类只用在使用时才会去加载这一特性，实现了单例对象的懒加载，并且程序的类加载是线程安全的
 * 优点：实现简单，懒加载，线程安全
 * 缺点：增加了一个静态内部类，apk文件增大
 */

public class SingleTonFive {
    /**
     * 私有静态内部类，程序只有当使用到静态内部类是才会去加载静态内部类，然后生成单例对象
     */
    private static class SingleTonFiveHolder {
        public static SingleTonFive singleTonFive = new SingleTonFive();
    }

    private SingleTonFive() {

    }

    /**
     * 只用调用了getInstance方法，程序中使用到了静态内部类SingleTonFiveHolder，才会去加载SingleTonFiveHolder，生成单例对象
     *
     * @return
     */
    public SingleTonFive getInstance() {
        return SingleTonFiveHolder.singleTonFive;
    }
}
