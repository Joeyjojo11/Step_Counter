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
    public static final int DATABASE_VERSION = 9;
    // Table Columns names
    public static final String COLUMN_ID = "Run_id";
    public static final String COLUMN_STARTTIME = "startTime";
    //private static final String KEY_ENDTIME = "endTime";
    //public static final String COLUMN_NUMSTEPS = "numSteps";
    public static final String COLUMN_COMMENT = "numSteps";
    //private static final String KEY_NUMSECONDS = "numSeconds";
    //private static final String KEY_DISTANCE = "distanceTravelled";

    public boolean TableCreated =false;
    private SQLiteDatabase database;
    //private static final String[] COLUMNS = {COLUMN_ID, KEY_STARTTIME, KEY_ENDTIME, KEY_NUMSTEPS, KEY_NUMSECONDS, KEY_DISTANCE};

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

      // Database creation sql statement
     private static final String DATABASE_CREATE = "create table Steps ("
                  + " id integer primary key autoincrement,"
                  + " numSteps integer,"
                  + " startTime text" + ");";

      @Override
      public void onCreate(SQLiteDatabase database) {
          database.execSQL(DATABASE_CREATE);
      }



   // public Cursor getAllData(){
      //  SQLiteDatabase db = this.getWritableDatabase();

       // Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
       // return res;

    //}


    //Print out the database as a string
    public Cursor databaseToString(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return res;
        /*
        String dbString = "";
        SQLiteDatabase db =  getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME ;

        //Cursor point to a location in your result
        Cursor c = db.rawQuery(query, null);
        //Move to first row in your result
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("COLUMN_ID")) != null) {
                dbString += c.getString(c.getColumnIndex("COLUMN_ID"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString; */
    }


    // Getting No of runs done to calculate the Avwerage
    public int getRunCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    public int getTotalSteps() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT SUM(numSteps) FROM Steps", null);
        if (c !=null) {
            c.moveToFirst();
        }
        return c.getInt(0);
       // String countQuery = "SELECT Sum FROM " + DATABASE_TABLE, null);
        //String countQuery = "SELECT  * FROM " + TABLE_NAME;
       // SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor = db.rawQuery(countQuery, null);

        // return count
       // return cursor.getCount();
    }

   // public void deleteContact(View view) {
     //   int idToDelete = 0;

        // Get the id to delete
    //    String id = idToDelete.toString();
//
        // Delete matching id in database
     //   database.execSQL("DELETE FROM contacts WHERE id = " + id + ";");

    //}
   // public void deleteByID(Step_History Steps) {
  //      long id = Steps.getId();
  //      System.out.println("Comment deleted with id: " + id);
   //     database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID
   //             + " = " + id, null);
  //  }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String startTime, int numSteps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(KEY_STARTTIME, startTime);
        //values.put(KEY_NUMSTEPS, contact.getName());
        //values.put(KEY_STARTTIME, contact.getPhone());
        contentValues.put(COLUMN_COMMENT, numSteps);
        contentValues.put(COLUMN_STARTTIME, startTime);

        long result = db.insert(TABLE_NAME, null, contentValues);
       // if(result = -1)
       //     return  false;
       // else
         //   return  true;
        return  true;
    }

 /*   public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("Select * From "+TABLE_NAME, null);
        return  res;
    } */


}