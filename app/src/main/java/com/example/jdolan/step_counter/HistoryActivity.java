package com.example.jdolan.step_counter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by jdolan on 26/04/16.
 */
public class HistoryActivity extends AppCompatActivity {
    private CountDataSource datasource;
    public EditText userText = null;

    private SimpleCursorAdapter dataAdapter;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    MySQLiteHelper myDB;
    MainActivity MA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Button Back = (Button) findViewById(R.id.button4);
        Button Delete = (Button) findViewById(R.id.b_delete);
        myDB = new MySQLiteHelper(this);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPage(v);
            }
        });
        displayListView();


    }


    private void displayListView(){

        Cursor cursor = myDB.databaseToString();

        // The desired columns to be bound
        String[] columns = new String[] {
                myDB.COLUMN_ID,
                myDB.COLUMN_STARTTIME,
                myDB.COLUMN_NUM_STEPS
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.TextView_id,
                R.id.TextView_startTime,
                R.id.TextView_numSteps,
        };


        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.walk_info,
                cursor,
                columns,
                to,
                0);


        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    public void MainPage(View v1) {
        Intent A2Intent = new Intent(v1.getContext(), MainActivity.class);
        startActivity(A2Intent);
    }


}