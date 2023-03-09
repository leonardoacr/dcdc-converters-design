package com.example.dcdcconvertersdesign.views;

import static com.example.dcdcconvertersdesign.R.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.dcdcconvertersdesign.About;
import com.example.dcdcconvertersdesign.ConvertersDefinitions;
import com.example.dcdcconvertersdesign.InductorDefinitions;
import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.SnubberDefinitions;
import com.example.dcdcconvertersdesign.SymbolsDefinitions;
import com.example.dcdcconvertersdesign.controllers.MainController;
import com.example.dcdcconvertersdesign.helpers.Helpers;

public class MainActivity extends AppCompatActivity {

    private MainController controller;
    private Button usualDesignBtn, reverseEngineeringBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        Helpers.setMainActionBar(this);

        // Set up the controller
        controller = new MainController(this);

        // Set the text color and UI components
        setTheme(style.AppTheme);
        setUIComponents();

        // Set up the buttons listeners
        setButtonsListeners();
    }

    private void setUIComponents()
    {
        // Buttons
        usualDesignBtn = findViewById(id.usual_design_button);
        reverseEngineeringBtn = findViewById(id.reverse_design_button);
    }

    private void setButtonsListeners() {
        usualDesignBtn.setOnClickListener(v -> controller.onUsualDesignClicked());
        reverseEngineeringBtn.setOnClickListener(v -> controller.onReverseDesignClicked());
    }

    public void navigateToUsualDesign() {
        Intent intent = new Intent(this, UsualDesignActivity.class);
        startActivity(intent);
    }

    public void navigateToReverseEngineering() {
        Intent intent = new Intent(this, ReverseDesignActivity.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Symbols) {
            Intent intent_symbols = new Intent(MainActivity.this,
                    SymbolsDefinitions.class);
            startActivity(intent_symbols);
            return true;
        } else if (id == R.id.ConvertersDefinitions) {
            Intent intent_converters_definitions = new Intent(MainActivity.this,
                    ConvertersDefinitions.class);
            startActivity(intent_converters_definitions);
            return true;
        } else if (id == R.id.InductorDefinitions) {
            Intent intent_inductor_definitions = new Intent(MainActivity.this,
                    InductorDefinitions.class);
            startActivity(intent_inductor_definitions);
            return true;
        } else if (id == R.id.SnubberDefinitions) {
            Intent intent_snubber_definitions = new Intent(MainActivity.this,
                    SnubberDefinitions.class);
            startActivity(intent_snubber_definitions);
            return true;
        } else if (id == R.id.About) {
            Intent intent_about = new Intent(MainActivity.this, About.class);
            startActivity(intent_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
