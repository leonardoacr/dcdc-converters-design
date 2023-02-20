package com.example.dcdcconvertersdesign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Symbols extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_symbols);
    }
}
