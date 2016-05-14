package com.example.jdolan.step_counter;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Date;

/**
 * Created by jdolan on 17/04/16.
 */
public class CountDataSource {

    // Database fields
   // private SQLiteDatabase database;
   // private MySQLiteHelper dbHelper;
   // private String[] allColumns = {
   //         MySQLiteHelper.COLUMN_ID,
   //         MySQLiteHelper.COLUMN_COMMENT,

 //   };


   /* public CountDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }


 /*   public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

  /*  public Step_History createSession(String Steps) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, Steps);
        long insertId = database.insert(MySQLiteHelper.TABLE_NAME, null,
                values);
        //Query the DB, all columns on specific ID
        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);

        cursor.moveToFirst();
        Step_History newSteps = cursorToSteps(cursor);
        cursor.close();
        return newSteps;
    }

    public void deleteSession(Step_History Steps) {
        long id = Steps.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_NAME, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

 /*   public List<Step_History> getAllComments() {
        List<Step_History> comments = new ArrayList<Step_History>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Step_History comment = cursorToSteps(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

 /*   private Step_History cursorToSteps(Cursor cursor) {
        Step_History steps = new Step_History();
        steps.setId(cursor.getLong(0));
        steps.setSteps(cursor.getInt(1));
        Date date = new Date(cursor.getLong(2));
        steps.setStart(date);
        return steps;
    } */

 /*   private String getDateTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return  dateFormat.format(date);
    }  */

}




