package com.example.dcdcconvertersdesign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ConvertersDefinitions extends AppCompatActivity {

    TextView Buck_definition, Boost_definition, BuckBoost_definition;

    public void createObjects(){
        // Text ID
        //Buck_definition = (TextView) findViewById(R.id.Buck_definition);
        //Boost_definition = findViewById(R.id.Boost_definition);
        //BuckBoost_definition = findViewById(R.id.BuckBoost_definition);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_converters_definitions);
        createObjects();
    }
    }
