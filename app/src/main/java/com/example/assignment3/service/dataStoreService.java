package com.example.assignment3.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.example.assignment3.database.tables.StudentTable;
import com.example.assignment3.util.Constants;

public class dataStoreService extends Service implements Runnable {
    private String mode;
    private ContentValues record = new ContentValues();
    Intent intent;

    public dataStoreService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.intent = intent;
        new Thread(this).start();

        return START_NOT_STICKY;

    }


    @Override
    public void onDestroy() {
        super.onDestroy();

//        Log.d("uuuuuuu", "destroy");


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void run() {
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
        stopSelf();
    }
}
