package com.example.mytodoapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  {
    EditText etTitle;
    Button btnAdd;
    ListView lsvItems;
    MyAdapter adapter ;
    MySoundPool myPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle =findViewById(R.id.etToDoTiltle);
        btnAdd = findViewById(R.id.btnAdd);
        lsvItems=findViewById(R.id.lsvItems);

        adapter = new MyAdapter(this);
        Item item1 = new Item("item1","This is item1 description. used to show long text");
        Item item2 = new Item("title", "Details", true);

        adapter.add(item1);
        adapter.add(item2);
        lsvItems.setAdapter(adapter);

        myPool = new MySoundPool(this);
    }


    public void addNewItem(View view) {
        myPool.playClickSound();
        String newItemTitle = etTitle.getText().toString();
        adapter.add(new Item(newItemTitle,""));
        etTitle.setText("");
    }
}