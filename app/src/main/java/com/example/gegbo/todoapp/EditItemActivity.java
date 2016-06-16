package com.example.gegbo.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditItem;
    Integer position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditItem = (EditText) findViewById(R.id.etEditItem);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("old_value");
            Integer number = extras.getInt("position");
            etEditItem.setText(value);
            position = number;
        }
    }

    public void onSave(View view) {
//        Intent i = new Intent();
//        i.putExtra("new_value",etEditItem.getText());
//        i.putExtra("position",position);
//
//        setResult(RESULT_OK,i);
//        finish();

        EditText etEdit = (EditText) findViewById(R.id.etEditItem);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("new_value", etEditItem.getText().toString());
        data.putExtra("position", position); // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
}
