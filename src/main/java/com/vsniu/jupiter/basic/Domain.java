package com.vsniu.jupiter.basic;

import java.io.Serializable;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/11 21:20
 * @Description:
 */
public class Domain implements Serializable {
    //如果有serialVersionUID这个字段，在反序列化的时候，此类新增字段是不会报错的，否则会报错
    private static final long serialVersionUID = 5969192948961213109L;
    private int age;
    private String name;
    private double score;

    private String uu;

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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getUu() {
        return uu;
    }

    public void setUu(String uu) {
        this.uu = uu;
    }

    public Domain(int age, String name, double score) {
        this.age = age;
        this.name = name;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", uu='" + uu + '\'' +
                '}';
    }
}
