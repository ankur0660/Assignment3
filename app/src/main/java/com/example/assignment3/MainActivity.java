package com.example.assignment3;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.assignment3.Adapter.StudentAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    final int SORT_BY_NAME=0;
    final int SORT_BY_ROLL=1;
 public static ArrayList<Student> student=new ArrayList<>();
     RecyclerView recyclerView;
   public static StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=(RecyclerView) findViewById(R.id.recycle);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
studentAdapter=new StudentAdapter(student,this);
        recyclerView.setAdapter(studentAdapter);

    }

    void addStudent(View view){
        Intent i=new Intent(this,StudentDetailsEnter.class);
        startActivity(i);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actions_menu,menu);
        getSupportActionBar().setTitle("Students");



        final MenuItem switchItem = menu.findItem(R.id.switcher);
switchItem.setActionView(R.layout.switch_layout);
        MenuItem spinnerItem = menu.findItem(R.id.spinnerMenu);
        spinnerItem.setActionView(R.layout.spinn);


        Switch swtch=switchItem.getActionView().findViewById(R.id.switchLayout);
        Spinner spinner=spinnerItem.getActionView().findViewById(R.id.spinnerLayout);

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));


                }
                else
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }

        });
spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case SORT_BY_NAME:

                Collections.sort(student, new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.getName().compareToIgnoreCase(o2.getName());

                    }
                });
                studentAdapter.notifyDataSetChanged();
                break;
            case SORT_BY_ROLL:
                Collections.sort(student, new Comparator<Student>() {
                    @Override
                    public int compare(Student o1, Student o2) {
                        return o1.getRoll().compareTo(o2.getRoll());

                    }
                });

studentAdapter.notifyDataSetChanged();
break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {





        return super.onOptionsItemSelected(item);
    }
}
