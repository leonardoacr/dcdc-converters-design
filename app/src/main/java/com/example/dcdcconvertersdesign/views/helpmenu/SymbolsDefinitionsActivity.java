package com.example.dcdcconvertersdesign.views.helpmenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.helpers.Helpers;

public class SymbolsDefinitionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_symbols);
        Helpers.setMinActionBar(this);
    }
}
