package com.example.dcdcconvertersdesign;

import static com.example.dcdcconvertersdesign.helpers.Helpers.stringFormat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.dcdcconvertersdesign.helpers.Helpers;

public class SnubberDesign extends AppCompatActivity {
    double inputCurrent, outputCurrent, outputVoltage, frequency;
    int flag;
    double capacitanceSnubber, resistanceSnubber, powerSnubber, timeDelayOff, timeDelayFall;
    TextView capacitanceSnubberTextView, resistanceSnubberTextView, powerSnubberTextView, capacitanceSnubberText, resistanceSnubberText, powerSnubberText;
    EditText timeDelayOffEditText, timeDelayFallEditText;
    ImageView snubberImage;
    ToggleButton resultsSnubberDesign;

    String TAG = "SnubberDesign";

    RelativeLayout capacitanceSnubberLayout, resistanceSnubberLayout, powerSnubberLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snubber_design);
        Helpers.setMinActionBar(this);
        createObjects();

        // Recovering data
        Bundle data3 = getIntent().getExtras();

        if (data3 != null) {
            retrieveDataFromAdvanced(data3);
            setSnubberImage();

            // Writing Results
            resultsSnubberDesign.setOnCheckedChangeListener((buttonView, btn) -> {
                if(btn) {
                    if (isEmpty()) {
                        Helpers.showToast(SnubberDesign.this, "Error! Something is empty");
                        return;
                    }

                    getValuesFromUI();
                    snubberEquations();
                    formatAndSetResources();
                }
            });
        }
    }

    public void createObjects() {
        // Text
        capacitanceSnubberText = findViewById(R.id.capacitance_snubber_text);
        resistanceSnubberText = findViewById(R.id.resistance_snubber_text);
        powerSnubberText = findViewById(R.id.power_snubber_text);

        // Get values from UI
        capacitanceSnubberTextView = findViewById(R.id.capacitance_snubber);
        resistanceSnubberTextView = findViewById(R.id.resistance_snubber);
        powerSnubberTextView = findViewById(R.id.power_snubber);
        timeDelayOffEditText = findViewById(R.id.time_delay_off);
        timeDelayFallEditText = findViewById(R.id.time_delay_fall);

        // Toggle Button
        resultsSnubberDesign = findViewById(R.id.results_snubber);

        // Image
        snubberImage = findViewById(R.id.snubber_image);

        // Layouts
        resistanceSnubberLayout = findViewById(R.id.resistance_snubber_layout);
        capacitanceSnubberLayout = findViewById(R.id.capacitance_snubber_layout);
        powerSnubberLayout = findViewById(R.id.power_snubber_layout);
    }

    private void setSnubberImage(){
        if(flag == 1){
            snubberImage.setImageResource(R.drawable.snubber_buck);
        }
        if(flag == 2){
            snubberImage.setImageResource(R.drawable.snubber_boost);
            // Create a new LayoutParams object to modify the existing layout parameters
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) snubberImage.getLayoutParams();

            // Set the layout margins to 8dp on both the start and end sides
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
            params.setMargins(margin, 0, margin, 0);

            // Apply the modified layout parameters to the ImageView
            snubberImage.setLayoutParams(params);
        }
        if(flag == 3){
            snubberImage.setImageResource(R.drawable.snubber_buck_boost);
        }
    }

    @SuppressLint("SetTextI18n")
    private void formatAndSetResources(){
        String formattedUnit;

        // Capacitance
        if(capacitanceSnubber > 1){
            formattedUnit = stringFormat(capacitanceSnubber) + " F";
            capacitanceSnubberTextView.setText(formattedUnit);
        }
        if(capacitanceSnubber >= 1e-3 && capacitanceSnubber < 1) {
            formattedUnit = stringFormat(capacitanceSnubber * 1e3) + " mF";
            capacitanceSnubberTextView.setText(formattedUnit);
        }
        if(capacitanceSnubber < 1e-3 && capacitanceSnubber >= 1e-6) {
            formattedUnit = stringFormat(capacitanceSnubber * 1e6) + " μF";
            capacitanceSnubberTextView.setText(formattedUnit);
        }
        if(capacitanceSnubber < 1e-6){
            formattedUnit = stringFormat(capacitanceSnubber * 1e9) + " nF";
            capacitanceSnubberTextView.setText(formattedUnit);
        }

        // Resistance
        if (resistanceSnubber >= 1e3) {
            formattedUnit = stringFormat(resistanceSnubber / 1e3) + " kΩ";
            resistanceSnubberTextView.setText(formattedUnit);
        }
        else if (resistanceSnubber >= 1) {
            formattedUnit = stringFormat(resistanceSnubber) + " Ω";
            resistanceSnubberTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(resistanceSnubber * 1e3) + " mΩ";
            resistanceSnubberTextView.setText(formattedUnit);
        }

        // Power
        if (powerSnubber >= 1e3) {
            formattedUnit = stringFormat(powerSnubber / 1e3) + " kW";
            powerSnubberTextView.setText(formattedUnit);
        }
        else if (powerSnubber >= 1) {
            formattedUnit = stringFormat(powerSnubber) + " W";
            powerSnubberTextView.setText(formattedUnit);
        }
        else {
            formattedUnit = stringFormat(powerSnubber * 1e3) + " mW";
            powerSnubberTextView.setText(formattedUnit);
        }

        capacitanceSnubberText.setText("Snubber Capacitor");
        capacitanceSnubberLayout.setVisibility(View.VISIBLE);
        resistanceSnubberText.setText("Snubber Resistor");
        resistanceSnubberLayout.setVisibility(View.VISIBLE);
        powerSnubberText.setText("Power Dissipated");
        powerSnubberLayout.setVisibility(View.VISIBLE);
    }

    public void getValuesFromUI() {
        // Convert time values from UI to double
        timeDelayOff = Double.parseDouble(timeDelayOffEditText.getText().toString());
        timeDelayFall = Double.parseDouble(timeDelayFallEditText.getText().toString());
    }

    public boolean isEmpty(){
        return timeDelayOffEditText.getText().toString().isEmpty() ||
                timeDelayFallEditText.getText().toString().isEmpty();
    }

    public void snubberEquations ()
    {
        // Buck
        if (flag == 1) {
            capacitanceSnubber = outputCurrent * (timeDelayFall + timeDelayOff) * 1e-9 / (2 * outputVoltage);
            resistanceSnubber = outputVoltage / (0.2 * outputCurrent);
            powerSnubber = 0.5 * capacitanceSnubber * Math.pow(outputVoltage, 2) * frequency;
        }
        // Boost
        if (flag == 2) {
            capacitanceSnubber = inputCurrent * (timeDelayFall + timeDelayOff) * 1e-9 / (2 * outputVoltage);
            resistanceSnubber = outputVoltage / (0.2 * inputCurrent);
            powerSnubber = 0.5 * capacitanceSnubber * Math.pow(outputVoltage, 2) * frequency;
        }
        // Buck Boost
        if (flag == 3) {
            capacitanceSnubber = inputCurrent * (timeDelayFall + timeDelayOff) * 1e-9 / (2 * outputVoltage);
            resistanceSnubber = outputVoltage / (0.2 * inputCurrent);
            powerSnubber = 0.5 * capacitanceSnubber * Math.pow(outputVoltage, 2) * frequency;
        }
    }
    private void retrieveDataFromAdvanced(Bundle data3) {
        inputCurrent = data3.getDouble("Input_Current");
        outputCurrent = data3.getDouble("Output_Current");
        outputVoltage = data3.getDouble("Output_Voltage");
        frequency = data3.getDouble("Frequency");
        flag = data3.getInt("Flag");
    }
}
