package com.liunian.androidbasic.designpattern.build;

/**
 * Created by dell on 2018/4/19.
 */

public class Person {
    private String name;
    private int age;
    private double weight;
    private double height;

    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.weight = builder.weight;
        this.height = builder.height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public static class Builder {
        private String name;
        private int age;
        private double weight;
        private double height;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setAge(int age) {
            this.age = age;
            return this;
        }

        public Builder setWeight(double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setHeight(double height) {
            this.height = height;
            return this;
        }

        public Person build() {
            Person person = new Person(this);
            return person;
        }
    }
}
