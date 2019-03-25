package com.example.assignment3;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment3.model.Student;
import com.example.assignment3.service.CustomIntentService;
import com.example.assignment3.service.dataStoreService;
import com.example.assignment3.task.StudentDataBaseUpdateTask;
import com.example.assignment3.util.Constants;

import java.util.ArrayList;

import static com.example.assignment3.util.Constants.NAME_EXTRA;
import static com.example.assignment3.util.Constants.ROLL_EXTRA;


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
    private final int SERVICE = 0;
    private final int INTENT_SERVICE = 1;
    private final int ASYNC_TASK = 2;


    private String mode = Constants.ACTIVITY_NEW_MODE;
    private String initialRoll;
    private ContentValues record;


    /*
     @Params View Button
     on Click function for adding a new student
     */
    public Intent makeAndSendStudent() {
        intent = new Intent();

        record = new ContentValues();
        name = nameEdit.getText().toString();
        roll = rollEdit.getText().toString();
        if (uniqueValidation()) {

            intent.putExtra(NAME_EXTRA, name);
            intent.putExtra(ROLL_EXTRA, roll);
            setResult(RESULT_OK, intent);
            if (mode.equals(Constants.ACTIVITY_NEW_MODE)) {

                intent.putExtra("mode", Constants.ACTIVITY_NEW_MODE);
                intent.setClass(this, dataStoreService.class);
                return intent;

            } else {
                intent.putExtra("mode", Constants.ACTIVITY_EDIT_MODE);
                intent.putExtra("initialRoll", initialRoll);

                return intent;


            }


        } else
            return null;


    }
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
        studentArrayList = (ArrayList<Student>) getIntent().getSerializableExtra("stuArrList");
        btn.setText(getString(R.string.add_new_student));
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showAlert();

//                makeAndSendStudent();
            }
        });

    }

    private void showAlert() {
        String choice[] = {getString(R.string.service_dialog), getString(R.string.intservice_dialog), getString(R.string.async_dialog)};
        final Intent intent = makeAndSendStudent();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case SERVICE:
                        intent.setClass(StudentDetailsActivity.this, dataStoreService.class);
                        startService(intent);
                        finish();
                        break;
                    case INTENT_SERVICE:
                        Log.d("iiiiiiiiiiiiii", "iiiiiiiiii");
                        intent.setClass(StudentDetailsActivity.this, CustomIntentService.class);
                        startService(intent);

                        finish();
                        break;
                    case ASYNC_TASK:
                        new StudentDataBaseUpdateTask().execute(intent);
                        finish();
                        break;

                }

            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_details_enter);
        init();

        if (getIntent().hasCategory(Constants.ACTIVITY_EDIT_MODE) || getIntent().hasCategory(Constants.ACTIVITY_VIEW_MODE)) {


            if (getIntent().hasCategory(Constants.ACTIVITY_VIEW_MODE)) {
                modifyUserInterface();
            } else {
                nameEdit.setText(studentArrayList.get(POSITION).getName());

                rollEdit.setText(studentArrayList.get(POSITION).getRoll());
                initialRoll = studentArrayList.get(POSITION).getRoll();
                btn.setText(getString(R.string.Edit));
                mode = Constants.ACTIVITY_EDIT_MODE;


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
