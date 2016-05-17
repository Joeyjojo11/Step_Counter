package com.example.jdolan.step_counter;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

/**
 * Created by jdolan on 26/04/16.
 */
public class HistoryActivity extends AppCompatActivity {
    public EditText userText = null;

    private SimpleCursorAdapter dataAdapter;

    private MenuItem historyMenu;
    private MenuItem homeMenu;



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

        final ListView listView = (ListView) findViewById(R.id.listView1);
             
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long arg3) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(HistoryActivity.this);
                builder1.setMessage("Are you sure you want to delete this entry");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int itemid) {
                                Cursor cursor2 = myDB.databaseToString();

                                cursor2.moveToPosition(position);
                                int id= cursor2.getInt(cursor2.getColumnIndex("_id"));

                                myDB.deleteData(Integer.toString(id));
                                Toast.makeText(HistoryActivity.this, "Item Deleted: ", Toast.LENGTH_SHORT).show();
                                displayListView();

                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();


                return true ;
            }

        });


    }



    public boolean onCreateOptionsMenu(Menu menu){

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main, menu);
//        String title = "Additonal Menu";
//        int groupId = Menu.NONE;
//        int itemId = Menu.FIRST;
//        int order = 103;
//        menu.add(groupId,itemId,order,title);
        historyMenu = menu.getItem(1);
        menu.getItem(1).setVisible(false);

        return true;


    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.menu_home:
                View v = new View(this);
                MainPage(v);
            case R.id.menu_history:
                //
        }

        //Toast.makeText(this, "Menus item selected: " + item.getItemId(), Toast.LENGTH_SHORT).show();
        return true;
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

        ListView listView = (ListView) findViewById(R.id.listView1);

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.walk_info,
                cursor,
                columns,
                to,
                0);



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