package com.example.jdolan.step_counter;

import android.app.AlertDialog;
import android.app.FragmentHostCallback;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.example.jdolan.step_counter.MySQLiteHelper;
import android.database.sqlite.SQLiteDatabase;
import android.os.SystemClock;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.example.jdolan.step_counter.CountDataSource;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    //SQLiteDatabase Step_History = null;
    MySQLiteHelper myDB;
    private CountDataSource datasource;

    private SensorManager sensorManager;
    private Sensor stepDetectorSensor;
    private Sensor accelerometer;
    private Sensor magnetometer;
    public int stepCount = 0;
    public float Avg = 0;
    public int TotalCount = 0;
    public int NumOfActivities = 0;
    public float TempTotalCount = 0;
    public float TempNumOfActivities = 0;

    private int counterSteps = 0;
    private int stepDetector = 0;
    private int start_stop = 0;
    public int Countint = 0;

    Button Start, Stop, History, Viewall;
    EditText userText;
    TextView Count,Total_Count, Average;
    boolean activityRunning;
    public boolean active = false; //Used to checked if the counter is running
    public boolean isInserted =false;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        //Start = (Button) findViewById(R.id.b_Start);
        History = (Button) findViewById(R.id.b_History);
        Total_Count = (TextView) findViewById(R.id.Total_Count);
        Count = (TextView) findViewById(R.id.Current_Count);
        Average = (TextView) findViewById(R.id.Average);
        myDB = new MySQLiteHelper(this);

        //Ensure when when the App Starts it displays 0
        TextView tv = (TextView) findViewById(R.id.Current_Count);
        tv.setText(Integer.toString(stepCount));

        //Show the Data on Start up
        UpdateCounters();


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        // ATTENTION: This was auto-generated to implement the App Indexing API.


        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History(v);
            }
        });


        //Step counting and other calculations start when user presses "start" button
        final Button b_Start = (Button) findViewById(R.id.b_Start);
        if (b_Start != null) {
            final String[] StartTime2 = {"test"};
            final int[] NumSteps2 = {0};
            b_Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!active) {
                        b_Start.setText("Finish");
                        sensorManager.registerListener(MainActivity.this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        //StartTime2 = getDateTime();
                        //StartTime2.getText().toString();
                        //String contactEmail = emailEditText.getText().toString();

                        active = true;
                    } else {
                        b_Start.setText(R.string.start);
                        NumSteps2[0] = stepCount;

                        sensorManager.unregisterListener(MainActivity.this, stepDetectorSensor);
                        isInserted = myDB.insertData(StartTime2[0], NumSteps2[0]);
                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                        active = false;
                        UpdateCounters();
                    }
                    stepCount = 0;
                    TextView tv = (TextView) findViewById(R.id.Current_Count);
                    tv.setText(Integer.toString(stepCount));


                }
            });




        }



    }

    public void checkdb (){
        Toast.makeText(MainActivity.this, "No DB", Toast.LENGTH_SHORT).show();
    }

    public void UpdateCounters(){
        //Get the Total Number of Steps taken by suming the Steps Column
        TotalCount = myDB.getTotalSteps();
        TextView tv = (TextView) findViewById(R.id.Total_Count);
        tv.setText(Integer.toString(TotalCount));

        //Get the Total Number of Activities by counting the Rows
        NumOfActivities = myDB.getRunCount();
        TextView tv1 = (TextView) findViewById(R.id.No_Activities);
        tv1.setText(Integer.toString(NumOfActivities));

        //Check if the number of activities is 0, If so the Average is 0
        //Need this as dividing by 0 will cause the app to crash
        if (NumOfActivities == 0){
            Avg = 0;

        }
        else {
            //Do the Calculato to get the Average
            TempNumOfActivities = NumOfActivities;
            TempTotalCount = TotalCount;
            Avg = TempTotalCount / TempNumOfActivities;
            TextView tv2 = (TextView) findViewById(R.id.Average);
            tv2.setText(Float.toString(Avg));
        }
    }


    //   Viewall.setOnClickListener(
    //        new Button.OnClickListener(){
    //  public void onClick(View v){
    //    myDB.checkDataBase();
    // }

    //      }
    // );







    public void History(View v1) {
        Intent A2Intent = new Intent (v1.getContext(), Activity.class);
        // start the next activity/page
        startActivity(A2Intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Count.open();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

 //   private String getDateTime() {
   //     SimpleDateFormat dateFormat = new SimpleDateFormat(
    //            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    //    Date date = new Date();
    //    return dateFormat.format(date);
  //  }


    @Override
    protected void onPause() {
        super.onPause();
        //Count.close();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
        //sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning & (active == true)) {
            stepCount++;
            //Countint = stepCount ;
            TextView tv = (TextView) findViewById(R.id.Current_Count);
            tv.setText(Integer.toString(stepCount));
            // Total_Count.setText(String.valueOf(event.values[0]));
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jdolan.step_counter/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction2);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction2 = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jdolan.step_counter/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction2);
        client.disconnect();
    }

}

