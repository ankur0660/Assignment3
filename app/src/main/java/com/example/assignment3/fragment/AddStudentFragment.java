package com.example.assignment3.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignment3.MainActivity;
import com.example.assignment3.R;
import com.example.assignment3.model.Student;
import com.example.assignment3.service.CustomIntentService;
import com.example.assignment3.service.dataStoreService;
import com.example.assignment3.task.StudentDataBaseUpdateTask;
import com.example.assignment3.util.Constants;

import java.util.ArrayList;

import static com.example.assignment3.util.Constants.NAME_EXTRA;
import static com.example.assignment3.util.Constants.ROLL_EXTRA;

public class AddStudentFragment extends BaseFragment {
    private final boolean VALID = true;
    private final boolean INVALID = false;
    private Intent intent;
    private ArrayList<Student> studentArrayList;
    private EditText nameEdit;
    private EditText rollEdit;
    private Button btn;
    private String name;
    private String roll;
    private Integer position;
    private final int SERVICE = 0;
    private final int INTENT_SERVICE = 1;
    private final int ASYNC_TASK = 2;


    private String mode;
    private String initialRoll;
    private ContentValues record;
    private ShowStudentViewModel mViewModel;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();
        MainActivity.debug("frag2 start");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity.debug("frag2 attach");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_add_fragment, container, false);
    }

    @Override
    public void onResume() {

        super.onResume();
        MainActivity.debug("farg2 resume");
        initViews();
    }

    @Override
    public void onPause() {
        super.onPause();
        MainActivity.debug("frag2 pause");
    }

    public void refresh() {

        initViews();

    }


    private void initViews() {

        mViewModel = ViewModelProviders.of(getActivity()).get(ShowStudentViewModel.class);
        mode = mViewModel.mode;
        position = mViewModel.getpositionClicked();
        nameEdit = getView().findViewById(R.id.nameEdit);
        rollEdit = getView().findViewById(R.id.rollEdit);
        btn = getView().findViewById(R.id.btn);
        studentArrayList = mViewModel.getStudentList();
        if (mode.equals(Constants.ACTIVITY_EDIT_MODE)) {
            btn.setText("Edit Student");
            initialRoll = studentArrayList.get(mViewModel.getpositionClicked()).getRoll();
            nameEdit.setText(studentArrayList.get(position).getName());
            rollEdit.setText(studentArrayList.get(position).getRoll());

        } else if (mode.equals(Constants.ACTIVITY_NEW_MODE)) {
            nameEdit.setText("");
            rollEdit.setText("");
            btn.setText(getString(R.string.add_new_student));


        }

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                roll = rollEdit.getText().toString();
                if (uniqueValidation()) {

                    showAlert();

                }
            }
        });
    }

    public Intent makeAndSendStudent() {
        intent = new Intent();

        record = new ContentValues();

        if (uniqueValidation()) {

            intent.putExtra(NAME_EXTRA, name);
            intent.putExtra(ROLL_EXTRA, roll);

            if (mode.equals(Constants.ACTIVITY_NEW_MODE)) {
                mViewModel.getStudentList().add(new Student(name, roll));
                intent.putExtra("mode", Constants.ACTIVITY_NEW_MODE);
                intent.setClass(getActivity(), dataStoreService.class);
                return intent;

            } else {
                Student student = mViewModel.getStudentList().get(position);
                student.setName(name);
                student.setRoll(roll);
                intent.putExtra("mode", Constants.ACTIVITY_EDIT_MODE);
                intent.putExtra("initialRoll", initialRoll);
                return intent;
            }


        } else
            return null;


    }

    private void showAlert() {
        String choice[] = {getString(R.string.service_dialog), getString(R.string.intservice_dialog), getString(R.string.async_dialog)};
        final Intent intent = makeAndSendStudent();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(choice, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case SERVICE:
                        intent.setClass(getActivity(), dataStoreService.class);
                        getActivity().startService(intent);
                        mViewModel.mode = Constants.ACTIVITY_NEW_MODE;
                        mViewModel.getViewPager().setCurrentItem(Constants.SHOW_FRAGMENT);
                        break;
                    case INTENT_SERVICE:
                        intent.setClass(getActivity(), CustomIntentService.class);
                        getActivity().startService(intent);
                        mViewModel.mode = Constants.ACTIVITY_NEW_MODE;
                        mViewModel.getViewPager().setCurrentItem(Constants.SHOW_FRAGMENT);
                        break;
                    case ASYNC_TASK:
                        new StudentDataBaseUpdateTask().execute(intent);
                        mViewModel.mode = Constants.ACTIVITY_NEW_MODE;
                        mViewModel.getViewPager().setCurrentItem(Constants.SHOW_FRAGMENT);
                        break;

                }

            }
        });
        builder.show();
    }

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
            if (position != null) {

                if (counter == position) {

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
