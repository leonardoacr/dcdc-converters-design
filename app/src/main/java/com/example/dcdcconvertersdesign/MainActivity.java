package com.example.dcdcconvertersdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button UsualDesign, ReverseEngineering;

    public void createObjects()
    {
        // Buttons
        UsualDesign = findViewById(R.id.UsualDesign);
        ReverseEngineering = findViewById(R.id.ReverseEngineering);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon);
        createObjects();

        // Usual Design
        UsualDesign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, UsualDesign.class);

                startActivity(intent);
            }
        });
        // Reverse Engineering
        ReverseEngineering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ReverseEngineering.class);

                startActivity(intent);
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Symbols:
                Intent intent_symbols = new Intent(MainActivity.this, Symbols.class);
                startActivity(intent_symbols);
                return true;

            case R.id.ConvertersDefinitions:
                Intent intent_converters_definitions = new Intent(MainActivity.this, ConvertersDefinitions.class);
                startActivity(intent_converters_definitions);
                return true;

            case R.id.InductorDefinitions:
                Intent intent_inductor_definitions = new Intent(MainActivity.this, InductorDefinitions.class);
                startActivity(intent_inductor_definitions);
                return true;

            case R.id.SnubberDefinitions:
                Intent intent_snubber_definitions = new Intent(MainActivity.this, SnubberDefinitions.class);
                startActivity(intent_snubber_definitions);
                return true;

            case R.id.About:
                Intent intent_about = new Intent(MainActivity.this, About.class);
                startActivity(intent_about);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }
