package com.example.lab1;

import static com.example.lab1.R.id.button2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;



public class HelloActivity extends Activity {
    private static Integer counter = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloact);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        TextView textView = findViewById(R.id.textCounter);
        textView.setText(counter.toString());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button1.setText("Нажато");
                button2.setText("Нажми");
                counter++;
                textView.setText(counter.toString());

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2.setText("Нажато");
                button1.setText("Нажми");
                counter++;
                textView.setText(counter.toString());
            }
        });
    }
}
