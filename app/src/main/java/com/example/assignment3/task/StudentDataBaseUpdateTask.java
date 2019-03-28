package com.example.assignment3.task;

import android.content.ContentValues;
//import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
//import android.widget.Toast;

import com.example.assignment3.database.tables.StudentTable;
import com.example.assignment3.util.Constants;

public class StudentDataBaseUpdateTask extends AsyncTask<Intent, Void, Void> {
    private ContentValues record = new ContentValues();
    private String mode;

    @Override
    protected Void doInBackground(Intent... intent) {

        mode = intent[0].getStringExtra("mode");
        String name = intent[0].getStringExtra(Constants.NAME_EXTRA);
        String roll = intent[0].getStringExtra(Constants.ROLL_EXTRA);
        record.put(StudentTable.COL_NAME, name);
        record.put(StudentTable.COL_ROLL, Integer.parseInt(roll));
        String initialRoll = intent[0].getStringExtra("initialRoll");

        if (mode.equals(Constants.ACTIVITY_NEW_MODE)) {
            Constants.dbHelper.insertQuery(StudentTable.TABLE_NAME, record);
        } else if (mode.equals(Constants.ACTIVITY_EDIT_MODE)) {
            Constants.dbHelper.updateQuery(new StudentTable(), record, " _Roll = ?", new String[]{initialRoll});

        }

        return null;

    }

}

