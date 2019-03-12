package com.example.assignment3.Adapter;

import android.app.AlertDialog;
import android.app.usage.UsageEvents;
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
import com.example.assignment3.StudentDetailsActivity;


import java.util.ArrayList;

public class StudentAdapter  extends RecyclerView.Adapter<StudentAdapter.viewholder>{
ArrayList<Student> students;
Context ctx;

private clickRecycleItemListener mListener;

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
                mListener=(clickRecycleItemListener)ctx;
                mListener.onItemClick(getAdapterPosition());
            }
        }

public interface clickRecycleItemListener{
    void onItemClick(int position);

}
}

