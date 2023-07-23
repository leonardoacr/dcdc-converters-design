package com.example.dcdcconvertersdesigndemo.views;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dcdcconvertersdesigndemo.R;
import com.example.dcdcconvertersdesigndemo.controllers.SimulationParametersController;
import com.example.dcdcconvertersdesigndemo.helpers.Helpers;
import com.example.dcdcconvertersdesigndemo.interfaces.views.SimulationParametersViewInterface;

public class SimulationParametersView extends AppCompatActivity implements
        SimulationParametersViewInterface {
    private TextView switchingFrequencyTextView;
    private TextView timeStepTextView;
    private TextView timeStepText;
    private TextView requiredMemoryText;
    private TextView requiredMemoryBigText;
    private TextView requiredMemoryTextView;

    private TextView maxTimeRecommendedTextView;
    private TextView maxTimeRecommendedText;

    private EditText maxTimeEditText;
    private Button outputVoltageBtn;
    private Button outputCurrentBtn;
    private Button inputCurrentBtn;
    private Button switchCurrentBtn;
    private Button diodeCurrentBtn;
    private Button inductorCurrentBtn;
    private Button capacitorCurrentBtn;

    private RelativeLayout requiredMemoryLayout;
    private RelativeLayout maxTimeRecommendedLayout;
    @SuppressLint("StaticFieldLeak")
    private static ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation_parameters);
        Helpers.setMinActionBar(this);
        setUIComponents();

        // retrieve extras from the intent and init the controller
        Bundle bundle = getIntent().getExtras();
        SimulationParametersController controller = new SimulationParametersController(this);
        controller.onCreateController(bundle);

        maxTimeEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String maxTimeStr = v.getText().toString();
                controller.handleMaxTimeInput(maxTimeStr);
                return true;
            }
            return false;
        });
    }
    public void updateFrequency(double frequency) {
        switchingFrequencyTextView.setText(Helpers.formatValue(frequency, "Hz"));
    }

    public void updateTimeStep(double timeStep) {
        timeStepTextView.setText(Helpers.formatValue(timeStep, "s"));
        timeStepText.setText(R.string.time_step_text);
    }

    private void setUIComponents()
    {
        // Output Values
        switchingFrequencyTextView = findViewById(R.id.switching_frequency_simulation);
        timeStepTextView = findViewById(R.id.time_step_simulation);
        timeStepText = findViewById(R.id.time_step_text_simulation);
        maxTimeRecommendedTextView = findViewById(R.id.max_time_recommended_simulation);

        // Output Text
        requiredMemoryText = findViewById(R.id.required_memory_text);
        requiredMemoryBigText = findViewById(R.id.max_time_text_3);
        maxTimeRecommendedText = findViewById(R.id.max_time_recommended_text_simulation);

        // Input Values
        maxTimeEditText = findViewById(R.id.max_time_simulation);
        requiredMemoryTextView = findViewById(R.id.required_memory);

        // Buttons
        outputVoltageBtn = findViewById(R.id.output_voltage_btn_simulation);
        outputCurrentBtn = findViewById(R.id.output_current_btn_simulation);
        inputCurrentBtn = findViewById(R.id.input_current_btn_simulation);
        switchCurrentBtn = findViewById(R.id.switch_current_btn_simulation);
        diodeCurrentBtn = findViewById(R.id.diode_current_btn_simulation);
        inductorCurrentBtn = findViewById(R.id.inductor_current_btn_simulation);
        capacitorCurrentBtn = findViewById(R.id.capacitor_current_btn_simulation);

        // Relative Layout
        requiredMemoryLayout = findViewById(R.id.required_memory_layout);
        maxTimeRecommendedLayout = findViewById(R.id.max_time_recommended_layout);

        // Progress Bar
        progressBar = findViewById(R.id.progress_bar);

    }

    public void showSimulationTexts(double memoryCalculated, double maxTimeRecommended) {
        String memoryString = Helpers.stringFormat(memoryCalculated) + " MB";
        requiredMemoryTextView.setText(memoryString);
        requiredMemoryText.setText(R.string.required_memory_info);

        findViewById(R.id.simulation_options).setVisibility(View.VISIBLE);

        requiredMemoryBigText.setVisibility(View.VISIBLE);
        if(memoryCalculated > 10.05) {
            requiredMemoryBigText.setText(R.string.required_memory_big_text);
            requiredMemoryBigText.setTextColor(Color.RED);

            String maxTimeFormattedString = Helpers.stringFormat(maxTimeRecommended) + " ms";
            maxTimeRecommendedTextView.setText(maxTimeFormattedString);
            maxTimeRecommendedText.setText(R.string.max_time_recommended_info);
            maxTimeRecommendedLayout.setVisibility(View.VISIBLE);
        } else {
            requiredMemoryBigText.setText("");
        }

        requiredMemoryLayout.setVisibility(View.VISIBLE);
    }

    public void handleSimulationButtons(SimulationParametersController controller,
                                        double maxTime, double timeStep) {
        // Show buttons
        outputVoltageBtn.setVisibility(View.VISIBLE);
        outputCurrentBtn.setVisibility(View.VISIBLE);
        inputCurrentBtn.setVisibility(View.VISIBLE);
        diodeCurrentBtn.setVisibility(View.VISIBLE);
        inductorCurrentBtn.setVisibility(View.VISIBLE);
        capacitorCurrentBtn.setVisibility(View.VISIBLE);
        switchCurrentBtn.setVisibility(View.VISIBLE);

        outputVoltageBtn.setOnClickListener(view -> {
            showProgressBar();
            String receivedID = "outputVoltage";
            controller.navigateToSimulation(maxTime, timeStep, receivedID);
        });

        inputCurrentBtn.setOnClickListener(view -> {
            showProgressBar();
            String receivedID = "inputCurrent";
            controller.navigateToSimulation(maxTime, timeStep, receivedID);
        });

        switchCurrentBtn.setOnClickListener(view -> {
            showProgressBar();
            String receivedID = "switchCurrent";
            controller.navigateToSimulation(maxTime, timeStep, receivedID);
        });

        outputCurrentBtn.setOnClickListener(view -> {
            showProgressBar();
            String receivedID = "outputCurrent";
            controller.navigateToSimulation(maxTime, timeStep, receivedID);
        });

        diodeCurrentBtn.setOnClickListener(view -> {
            showProgressBar();
            String receivedID = "diodeCurrent";
            controller.navigateToSimulation(maxTime, timeStep, receivedID);
        });

        inductorCurrentBtn.setOnClickListener(view -> {
            showProgressBar();
            String receivedID = "inductorCurrent";
            controller.navigateToSimulation(maxTime, timeStep, receivedID);
        });

        capacitorCurrentBtn.setOnClickListener(view -> {
            showProgressBar();

            String receivedID = "capacitorCurrent";
            controller.navigateToSimulation(maxTime, timeStep, receivedID);
        });
    }
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
    public static void hideProgressBar() {
        if(progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
