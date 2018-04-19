package com.liunian.androidbasic.designpattern.singleton;

/**
 * Created by dell on 2018/4/18.
 * 单例模式：双重校验锁
 * 优点：线程安全，懒加载（只有需要单例对象时才去生成），效率高
 * 缺点：判断较多，实现麻烦
 */

public class SingleTonFour {
    private volatile static SingleTonFour singleTonFour = null; // 加上volatile关键字，线程每次使用到被volatile关键字修饰的变量时，都会去堆里拿最新的数据

    private SingleTonFour() {

    }

    public static SingleTonFour getInstance() {
        if (singleTonFour == null) { // 在懒汉式同步锁的基础上加上了一个判断，如果单例对象不为空，就不需要执行获得对象同步锁的代码，从而提高效率
            synchronized (SingleTonFour.class) { // 只有当单例对象为空时才会执行
                if (singleTonFour == null) {
                    singleTonFour = new SingleTonFour();
                }
            }
        }
        return singleTonFour;
    }
}
