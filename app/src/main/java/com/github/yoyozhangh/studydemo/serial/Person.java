package com.github.yoyozhangh.studydemo.serial;

import java.io.Serializable;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class Person implements Serializable {


    private static final long serialVersionUID = -405670906533172853L;
    private int age;
    private String name;
    private String sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
