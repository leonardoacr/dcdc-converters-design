package com.dcdcconvertersdesigndemo.views;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.dcdcconvertersdesigndemo.R;
import com.dcdcconvertersdesigndemo.helpers.Helpers;

public class InductorDesignTableView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Helpers.setMinActionBar(this);
    }
}
