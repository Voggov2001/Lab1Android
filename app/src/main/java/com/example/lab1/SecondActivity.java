package com.example.lab1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private static Button buttonAdd;
    private static Button buttonDel;
    private static List<String> myStringArray;
    private static List<String> selectStringArray;
    private static ArrayAdapter<String> textAdapter;
    private static ListView listView;
    private static TextView textView;
    public static SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getPreferences(MODE_PRIVATE);
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("List", myStringArray.stream().collect(Collectors.toSet()));
        editor.apply();
        Log.e("jopa", "pizda");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Set<String> setString = new HashSet<>();
        setString = sharedPreferences.getStringSet("List", setString);
        if (!setString.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               /* myStringArray = setString.stream().collect(Collectors.toList());
                textAdapter.notifyDataSetChanged();*/
                for (String i : setString) {
                    add(i);
                }

            }
        }
        Log.e("govno", "penis");



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