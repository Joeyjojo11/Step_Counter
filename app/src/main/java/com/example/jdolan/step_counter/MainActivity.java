package com.example.jdolan.step_counter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.hardware.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {

    //SQLiteDatabase Step_History = null;
    MySQLiteHelper myDB;

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

    private MenuItem historyMenu;

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
    String StartTime2 = "";
    int[] NumSteps2 = {0};


    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        setContentView(R.layout.activity_main);

        //Start = (Button) findViewById(R.id.b_Start);
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


        //Step counting and other calculations start when user presses "start" button
        final Button b_Start = (Button) findViewById(R.id.b_Start);
        if (b_Start != null) {

            b_Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!active) {
                        b_Start.setText("Finish");
                        b_Start.setBackgroundColor(Color.parseColor("#F44336"));
                        sensorManager.registerListener(MainActivity.this, stepDetectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
                        StartTime2 = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                        active = true;

                        historyMenu.setEnabled(false);

                    }

                    else {
                        b_Start.setText("Start");
                        b_Start.setBackgroundColor(Color.parseColor("#A4C639"));
                        NumSteps2[0] = stepCount;

                        sensorManager.unregisterListener(MainActivity.this, stepDetectorSensor);
                        isInserted = myDB.insertData(StartTime2, NumSteps2[0]);
                        if (isInserted = true)
                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();

                        active = false;
                        historyMenu.setEnabled(true);

                        UpdateCounters();
                    }
                    stepCount = 0;
                    TextView tv = (TextView) findViewById(R.id.Current_Count);
                    tv.setText(Integer.toString(stepCount));

                }
            });


        }

    }


    public boolean onCreateOptionsMenu(Menu menu){

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);
//        String title = "Additonal Menu";
//        int groupId = Menu.NONE;
//        int itemId = Menu.FIRST;
//        int order = 103;
//        menu.add(groupId,itemId,order,title);
        historyMenu=menu.getItem(1);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.menu_home:
                // Do something
            case R.id.menu_history:
                View v = new View(this);
                History(v);
        }

        //Toast.makeText(this, "Menus item selected: " + item.getItemId(), Toast.LENGTH_SHORT).show();
        return true;
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
            //Convert the Float to a String to limit the value to 2 decimal places
            String str = String.format("%1.2f", Avg);
            //convert it Back to a Double
            Avg = Float.valueOf(str);
            TextView tv2 = (TextView) findViewById(R.id.Average);
            tv2.setText(Float.toString(Avg));
        }
    }


    public void History(View v1) {
        Intent A2Intent = new Intent (v1.getContext(), HistoryActivity.class);
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

    @Override
    protected void onPause() {
        super.onPause();
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

    }

    @Override
    public void onStop() {
        super.onStop();


    }

}

