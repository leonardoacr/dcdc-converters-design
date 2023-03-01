package com.example.dcdcconvertersdesign;

import android.media.Image;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;
public class ConvertersDefinitions extends AppCompatActivity {

    TextView Buck_definition, Boost_definition, BuckBoost_definition;

    ImageView imageViewBuckDefinitions;

    public void createObjects(){
        // Image ID
        imageViewBuckDefinitions = (ImageView) findViewById(R.id.imageViewBuckDefinitions);
        imageViewBuckDefinitions.setImageResource(R.drawable.buck);
//        Boost_definition = findViewById(R.id.Boost_definition);
//        BuckBoost_definition = findViewById(R.id.BuckBoost_definition);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_converters_definitions);
        createObjects();
    }
    }
