package com.example.gegbo.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> toDoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;
    private final int REQUEST_CODE = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(MainActivity.this,EditItemActivity.class);
                i.putExtra("old_value",toDoItems.get(position));
                i.putExtra("position",position);
                startActivityForResult(i,REQUEST_CODE) ;
            }
        });

//        Bundle extras = getIntent().getExtras();
//        if (extras != null) {
//            String value = extras.getString("new_value");
//            Integer number = extras.getInt("position");
//            toDoItems.set(number,value);
//        }
    }

    public void populateArrayItems() {
//        toDoItems = new ArrayList<String>();
//        toDoItems.add("Item 1");
//        toDoItems.add("Item 2");
//        toDoItems.add("Item 3");
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,toDoItems);

    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try {
            toDoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e){
            toDoItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try {
           FileUtils.writeLines(file,toDoItems );
        } catch (IOException e){

        }
    }

    public void onAddItem(View view) {
        aToDoAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            // Extract name value from result extras
//            String name = data.getExtras().getString("name");
//            int code = data.getExtras().getInt("code", 0);
//            // Toast the name to display temporarily on screen
//            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();

            String value = data.getExtras().getString("new_value");
            Integer number = data.getExtras().getInt("position");
            toDoItems.set(number,value);
            aToDoAdapter.notifyDataSetChanged();
            writeItems();

        }
    }
}
