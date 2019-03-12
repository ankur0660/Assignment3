package com.example.assignment3;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


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
    private final String CAT_VIEW="view";
    private final String CAT_EDIT="edit";

    private final String ROLL_EXTRA = "rollEdit";
    private final String NAME_EXTRA = "nameEdit";


    /*
 initialize ArrayList,intent,poisition,rollEdit,nameEdit,Button
 */
    /*
     @Params View Button
     on Click function for adding a new student
     */
    public void onClickSave(View view) {


        name = nameEdit.getText().toString();
        roll = rollEdit.getText().toString();
        if (uniqueValidation()) {
//MainActivity.debug();
            intent.putExtra(NAME_EXTRA, name);
            intent.putExtra(ROLL_EXTRA, roll);
            MainActivity.debug();
            setResult(RESULT_OK, intent);

            Toast toast = Toast.makeText(this, getString(R.string.toast_stu_added), Toast.LENGTH_LONG);
            toast.show();
            finish();

        }


    }


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

        if (getIntent().hasCategory(CAT_EDIT) || getIntent().hasCategory(CAT_VIEW)) {


            if (getIntent().hasCategory(CAT_VIEW)) {
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

                            intent.putExtra(NAME_EXTRA, nameEdit.getText().toString());
                            intent.putExtra(ROLL_EXTRA, rollEdit.getText().toString());
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

        nameEdit.setText(String.format("Name: %s", intent.getStringExtra(NAME_EXTRA)));
        nameEdit.setGravity(Gravity.CENTER);
        nameEdit.setTypeface(null, Typeface.BOLD);
        nameEdit.setTypeface(null, Typeface.ITALIC);
        nameEdit.setTextSize(50);


        rollEdit.setText("Roll No :" + intent.getStringExtra(ROLL_EXTRA));
        rollEdit.setGravity(Gravity.CENTER);

        rollEdit.setGravity(Gravity.CENTER);
        rollEdit.setTypeface(null, Typeface.BOLD);
        rollEdit.setTypeface(null, Typeface.ITALIC);
        rollEdit.setTextSize(50);
    }




    /*
    @return boolean VALID OR INVALID respectively for true or false
    if the entered roll no is unique or not
    */
    boolean uniqueValidation() {
        if (nameEdit.getText().toString().isEmpty()) {
            nameEdit.setError(getString(R.string.error_name));
            return INVALID;
        }

        if (rollEdit.getText().toString().isEmpty()) {
            rollEdit.setError(getString(R.string.error_roll));
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

                rollEdit.setError(getString(R.string.dup_error));
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
