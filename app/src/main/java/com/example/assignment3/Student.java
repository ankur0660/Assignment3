package com.example.assignment3;

import java.io.Serializable;


public class Student implements Serializable, Comparable,Cloneable {
    private String name;
    private String roll;
Student(String name, String roll){
    this.name=name;
    this.roll=roll;

}
    public String getRoll() {
        return roll;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    @Override
    public int compareTo(Object o) {
return 0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
