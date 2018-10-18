package com.liunian.androidbasic.compass;

/**
 * Created by dell on 2018/9/29.
 */

public class Height {
    private int id;
    private double left;
    private double right;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public double getLeft() {
        return this.left;
    }

    public void setLeft(double d) {
        this.left = d;
    }

    public double getRight() {
        return this.right;
    }

    public void setRight(double d) {
        this.right = d;
    }

    public String toString() {
        return "Height{id=" + this.id + ", left=" + this.left + ", right=" + this.right + '}';
    }
}