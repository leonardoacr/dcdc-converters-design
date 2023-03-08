package com.example.dcdcconvertersdesign.view;

import android.annotation.SuppressLint;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.controller.SimulationController;
import com.example.dcdcconvertersdesign.helpers.Helpers;
import com.example.dcdcconvertersdesign.model.SimulationModel;
import com.example.dcdcconvertersdesign.simulationutils.CalculateBoostArrays;
import com.example.dcdcconvertersdesign.simulationutils.CalculateBuckBoostArrays;
import com.example.dcdcconvertersdesign.simulationutils.CalculateBuckArrays;
import com.example.dcdcconvertersdesign.simulationutils.GraphUtils;
import com.example.dcdcconvertersdesign.simulationutils.LimitsDialog;
import com.example.dcdcconvertersdesign.simulationutils.SaveDialog;
import com.example.dcdcconvertersdesign.simulationutils.SolveDiffEquations;
import com.github.mikephil.charting.charts.LineChart;

public class SimulationActivity extends AppCompatActivity {
    // define a tag for logging purposes
    private static final String TAG = "Simulation";
    private final SimulationModel model = new SimulationModel();
    private final SimulationController controller = new SimulationController(this, model);

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        Helpers.unsetActionBar(this);

        // retrieve extras from the intent
        Bundle bundle = getIntent().getExtras();

        controller.onCreateController(bundle);
    }

    @SuppressLint("SetTextI18n")
    public LineChart initChart(){
            TextView xLabel = findViewById(R.id.x_label);
            xLabel.setText("Time (ms)");
            return findViewById(R.id.chart);
    }

    public void handleDialogButtons(LineChart chart, GraphUtils graphUtils) {
        // Handling Limits button and open dialog:
        Button optionsButton = findViewById(R.id.options_button);

        // Set an onClickListener for the options_button
        optionsButton.setOnClickListener(v -> {
            controller.handleOptionsButton(chart, graphUtils);
        });

        // Handling Limits button and open dialog:
        Button saveGraphButton = findViewById(R.id.save_button);

        // Set an onClickListener for the options_button
        saveGraphButton.setOnClickListener(v -> {
            controller.handleSaveGraphButton(chart);
        });
    }

    public void alertBox(String alertString) {
        // create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the message to display in the dialog box
        builder.setMessage(alertString);
        // set a listener for when the user clicks outside of the dialog box to close it
        builder.setOnCancelListener(dialog1 -> {
            // do nothing, the dialog will just close
        });
        // create the dialog box and show it
        AlertDialog alert = builder.create();
        alert.show();
    }
}




