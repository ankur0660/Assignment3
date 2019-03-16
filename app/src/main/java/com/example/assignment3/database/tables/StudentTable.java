package com.example.assignment3.database.tables;

public class StudentTable extends Table {
    public  static final String COL_NAME="Name";
    public  static final String COL_ROLL="_Roll";
    public static final String TABLE_NAME ="Student";
    public final static String CREATE_TABLE_QUERY="Create table Student("
                                                  + COL_NAME+" TEXT,"
                                                  +COL_ROLL+" INTEGER PRIMARY KEY)";


    @Override
    public String getTableName() {
return TABLE_NAME;
    }
}
