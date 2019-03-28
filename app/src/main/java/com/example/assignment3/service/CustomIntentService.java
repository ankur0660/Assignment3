package com.example.assignment3.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.example.assignment3.database.tables.StudentTable;
import com.example.assignment3.util.Constants;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CustomIntentService extends IntentService {


    private String mode;
ContentValues record=new ContentValues();

    public CustomIntentService() {
        super("CustomIntentService");
        Log.d("iiii","ssss");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("iiii","ssss");
        mode = intent.getStringExtra("mode");
        String name = intent.getStringExtra(Constants.NAME_EXTRA);
        String roll = intent.getStringExtra(Constants.ROLL_EXTRA);
        record.put(StudentTable.COL_NAME, name);
        record.put(StudentTable.COL_ROLL, Integer.parseInt(roll));
        String initialRoll = intent.getStringExtra("initialRoll");

        if (mode.equals(Constants.ACTIVITY_NEW_MODE)) {
            Constants.dbHelper.insertQuery(StudentTable.TABLE_NAME, record);
        } else if (mode.equals(Constants.ACTIVITY_EDIT_MODE)) {
            Constants.dbHelper.updateQuery(new StudentTable(), record, " _Roll = ?", new String[]{initialRoll});

        }
    }

}
