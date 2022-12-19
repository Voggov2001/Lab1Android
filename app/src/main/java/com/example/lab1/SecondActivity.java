package com.example.lab1;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static Button buttonAdd;
    private static Button buttonDel;
    private static ArrayList<String> myStringArray;
    private static ArrayList<String> selectStringArray;
    private static ArrayAdapter<String> textAdapter;
    private static ListView listView;
    private static TextView textView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        listView = findViewById(R.id.textList);
        myStringArray = new ArrayList<>();
        selectStringArray = new ArrayList<>();
        textAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray);
        listView.setAdapter(textAdapter);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDel = findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(this);
        buttonAdd.setOnClickListener(this);
        textView = findViewById(R.id.inputText);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = textAdapter.getItem(position);

            if (textAdapter.isEnabled(position)) {
                selectStringArray.add(item);
            } else {
                selectStringArray.remove(item);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                add(textView.getText().toString());
                break;
            case R.id.buttonDel:
                remove();
            default:
                break;
        }

    }

    public void add(String name) {
        if (!name.isEmpty()) {
            myStringArray.add(name);
            textAdapter.notifyDataSetChanged();
        }

    }
    public void remove() {
        for (String i : selectStringArray) {
            textAdapter.remove(i);
        }
        listView.clearChoices();
        selectStringArray.clear();
        textAdapter.notifyDataSetChanged();
    }
}