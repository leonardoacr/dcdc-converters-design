package com.example.dcdcconvertersdesigndemo.views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesigndemo.R;
import com.example.dcdcconvertersdesigndemo.helpers.Helpers;

public class InductorDesignTableView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Helpers.setMinActionBar(this);
    }
}
