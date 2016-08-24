package com.beekay.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by krishna on 11/7/2014.
 */
public class DataOpener  {

    private static final String DBNAME="password.db";
    private static final String TABLE_NAME="ptable";
    private static final String COLUMN="pass";
    private static final String CREATE="CREATE TABLE IF NOT EXISTS ptable(pass INTEGER NOT NULL);";
    private static final int VERSION=1;
    private SQLiteDatabase db;
    private DataHelper helper;
    Context context;
    public DataOpener(Context context){
        this.context=context;
        helper=new DataHelper(context);

    }

    private class DataHelper extends SQLiteOpenHelper{


        public DataHelper(Context context) {
            super(context,DBNAME,null,VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE);

            }catch (SQLException exception){
             Log.v("exception",exception.toString());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ptable");
            onCreate(sqLiteDatabase);
        }
    }

    public DataOpener open(){
        db=helper.getWritableDatabase();
        return this;
    }

    public void close(){
        helper.close();
    }

    public DataOpener openRead(){
        db=helper.getReadableDatabase();
        return this;
    }

    public long insertData(int pass){
        ContentValues values=new ContentValues();
        values.put(COLUMN,pass);
        return db.insertOrThrow(TABLE_NAME,null,values);
    }


    public Cursor retrieve(){
        return db.query(TABLE_NAME,new String[]{COLUMN},null,null,null,null,null);

    }



    public void delete(){
        db.delete(TABLE_NAME,null,null);
    }
}
