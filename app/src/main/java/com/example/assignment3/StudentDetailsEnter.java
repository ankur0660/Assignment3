package com.example.assignment3;

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

public class StudentDetailsEnter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details_enter);
        final EditText nameEdit=findViewById(R.id.nameEdit);

        final EditText rollEdit=findViewById(R.id.rollEdit);

        if(getIntent().hasCategory("view")||getIntent().hasCategory("edit")||getIntent().hasCategory("delete")){

    Log.d("ffff",String.valueOf(getIntent().hasCategory("view")));

           final Student stu=MainActivity.student.get(getIntent().getIntExtra("position",0));
            Button btn=findViewById(R.id.btn);
            if(getIntent().hasCategory("view")){
                nameEdit.setEnabled(false);
                rollEdit.setEnabled(false);

                btn.setVisibility(View.GONE);
            }

            else
            {

                btn.setText("EDIT");
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stu.setName(nameEdit.getText().toString());
                        stu.setRoll(rollEdit.getText().toString());
                        MainActivity.studentAdapter.notifyDataSetChanged();
                        finish();
                    }
                });
            }


nameEdit.setText("Name: "+ stu.getName());
nameEdit.setGravity(Gravity.CENTER);
nameEdit.setTypeface(null,Typeface.BOLD);
nameEdit.setTypeface(null,Typeface.ITALIC);
nameEdit.setTextSize(50);


rollEdit.setText("Roll No :" + stu.getRoll());
rollEdit.setGravity(Gravity.CENTER);
rollEdit.setGravity(Gravity.CENTER);
rollEdit.setTypeface(null,Typeface.BOLD);
rollEdit.setTypeface(null,Typeface.ITALIC);
rollEdit.setTextSize(50);
        }


    }
    void saveStudent(View view){
        EditText nme= ((EditText)findViewById(R.id.nameEdit));
        String name=nme.getText().toString();
        EditText roll= ((EditText)findViewById(R.id.rollEdit));
        String Roll=roll.getText().toString();



        //String Roll= ((EditText)findViewById(R.id.name)).getText().toString();
        MainActivity.student.add(new Student(name,Roll));
        MainActivity.studentAdapter.notifyDataSetChanged();
        Toast toast=Toast.makeText(this,"Student Added",Toast.LENGTH_LONG);
        toast.show();

        finish();



    }
}
