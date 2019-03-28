package com.example.assignment3.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.assignment3.Adapter.StudentAdapter;
import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.example.assignment3.StudentDetailsActivity;
import com.example.assignment3.database.DbHelper;
import com.example.assignment3.database.tables.StudentTable;
import com.example.assignment3.model.Student;
import com.example.assignment3.util.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowStudentFragment extends BaseFragment implements StudentAdapter.clickRecycleItemListener {

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
    private StudentAdapter studentAdapter;
    private ShowStudentViewModel mViewModel;
    private ViewPager viewPager;
    private Button btn;

    public static ShowStudentFragment newInstance() {
        return new ShowStudentFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.show_student_fragment, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ShowStudentViewModel.class);

        Constants.dbHelper = new DbHelper(getActivity());
        mViewModel.setStudentList(Constants.dbHelper.getAllStudents(StudentTable.TABLE_NAME));
        init();


    }

    private void init() {
//        mViewModel.setStudentList(student);
        student = mViewModel.getStudentList();
        recyclerView = getView().findViewById(R.id.recycle);
        btn = getView().findViewById(R.id.addbtnfrag1);

        viewPager = mViewModel.getViewPager();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        studentAdapter = new StudentAdapter(student, this);
        recyclerView.setAdapter(studentAdapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.mode = Constants.ACTIVITY_NEW_MODE;
                viewPager.setCurrentItem(Constants.EDIT_FRAGMENT);

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity.debug("frag1 pause");
    }

    @Override
    public void onResume() {

        super.onResume();
        MainActivity.debug("frag1 resume");
//        student = this.mViewModel.getStudentList();
//        MainActivity.debug();
//        studentAdapter.notifyDataSetChanged();

    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.debug("frag1 start");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.debug("frag1 attach");
    }

    public void refresh() {

        mViewModel.mode = Constants.ACTIVITY_NEW_MODE;
        student = mViewModel.getStudentList();
        MainActivity.debug("refresh" + student.size());
        studentAdapter.notifyDataSetChanged();
//studentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(final int position) {
        this.position = position;

        String[] options = {"View", "Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case VIEW:

                        break;
                    case EDIT:
                        mViewModel.mode = Constants.ACTIVITY_EDIT_MODE;
                        mViewModel.setPositionClicked(position);
                        viewPager.setCurrentItem(Constants.EDIT_FRAGMENT);
                        break;
                    case DELETE:
                        Constants.dbHelper.deleteQuery(new StudentTable(), StudentTable.COL_ROLL + "=?", new String[]{student.get(position).getRoll()});
                        student.remove(position);
                        studentAdapter.notifyDataSetChanged();
//                        mViewModel.setStudentList(student);


                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MainActivity.debug("optionsmenu");
        MenuInflater menuInflater = inflater;
        menuInflater.inflate(R.menu.actions_menu, menu);
        getActivity().setTitle("Students");


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
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


                } else
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                                if(Integer.parseInt(o1.getRoll())>Integer.parseInt(o2.getRoll()))
                                    return 1;
                                else
                                    return -1;


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


    }


}
