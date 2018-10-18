package com.liunian.androidbasic.designpattern.singleton;

/**
 * Created by dell on 2018/4/19.
 * 枚举单例模式
 * 优点：线程安全，不用担心序列化和反射问题
 * 缺点：枚举占用的内存会多一点
 */

public enum SingleTonSix {
    INSTANCE;

    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
