package com.example.jdolan.step_counter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdolan on 26/04/16.
 */
public class Activity extends ListActivity {
    private CountDataSource datasource;
    public EditText userText = null;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    MySQLiteHelper myDB;
    MainActivity MA;
    ListView list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Button Back = (Button) findViewById(R.id.button4);
        Button Delete = (Button) findViewById(R.id.b_delete);
        myDB = new MySQLiteHelper(this);

        final ArrayList<String> list = new ArrayList<String>();

        list.add ("DATE       :   STEPS");

        list2 = (ListView) findViewById(android.R.id.list);
        //Cursor res = =MA.getAllData();
        Cursor res = getAllData();

       // String[] buffer = new String[10];
       // while (res.moveToNext()){
       //     buffer.append(res.getString(0) + " " );
       //     buffer.append(res.getString(1) + " ");
      //      buffer.append(res.getString(2) + "\n");
            //buffer.append("Marks: " + res.getString(3) + "\n\n");
      //  }

       //listview.setOnItemClickListener(new AdapterVi
        //ListAdapter ListAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
        //list2.setAdapter(ListAdapter);



        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainPage(v);
            }
        });

        // Defined Array values to show in ListView


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        list2.setAdapter(adapter);

        //  Viewall.setOnClickListener(
              //  new Button.OnClickListener() {
                //    public void onClick(View v) {
                //        myDB.deleteByID();
                //    }
               // });





        //datasource = new CountDataSource(this);
        //datasource.open();

        //List<Step_History> values = datasource.getAllComments();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        //ArrayAdapter<Step_History> adapter = new ArrayAdapter<Step_History>(this,
               /// android.R.layout.simple_list_item_1, values);
       // setListAdapter(adapter);

        //final ListView listview = (ListView) findViewById(R.id.list);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //

       /* final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter); */

    }


    public Cursor getAllData() {
        Cursor res = myDB.databaseToString();
        if(res.getCount() == 0){
            //Show no data message
            showMessage("Error", "No Data Found");
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append(res.getString(0) + " " );
            buffer.append(res.getString(1) + " ");
            buffer.append(res.getString(2) + "\n");
            //buffer.append("Marks: " + res.getString(3) + "\n\n");
        }
        return res;
        //showMessage("Data", buffer.toString());


    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void populateListView() {
        String[] values =  new String[] { myDB.COLUMN_ID,myDB.COLUMN_STARTTIME,myDB.COLUMN_COMMENT
        };
        SimpleCursorAdapter myCursorAdapter;
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(),android.R.layout.simple_list_item_1, res, values,0);


    }


    // Will be called via the onClick attribute
    // of the buttons in main.xml
 /*   public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Step_History> adapter = (ArrayAdapter<Step_History>) getListAdapter();
        Step_History Steps = null;
        switch (view.getId()) {
            case R.id.b_Start:
                String inStr = userText.getText().toString();
                // save the new comment to the database
                Steps = datasource.createSession(inStr);
                adapter.add(Steps);
                userText.setText("");
                //MainActivity.class. = true;
                //CountDataSource.getDateTime();
                break;
         //   case R.id.b_Reset:
              //  if (getListAdapter().getCount() > 0) {
              //      Steps = (Step_History) getListAdapter().getItem(0);
              //      datasource.deleteSession(Steps);
               //  /   adapter.remove(Steps);
               // }
              //  break;
        }
        adapter.notifyDataSetChanged();
    }
*/


    public void MainPage(View v1) {
        // ImageView imageView = (ImageView)findViewById(R.id.imageView);
        //Intent A2Intent = new Intent(v1.getContext(), Activity.class);
        Intent A2Intent = new Intent(v1.getContext(), MainActivity.class);
        // start the next activity/page
        startActivity(A2Intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                " Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jdolan.step_counter/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                " Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.jdolan.step_counter/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }////
}