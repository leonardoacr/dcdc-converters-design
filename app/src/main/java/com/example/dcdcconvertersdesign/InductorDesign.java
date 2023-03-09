package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.dcdcconvertersdesign.helpers.Helpers;

import java.util.Locale;

public class InductorDesign extends AppCompatActivity {

    private ToggleButton resultsInductorDesign;
    private Button tableBtn, exampleBtn;
    private TextView coreModelText, airGapText, sizePercentText, turnNumberText, awgText, parallelConductorsText;
    private TextView coreModelTextView, airGapTextView, sizePercentTextView, turnNumberTextView, awgTextView, parallelConductorsTextView;
    private EditText jmaxEditText, bmaxEditText, kuEditText;
    private ImageView inductorImage;
    private String coreModel;
    private double jmax, bmax, ku;
    private double inductance, inductorCurrentRMS, frequency, deltaInductorCurrent;
    private double apCalculated, ap, ae, Aw, flagGeneral, error, maximumDiameter, copperDiameter;
    private double airGap, areaPercentage, percentageOfUsableArea, u0, ur, p, pi = 3.1415;
    private double sf, st, isolatedConductorArea, turnNumber, awg, parallelConductors;
    private int  flag, i, i_N, Nc, Nl, Cl, Cc;

    private RelativeLayout sizePercentLayout, airGapLayout, coreModelLayout, turnNumberLayout,
            awgLayout, parallelConductorsLayout;

        // Tables
        String[] models =
                {"EE-20/15",
                "EE-30/07",
                "EE-30/14",
                "EE-42/15",
                "EE-42/20",
                "EE-55/21",
                "EE-65/13",
                "EE-65/26",
                "EE-65/39"};

        double[][] cores =
                {{0.312, 0.26, 0.08112},
                {0.6, 0.8, 0.48},
                {1.2, 0.85, 1.02},
                {1.81, 1.57, 2.8417},
                {2.4, 1.57, 3.768},
                {3.54, 2.5, 8.85},
                {2.66, 3.7, 9.842},
                {5.32, 3.7, 19.684},
                {7.98, 3.7, 29.526}};

