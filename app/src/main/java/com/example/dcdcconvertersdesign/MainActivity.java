package com.example.dcdcconvertersdesign;

import static com.example.dcdcconvertersdesign.R.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.dcdcconvertersdesign.helpers.Helpers;

public class MainActivity extends AppCompatActivity {

    Button UsualDesign, ReverseEngineering;

    public void createObjects()
    {
        // Buttons
        UsualDesign = findViewById(id.UsualDesign);
        ReverseEngineering = findViewById(id.ReverseEngineering);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        Helpers.setMainActionBar(this);

        // Set the text color
        setTheme(style.AppTheme);
        createObjects();

        // Usual Design
        UsualDesign.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UsualDesign.class);
            startActivity(intent);
        });
        // Reverse Engineering
        ReverseEngineering.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReverseDesign.class);
            startActivity(intent);
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case id.Symbols:
                Intent intent_symbols = new Intent(MainActivity.this, Symbols.class);
                startActivity(intent_symbols);
                return true;

            case id.ConvertersDefinitions:
                Intent intent_converters_definitions = new Intent(MainActivity.this, ConvertersDefinitions.class);
                startActivity(intent_converters_definitions);
                return true;

            case id.InductorDefinitions:
                Intent intent_inductor_definitions = new Intent(MainActivity.this, InductorDefinitions.class);
                startActivity(intent_inductor_definitions);
                return true;

            case id.SnubberDefinitions:
                Intent intent_snubber_definitions = new Intent(MainActivity.this, SnubberDefinitions.class);
                startActivity(intent_snubber_definitions);
                return true;

            case id.About:
                Intent intent_about = new Intent(MainActivity.this, About.class);
                startActivity(intent_about);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    }
