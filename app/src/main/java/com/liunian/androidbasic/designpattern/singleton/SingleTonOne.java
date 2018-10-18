package com.liunian.androidbasic.designpattern.singleton;

/**
 * Created by dell on 2018/4/18.
 * 单例模式：饿汉式
 * 饿汉式是最简单的单例模式定义方法，首先我们需要将类的构造方法定义为private，这样在外部就不能通过构造函数生成对象；
 * 其次，我们需要定义一个静态的对象并用构造方法对其赋值；
 * 最后，提供一个public方法访问我们定义的静态对象。
 * <p>
 * 优点：简单
 * 缺点：不管程序中是否使用到了单例对象，都会生成单例对象，并且由于静态对象是在类加载时就需要生成，会降低应用的启动速度
 * 适用：类对象功能简单，占用内存较小，使用频繁
 * 不适用：类对象功能复杂，占用内存大，使用概率较低
 */

public class SingleTonOne {
    private final static SingleTonOne singleTonOne = new SingleTonOne();

    private SingleTonOne() {
    }

    public static SingleTonOne getInstance() {
        return singleTonOne;
    }
}