        double[][] conductors =
                        {{10, 0.259, 0.273, 23.679, 0.052620, 0.058572},
                        {11, 0.231, 0.244, 18.778, 0.041729, 0.046738},
                        {12, 0.205, 0.218, 14.892, 0.033092, 0.037309},
                        {13, 0.183, 0.195, 11.809, 0.026243, 0.029793},
                        {14, 0.163, 0.174, 9.365, 0.020811, 0.0238},
                        {15, 0.145, 0.156, 7.427, 0.016504, 0.019021},
                        {16, 0.129, 0.139, 5.89, 0.013088, 0.015207},
                        {17, 0.115, 0.124, 4.671, 0.010379, 0.012164},
                        {18, 0.102, 0.111, 3.704, 0.008231, 0.009735},
                        {19, 0.091, 0.1, 2.937, 0.006527, 0.007794},
                        {20, 0.081, 0.089, 2.329, 0.005176, 0.006244},
                        {21, 0.072, 0.08, 1.847, 0.004105, 0.005004},
                        {22, 0.064, 0.071, 1.465, 0.003255, 0.004013},
                        {23, 0.057, 0.064, 1.162, 0.002582, 0.003221},
                        {24, 0.051, 0.057, 0.921, 0.002047, 0.002586},
                        {25, 0.045, 0.051, 0.731, 0.001624, 0.0020778},
                        {26, 0.04, 0.046, 0.579, 0.001287, 0.001671},
                        {27, 0.036, 0.041, 0.459, 0.001021, 0.001344},
                        {28, 0.032, 0.037, 0.364, 0.00081, 0.001083},
                        {29, 0.029, 0.033, 0.289, 0.000642, 0.000872},
                        {30, 0.025, 0.03, 0.229, 0.000509, 0.000704},
                        {31, 0.023, 0.027, 0.182, 0.000404, 0.000568},
                        {32, 0.02, 0.024, 0.144, 0.00032, 0.000459},
                        {33, 0.018, 0.022, 0.114, 0.000254, 0.000371},
                        {34, 0.016, 0.02, 0.091, 0.000201, 0.0003},
                        {35,0.014, 0.018, 0.072, 0.00016, 0.000243},
                        {36, 0.013, 0.016, 0.057, 0.000127, 0.000197},
                        {37, 0.011, 0.014, 0.045, 0.0001, 0.00016},
                        {38, 0.01, 0.013, 0.036, 0.00008, 0.00013},
                        {39, 0.009, 0.012, 0.028, 0.000063, 0.000106},
                        {40, 0.008, 0.01, 0.023, 0.00005, 0.000086},
                        {41, 0.007, 0.009, 0.018, 0.00004, 0.00007}};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inductor_design);
        Helpers.setMinActionBar(this);
        createObjects();
        setInductorImage();

        resultsInductorDesign.setOnCheckedChangeListener((buttonView, btn) -> {
            if(btn){
                if (isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error! Something is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                convertInputsToDouble();
                retrieveDataFromAdvanced();
                inductorDesignEquations();
                formatAndSetResources();

                tableBtn.setOnClickListener(v -> {

                    Intent intent = new Intent(InductorDesign.this, InductorDesignTable.class);

                    startActivity(intent);
                });

            }
        });

        // Example button listener
        exampleBtn.setOnClickListener(v -> {
            kuEditText.setText("70");
            jmaxEditText.setText("450");
            bmaxEditText.setText("0.3");
        });
    }

    public void createObjects() {
        // Text
        coreModelText = (TextView) findViewById(R.id.core_model_text_inductor_design);
        airGapText = (TextView) findViewById(R.id.air_gap_text_inductor_design);
        sizePercentText = (TextView) findViewById(R.id.size_percent_text_inductor_design);
        turnNumberText = (TextView) findViewById(R.id.turn_number_text_inductor_design);
        awgText = (TextView) findViewById(R.id.awg_text_inductor_design);
        parallelConductorsText = (TextView) findViewById(R.id.parallel_conductors_text_inductor_design);

        // Text
        coreModelTextView = (TextView) findViewById(R.id.core_model_inductor_design);
        airGapTextView = (TextView) findViewById(R.id.air_gap_inductor_design);
        sizePercentTextView = (TextView) findViewById(R.id.size_percent_inductor_design);
        turnNumberTextView = (TextView) findViewById(R.id.turn_number_inductor_design);
        awgTextView = (TextView) findViewById(R.id.awg_inductor_design);
        parallelConductorsTextView = (TextView) findViewById(R.id.parallel_conductors_inductor_design);

        // Inputs
        jmaxEditText = (EditText) findViewById(R.id.jmax_constant_inductor_design);
        bmaxEditText = (EditText) findViewById(R.id.bmax_inductor_design);
        kuEditText = (EditText) findViewById(R.id.ku_inductor_design);

        // Button
        tableBtn = (Button) findViewById(R.id.table_button_inductor_design);
        exampleBtn = (Button) findViewById(R.id.example_inductor_design_btn);

        // Toggle Button
        resultsInductorDesign = (ToggleButton) findViewById(R.id.results_inductor_design);

        // Image
        inductorImage = findViewById(R.id.inductor_image);

        // Layouts
        sizePercentLayout = findViewById(R.id.size_percent_inductor_layout);
        airGapLayout = findViewById(R.id.air_gap_inductor_layout);
        coreModelLayout = findViewById(R.id.core_model_inductor_layout);
        turnNumberLayout = findViewById(R.id.turn_number_inductor_layout);
        awgLayout = findViewById(R.id.awg_inductor_layout);
        parallelConductorsLayout = findViewById(R.id.parallel_conductors_inductor_layout);

    }

    public boolean isEmpty() {
        return jmaxEditText.getText().toString().isEmpty() ||
                bmaxEditText.getText().toString().isEmpty() ||
                kuEditText.getText().toString().isEmpty();
    }

    public void convertInputsToDouble() {
        jmax = Double.parseDouble(jmaxEditText.getText().toString()); // current density
        bmax = Double.parseDouble(bmaxEditText.getText().toString()); // flux density
        ku = Double.parseDouble(kuEditText.getText().toString()) / 100; // winding fill factor
    }

    private void setInductorImage() {
        inductorImage.setImageResource(R.drawable.inductor_model);
    }

    @SuppressLint("SetTextI18n")
    private void formatAndSetResources() {
        // Writing Text
        coreModelText.setText("Core Model");
        airGapText.setText("Air Gap (mm)");
        sizePercentText.setText("Size Percent (%)");
        turnNumberText.setText("Number of Turns");
        awgText.setText("AWG");
        parallelConductorsText.setText("Parallel Conductors");

        // Writing Values
        coreModelTextView.setText(coreModel);
        airGapTextView.setText(String.format(Locale.US, "%.4f", airGap*1000));
        sizePercentTextView.setText(String.format(Locale.US, "%.2f", areaPercentage));
        turnNumberTextView.setText(String.format(Locale.US, "%.0f", turnNumber));
        awgTextView.setText(String.format(Locale.US, "%.0f", awg));
        parallelConductorsTextView.setText(String.format(Locale.US, "%.0f", parallelConductors));

        // Set layouts visible
        sizePercentLayout.setVisibility(View.VISIBLE);
        airGapLayout.setVisibility(View.VISIBLE);
        coreModelLayout.setVisibility(View.VISIBLE);
        turnNumberLayout.setVisibility(View.VISIBLE);
        awgLayout.setVisibility(View.VISIBLE);
        parallelConductorsLayout.setVisibility(View.VISIBLE);

        // Set table button visible
        tableBtn.setVisibility(View.VISIBLE);
    }

    public void inductorDesignEquations() {
        // Product of areas

        apCalculated = inductance * deltaInductorCurrent * inductorCurrentRMS * 1e4 /
                (ku * bmax * jmax); // copper winding cross-sectional area
        Nc = 2; //
        Nl = 8;
        flagGeneral = 0;
        error = 0;

        while (flagGeneral == 0) {

            flag = 0;

            if (apCalculated <= 29.526 && apCalculated >= 0.08112) {
                for (i = Nl; i >= 0; i--){
                    if (i > 0 && flag == 0) {
                        if (cores[i - 1][Nc] <= apCalculated) {
                            ap = cores[i][Nc];
                            coreModel = models[i];
                            ae = cores[i][0];
                            i_N = i;
                            flag = 1;
                        }
                    }
                }
            }

            if (apCalculated > 29.526) {
                i_N = Nl;
                ap = cores[Nl][Nc];
                coreModel = models[Nl];
                ae = cores[Nl][0];
            }

            if (apCalculated < 0.08112) {
                i_N = 0;
                ap = cores[0][Nc];
                coreModel = models[0];
                ae = cores[0][0];
            }

            Aw = ap / ae;
            // Conductor Calculation to be used
            turnNumber = inductance * (inductorCurrentRMS + deltaInductorCurrent) * 1e4 / (bmax * ae);
            st = inductorCurrentRMS / jmax;
            p = 1.72e-4;
            u0 = 4 * pi * 1e-7;
            ur = 1;
            maximumDiameter = 2 * Math.sqrt(p / (pi * u0 * ur * frequency));

            // Searching in the table
            Cl = 31;
            Cc = 5;
            flag = 0;

            if (maximumDiameter >= 0.007 && maximumDiameter <= 0.259) {
                for (i = 0; i <= Cl; i++){
                    if (flag == 0) {
                        if (conductors[i + 1][1] < maximumDiameter) {
                            copperDiameter = conductors[i + 1][1];
                            awg = conductors[i + 1][0];
                            sf = conductors[i + 1][4];
                            isolatedConductorArea = conductors[i + 1][5];
                            flag = 1;
                        }
                    }
                }
            }

            if (maximumDiameter < 0.007) {
                copperDiameter = conductors[Cl][1];
                awg = conductors[Cl][0];
                sf = conductors[Cl][4];
                isolatedConductorArea = conductors[Cl][5];
            }

            if (maximumDiameter > 0.259) {
                copperDiameter = conductors[0][1];
                awg = conductors[0][0];
                sf = conductors[0][4];
                isolatedConductorArea = conductors[0][5];
            }

            parallelConductors = st / sf;

            // Gets the next integer greater than the calculated
            turnNumber = Math.ceil(turnNumber);
            parallelConductors = Math.ceil(parallelConductors);

            areaPercentage = parallelConductors * turnNumber * isolatedConductorArea * 100 / Aw;
            percentageOfUsableArea = 100;

            if (areaPercentage <= percentageOfUsableArea) {
                flagGeneral = 1;
            }

            if (areaPercentage > percentageOfUsableArea) {
                if (1 + i_N > Nl) {
                    Toast.makeText(getApplicationContext(), "Error! Doesn't exist core" +
                            " registered for this operation frequency", Toast.LENGTH_SHORT).show();
                    error = 1;
                    return;
                } else {
                    apCalculated = cores[1 + i_N][Nc] - 1e-3;
                }
            }
        }
            airGap = Math.pow(turnNumber, 2) * u0 * ur * ae * 1e-4 / inductance;
    }
    private void retrieveDataFromAdvanced() {
        Bundle data4 = getIntent().getExtras();
        inductance = data4.getDouble("Inductance");
        inductorCurrentRMS = data4.getDouble("Inductor_Current_RMS");
        deltaInductorCurrent = data4.getDouble("DeltaIL");
        frequency = data4.getDouble("Frequency");
    }
}