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
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = findViewById(R.id.etToDoTiltle);
        btnAdd = findViewById(R.id.btnAdd);
        lsvItems = findViewById(R.id.lsvItems);

        adapter = new MyAdapter(this);
        //Item item1 = new Item("item1","This is item1 description. used to show long text");
        //Item item2 = new Item("title", "Details", true);

        //adapter.add(item1);
        //adapter.add(item2);
        adapter.load();

        lsvItems.setAdapter(adapter);
        new MySoundPool(this);
    }


    @Override
    protected void onStop() {
        super.onStop();

        adapter.save();
    }

    public void addNewItem(View view) {
        MySoundPool.playAddSound(this);
        String newItemTitle = etTitle.getText().toString();
        adapter.add(new Item(newItemTitle, ""));
        etTitle.setText("");
    }
}