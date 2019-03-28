package com.example.assignment3.fragment;

import android.arch.lifecycle.ViewModel;
import android.support.v4.view.ViewPager;

import com.example.assignment3.model.Student;
import com.example.assignment3.util.Constants;

import java.util.ArrayList;

public class ShowStudentViewModel extends ViewModel {
private ArrayList<Student> student=new ArrayList<>();
private int position;
public String mode= Constants.ACTIVITY_NEW_MODE;
private ViewPager viewpager;


public void setStudentList(ArrayList<Student> student) {
    this.student=student;
    }

    public ArrayList<Student> getStudentList() {
        return student;
    }

    public void setPositionClicked(int position){
    this.position=position;

    }

    public Integer getpositionClicked() {
    return position;
    }

    public void setViewpager(ViewPager viewpager) {
        this.viewpager = viewpager;
    }

    public ViewPager getViewPager() {return viewpager;

    }
}
