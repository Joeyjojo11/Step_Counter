package com.example.jdolan.step_counter;

/**
 * Created by jdolan on 05/04/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.Date;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Name
    public static final String DATABASE_NAME = "Step_History.db";

    // Table name
    public static final String TABLE_NAME = "Steps";

    // Database Version
    public static final int DATABASE_VERSION = 10;

    // Table Columns names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_STARTTIME = "startTime";
    public static final String COLUMN_NUM_STEPS = "numSteps";

    public boolean TableCreated =false;
    private SQLiteDatabase database;

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

      // Database creation sql statement
     private static final String DATABASE_CREATE = "CREATE TABLE "+ TABLE_NAME+" ("
                  + " "+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                  + " "+COLUMN_NUM_STEPS+" INTEGER,"
                  + " "+COLUMN_STARTTIME+" TEXT" + ");";

      @Override
      public void onCreate(SQLiteDatabase db) {
          db.execSQL(DATABASE_CREATE);
      }


    public Cursor databaseToString(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return res;

    }


    // Getting No of runs done to calculate the Avwerage
    public int getRunCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor !=null) {
            cursor.moveToFirst();
            return cursor.getCount();
        }
        else{
            return 0;
        }
    }

    public int getTotalSteps() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT SUM("+COLUMN_NUM_STEPS+") FROM "+TABLE_NAME, null);
        if (c !=null) {
            c.moveToFirst();
            return c.getInt(0);
        }
        else{
            return 0;
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

    }

    public boolean insertData(String startTime, int numSteps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NUM_STEPS, numSteps);
        contentValues.put(COLUMN_STARTTIME, startTime);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return  true;
    }



}