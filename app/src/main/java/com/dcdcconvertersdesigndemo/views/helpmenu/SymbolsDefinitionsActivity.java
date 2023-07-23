package com.example.dcdcconvertersdesigndemo.views.helpmenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesigndemo.R;
import com.example.dcdcconvertersdesigndemo.helpers.Helpers;

public class SymbolsDefinitionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_symbols);
        Helpers.setMinActionBar(this);
    }
}
