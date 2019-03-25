package com.example.assignment3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.assignment3.database.DbHelper;
import com.example.assignment3.database.tables.StudentTable;
import com.example.assignment3.model.Student;
import com.example.assignment3.util.Constants;

import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.assignment3.Adapter.StudentAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements StudentAdapter.clickRecycleItemListener {
    private final int SORT_BY_NAME = 0;
    private final int SORT_BY_ROLL = 1;
    private final int ADD_NEW_STUDENT = 0;
    private final int VIEW = 0;
    private final int EDIT = 1;
    private final int DELETE = 2;
    private final String CAT_VIEW = "view";
    private final String CAT_EDIT = "edit";
    private int position;
    private ArrayList<Student> student = new ArrayList<>();
    private RecyclerView recyclerView;
    private Intent intent;


    private static StudentAdapter studentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Constants.dbHelper = new DbHelper(this);
        student = Constants.dbHelper.getAllStudents(StudentTable.TABLE_NAME);
        intent = new Intent(MainActivity.this, StudentDetailsActivity.class);

        recyclerView = findViewById(R.id.recycle);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter(student, this);
        recyclerView.setAdapter(studentAdapter);

    }

    public void addStudent(View view) {
        intent.putExtra("stuArrList", student);
        intent.removeCategory(CAT_EDIT);
        intent.removeCategory(CAT_VIEW);
        startActivityForResult(intent, ADD_NEW_STUDENT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Student newStud;
        if (resultCode == RESULT_OK) {
            newStud = new Student(data.getStringExtra("nameEdit"), data.getStringExtra("rollEdit"));
            switch (requestCode) {


                case ADD_NEW_STUDENT:


                    student.add(newStud);
                    studentAdapter.notifyDataSetChanged();
                    break;
                case EDIT:

                    Student s = student.get(position);
                    s.setRoll(newStud.getRoll());
                    s.setName(newStud.getName());
                    studentAdapter.notifyDataSetChanged();
                    break;


            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actions_menu, menu);
        getSupportActionBar().setTitle("Students");


        final MenuItem switchItem = menu.findItem(R.id.switcher);
        switchItem.setActionView(R.layout.switch_layout);
        MenuItem spinnerItem = menu.findItem(R.id.spinnerMenu);
        spinnerItem.setActionView(R.layout.spinn);


        Switch swtch = switchItem.getActionView().findViewById(R.id.switchLayout);
        Spinner spinner = spinnerItem.getActionView().findViewById(R.id.spinnerLayout);

        swtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));


                } else
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

            }

        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
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


    public static void debug() {
        Log.d("aaa", "rrrr");
    }

    /*
    On item click in recycler view
    */
    @Override
    public void onItemClick(final int position) {


        this.position = position;

        intent.putExtra("position", position);
        String[] options = {"View", "Edit", "Delete"};
        // final Intent intent=new Intent(MainActivity.this,StudentDetailsActivity.class);
        intent.putExtra("nameEdit", student.get(position).getName());
        intent.putExtra("rollEdit", student.get(position).getRoll());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case VIEW:
                        //debug();
                        intent.removeCategory(CAT_EDIT);
                        intent.addCategory(CAT_VIEW);
                        //debug();
                        MainActivity.this.startActivity(intent);
                        break;
                    case EDIT:
                        intent.removeCategory(CAT_VIEW);
                        intent.addCategory(CAT_EDIT);
                        intent.putExtra("stuArrList", student);
                        MainActivity.this.startActivityForResult(intent, EDIT);


                        break;
                    case DELETE:
                        Constants.dbHelper.deleteQuery(new StudentTable(), StudentTable.COL_ROLL + "=?", new String[]{student.get(position).getRoll()});
                        student.remove(position);

                        MainActivity.studentAdapter.notifyDataSetChanged();


                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
