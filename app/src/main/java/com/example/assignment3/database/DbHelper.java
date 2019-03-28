package com.example.assignment3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.assignment3.MainActivity;
import com.example.assignment3.model.Student;
import com.example.assignment3.database.tables.StudentTable;
import com.example.assignment3.database.tables.Table;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    private final static String DB_NAME = "StudentDatabase";

    private final static int DB_VERSION = 1;
    private static SQLiteDatabase db;




    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db=db;
createTable(StudentTable.CREATE_TABLE_QUERY);//change krna
    }

    public void insertQuery(String tableName, ContentValues record) {
       db=this.getWritableDatabase();
        db.insert(tableName, null, record);


    }
    public ArrayList<Student> getAllStudents(String tableName){
        MainActivity.debug("database retrieve");
       SQLiteDatabase db=this.getWritableDatabase();


        Cursor cursor=db.query(tableName,null,null,null,null,null,null);

        ArrayList<Student> students=new ArrayList<>();
cursor.moveToFirst();
if(cursor.getCount()==0){
    return students;
}
    do{

        String name=cursor.getString(0);
        String roll=String.valueOf(cursor.getInt(1));
        students.add(new Student(name,roll));
        }while(cursor.moveToNext());

        cursor.close();
        return students;

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable(String query) {
        if (DB_VERSION == 1) {

            db.execSQL(query);


        }


    }

    public void updateQuery(Table table, ContentValues record, String where, String [] args) {

        db=this.getWritableDatabase();

        db.update(table.getTableName(),record,where,args);
    }
    public void deleteQuery(Table table, String where, String [] args){
        db=this.getWritableDatabase();

        db.delete(table.getTableName(),where,args);
    }
}
