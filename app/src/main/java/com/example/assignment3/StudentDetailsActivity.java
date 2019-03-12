package com.example.assignment3;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment3.Adapter.StudentAdapter;

import java.util.ArrayList;
import java.util.Iterator;

public class StudentDetailsActivity extends AppCompatActivity {

    private final boolean VALID = true;
    private final boolean INVALID = false;
    private Intent intent;
    private ArrayList<Student> studentArrayList;
    private EditText nameEdit;
    private EditText rollEdit;
    private Button btn;
    private String name;
    private String roll;
    private Integer POSITION;

    private final String ROLLEXTRA = "rollEdit";
    private final String NAMEEXTRA = "nameEdit";


    /*
 initialize ArrayList,intent,poisition,rollEdit,nameEdit,Button
 */

    private void init() {
        intent = getIntent();
        if (getIntent().hasExtra("position")) {
            POSITION = getIntent().getIntExtra("position", -1);
        }
        nameEdit = findViewById(R.id.nameEdit);
        rollEdit = findViewById(R.id.rollEdit);
        btn = findViewById(R.id.btn);
        btn.setText(getString(R.string.add_new_student));
        studentArrayList = (ArrayList<Student>) getIntent().getSerializableExtra("stuArrList");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details_enter);
        init();

        if (getIntent().hasCategory("view") || getIntent().hasCategory("edit")) {  //krna


            if (getIntent().hasCategory("view")) {
                modifyUserInterface();
            } else {
                nameEdit.setText(studentArrayList.get(POSITION).getName());
                rollEdit.setText(studentArrayList.get(POSITION).getRoll());
                btn.setText(getString(R.string.Edit));


                btn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        name = nameEdit.getText().toString();
                        roll = rollEdit.getText().toString();
                        if (uniqueValidation()) {

                            intent.putExtra(NAMEEXTRA, nameEdit.getText().toString());
                            intent.putExtra(ROLLEXTRA, rollEdit.getText().toString());
                            setResult(RESULT_OK, intent);
                            finish();
                        }


                    }
                });
            }


        }


    }

    /*
    Modify the existing  user interface on edit request of user
    */
    private void modifyUserInterface() {
        nameEdit.setEnabled(false);
        rollEdit.setEnabled(false);

        btn.setVisibility(View.GONE);

        nameEdit.setText(String.format("Name: %s", intent.getStringExtra(NAMEEXTRA)));
        nameEdit.setGravity(Gravity.CENTER);
        nameEdit.setTypeface(null, Typeface.BOLD);
        nameEdit.setTypeface(null, Typeface.ITALIC);
        nameEdit.setTextSize(50);


        rollEdit.setText("Roll No :" + intent.getStringExtra(ROLLEXTRA));
        rollEdit.setGravity(Gravity.CENTER);

        rollEdit.setGravity(Gravity.CENTER);
        rollEdit.setTypeface(null, Typeface.BOLD);
        rollEdit.setTypeface(null, Typeface.ITALIC);
        rollEdit.setTextSize(50);
    }

    

    /*
     @Params View Button
     on Click function for adding a new student
     */
    public void onClickSave(View view) {


        name = nameEdit.getText().toString();
        roll = rollEdit.getText().toString();
        if (uniqueValidation()) {
//MainActivity.debug();
            intent.putExtra("nameEdit", name);
            intent.putExtra("rollEdit", roll);
            MainActivity.debug();
            setResult(RESULT_OK, intent);

            Toast toast = Toast.makeText(this, "Student Added", Toast.LENGTH_LONG);
            toast.show();
            finish();

        }


    }


    /*
    @return boolean VALID OR INVALID respectively for true or false
    if the entered roll no is unique or not
    */
    boolean uniqueValidation() {
        if (nameEdit.getText().toString().isEmpty()) {
            nameEdit.setError("enter a name");
            return INVALID;
        }

        if (rollEdit.getText().toString().isEmpty()) {
            rollEdit.setError("enter a Roll No");
            return INVALID;


        }
        int counter = 0;

        if (studentArrayList.size() == 0) {

            return VALID;
        }

        for (Student student : studentArrayList) {
            if (POSITION != null) {

                if (counter == POSITION) {

                    counter++;
                    continue;
                }

            }

            if (student.getRoll().equals(roll)) {

                rollEdit.setError("duplicate roll");
                counter = -1;
                break;
            }
            counter++;

        }
        if (counter == -1) {
            return INVALID;
        } else
            return VALID;


    }
}
