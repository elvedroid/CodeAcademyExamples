package com.example.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.activities.MainActivity.NAME_EXTRA;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.tvName);
//        Го читаме стрингот кој го испративме од MainActivity класата. NAME_EXTRA мора да е истиот
//        клуч кој сме го поставиле во интентот во MainActivity.
        String name = getIntent().getStringExtra(NAME_EXTRA);
//        Го прикажуваме текстот.
        textView.setText(name);

    }
}
