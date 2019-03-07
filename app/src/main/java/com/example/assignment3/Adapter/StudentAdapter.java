package com.example.assignment3.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.example.assignment3.Student;
import com.example.assignment3.StudentDetailsEnter;

import java.util.ArrayList;

public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.viewholder>{
ArrayList<Student> students;
Context ctx;
final String CAT_VIEW="view";
final String CAT_EDIT="edit";
public StudentAdapter(ArrayList<Student> students, Context ctx){
this.students=students;
this.ctx=ctx;

}


        @NonNull
        @Override

        public StudentAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflator=LayoutInflater.from(viewGroup.getContext());
            String sa=viewGroup.getClass().toString();

            Log.d("abc",sa);


            View view= (View) inflator.inflate(R.layout.student_view,viewGroup,false);


           // Log.d("abc",view.getContext().toString());


            return new viewholder(view,viewGroup.getContext());
        }

        @Override
        public void onBindViewHolder(@NonNull StudentAdapter.viewholder viewHolder, int i) {
viewHolder.name.setText(students.get(i).getName());
    viewHolder.roll.setText(students.get(i).getRoll());


        }

        @Override
        public int getItemCount() {
            return students.size();

        }

        public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView name,roll;

            public viewholder(@NonNull View itemView,Context ctx) {

                super(itemView);
itemView.setOnClickListener(this);
                name= itemView.findViewById(R.id.nameedit);
                roll= itemView.findViewById(R.id.Roll);


            }

            @Override
            public void onClick(View v) {
                String[] options={"View","Edit","Delete"};
                AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which){
                       case 0:
                           ctx.startActivity(new Intent(ctx,StudentDetailsEnter.class).addCategory(CAT_VIEW).putExtra("position", getAdapterPosition()));
                      break;
                       case 1:

                           ctx.startActivity(new Intent(ctx,StudentDetailsEnter.class).addCategory(CAT_EDIT).putExtra("position", getAdapterPosition()));
                           break;
                            case 2:
                            MainActivity.student.remove(getAdapterPosition());
                            MainActivity.studentAdapter.notifyDataSetChanged();



                   }
                    }
                });
                AlertDialog dialog=builder.create();
dialog.show();


 //    int position=getAdapterPosition();
   //   Toast toast=Toast.makeText(ctx,students.get(position).getRoll(),Toast.LENGTH_LONG);
     // toast.show();


            }
        }
    }

