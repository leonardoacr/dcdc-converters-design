package com.example.dcdcconvertersdesign;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesign.helpers.Helpers;

public class InductorDesignTable extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Helpers.setMinActionBar(this);
    }
}
