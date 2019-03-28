package com.example.assignment3.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.example.assignment3.model.Student;


import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.viewholder> {
    ArrayList<Student> students;
    Object ctx;

    private clickRecycleItemListener mListener;

    public StudentAdapter(ArrayList<Student> students, Object ctx) {
        this.students = students;
        this.ctx = ctx;

    }

    @NonNull
    @Override

    public StudentAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.student_view, viewGroup, false);

        return new viewholder(view, viewGroup.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.viewholder viewHolder, int i) {
        Log.d("aaa", String.valueOf(i));

        viewHolder.name.setText(students.get(i).getName());
        viewHolder.roll.setText(students.get(i).getRoll());


    }

    @Override
    public int getItemCount() {
        return students.size();

    }

    public class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, roll;

        public viewholder(@NonNull View itemView, Context ctx) {

            super(itemView);
            itemView.setOnClickListener(this);
            name = itemView.findViewById(R.id.nameedit);
            roll = itemView.findViewById(R.id.Roll);


        }

        @Override
        public void onClick(View v) {
            mListener = (clickRecycleItemListener) ctx;
            mListener.onItemClick(getAdapterPosition());
        }
    }

    public interface clickRecycleItemListener {
        void onItemClick(int position);

    }
}

