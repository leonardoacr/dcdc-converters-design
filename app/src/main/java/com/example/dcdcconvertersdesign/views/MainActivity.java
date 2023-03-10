package com.example.dcdcconvertersdesign.views;

import static com.example.dcdcconvertersdesign.R.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.dcdcconvertersdesign.views.helpmenu.AboutActivity;
import com.example.dcdcconvertersdesign.views.helpmenu.ConvertersDefinitionsActivity;
import com.example.dcdcconvertersdesign.views.helpmenu.InductorDefinitionsActivity;
import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.views.helpmenu.SnubberDefinitionsActivity;
import com.example.dcdcconvertersdesign.views.helpmenu.SymbolsDefinitionsActivity;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.symbols_item) {
            Intent intent_symbols = new Intent(MainActivity.this,
                    SymbolsDefinitionsActivity.class);
            startActivity(intent_symbols);
            return true;
        } else if (id == R.id.converter_definitions_item) {
            Intent intent_converters_definitions = new Intent(MainActivity.this,
                    ConvertersDefinitionsActivity.class);
            startActivity(intent_converters_definitions);
            return true;
        } else if (id == R.id.inductor_definitions_item) {
            Intent intent_inductor_definitions = new Intent(MainActivity.this,
                    InductorDefinitionsActivity.class);
            startActivity(intent_inductor_definitions);
            return true;
        } else if (id == R.id.snubber_definitions_item) {
            Intent intent_snubber_definitions = new Intent(MainActivity.this,
                    SnubberDefinitionsActivity.class);
            startActivity(intent_snubber_definitions);
            return true;
        } else if (id == R.id.about_item) {
            Intent intent_about = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
