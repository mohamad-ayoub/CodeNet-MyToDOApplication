package com.example.mytodoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    EditText etTitle;
    Button btnAdd;
    ListView lsvItems;
    MyAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle =findViewById(R.id.etToDoTiltle);
        btnAdd = findViewById(R.id.btnAdd);
        lsvItems=findViewById(R.id.lsvItems);

        adapter = new MyAdapter(this);

        lsvItems.setAdapter(adapter);
    }


    public void addNewItem(View view) {
        String newItemTitle = etTitle.getText().toString();
        adapter.add(newItemTitle);
        etTitle.setText("");
    }
}