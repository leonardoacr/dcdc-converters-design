package com.example.dcdcconvertersdesigndemo.views;

import static com.example.dcdcconvertersdesigndemo.R.*;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.dcdcconvertersdesigndemo.interfaces.views.MainViewInterface;
import com.example.dcdcconvertersdesigndemo.views.helpmenu.AboutActivity;
import com.example.dcdcconvertersdesigndemo.views.helpmenu.ConvertersDefinitionsActivity;
import com.example.dcdcconvertersdesigndemo.views.helpmenu.InductorDefinitionsActivity;
import com.example.dcdcconvertersdesigndemo.R;
import com.example.dcdcconvertersdesigndemo.views.helpmenu.SnubberDefinitionsActivity;
import com.example.dcdcconvertersdesigndemo.views.helpmenu.SymbolsDefinitionsActivity;
import com.example.dcdcconvertersdesigndemo.controllers.MainController;
import com.example.dcdcconvertersdesigndemo.helpers.Helpers;

public class MainView extends AppCompatActivity implements MainViewInterface {

    private MainController controller;
    private Button usualDesignBtn;
    private Button reverseEngineeringBtn;

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
        Intent intent = new Intent(this, UsualDesignView.class);
        startActivity(intent);
    }

    public void navigateToReverseEngineering() {
        Intent intent = new Intent(this, ReverseDesignView.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.symbols_item) {
            navigateTo(SymbolsDefinitionsActivity.class);
            return true;
        } else if (itemId == R.id.converter_definitions_item) {
            navigateTo(ConvertersDefinitionsActivity.class);
            return true;
        } else if (itemId == R.id.inductor_definitions_item) {
            navigateTo(InductorDefinitionsActivity.class);
            return true;
        } else if (itemId == R.id.snubber_definitions_item) {
            navigateTo(SnubberDefinitionsActivity.class);
            return true;
        } else if (itemId == R.id.about_item) {
            navigateTo(AboutActivity.class);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void navigateTo(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
