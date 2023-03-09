package com.example.dcdcconvertersdesign.views;

import static com.example.dcdcconvertersdesign.helpers.Helpers.showToast;

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

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.controllers.InductorDesignController;
import com.example.dcdcconvertersdesign.helpers.Helpers;
import com.example.dcdcconvertersdesign.models.InductorDesignModel;

import java.util.Locale;

public class InductorDesignActivity extends AppCompatActivity {

    private ToggleButton resultsInductorDesign;
    private Button tableBtn, exampleBtn;
    private TextView coreModelText, airGapText, sizePercentText, turnNumberText, awgText, parallelConductorsText;
    private TextView coreModelTextView, airGapTextView, sizePercentTextView, turnNumberTextView, awgTextView, parallelConductorsTextView;
    private EditText jmaxEditText, bmaxEditText, kuEditText;
    private ImageView inductorImage;

    private RelativeLayout sizePercentLayout, airGapLayout, coreModelLayout, turnNumberLayout,
            awgLayout, parallelConductorsLayout;

        // Tables
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inductor_design);
        Helpers.setMinActionBar(this);
        setUIComponents();
        setInductorImage();

        Bundle bundle = getIntent().getExtras();

        InductorDesignModel model = new InductorDesignModel();
        InductorDesignController controller = new InductorDesignController(this, model);

        controller.onCreateController(bundle);

        resultsInductorDesign.setOnCheckedChangeListener((buttonView, btn) -> {
            if(btn){
                controller.onResultsClicked();

                tableBtn.setOnClickListener(v -> controller.onTableClicked());

            }
        });

        // Example button listener
        exampleBtn.setOnClickListener(v -> {
            kuEditText.setText("70");
            jmaxEditText.setText("450");
            bmaxEditText.setText("0.3");
        });
    }

    public void setUIComponents() {
        // Text
        coreModelText = findViewById(R.id.core_model_text_inductor_design);
        airGapText = findViewById(R.id.air_gap_text_inductor_design);
        sizePercentText = findViewById(R.id.size_percent_text_inductor_design);
        turnNumberText = findViewById(R.id.turn_number_text_inductor_design);
        awgText = findViewById(R.id.awg_text_inductor_design);
        parallelConductorsText = findViewById(R.id.parallel_conductors_text_inductor_design);

        // Text
        coreModelTextView = findViewById(R.id.core_model_inductor_design);
        airGapTextView = findViewById(R.id.air_gap_inductor_design);
        sizePercentTextView = findViewById(R.id.size_percent_inductor_design);
        turnNumberTextView = findViewById(R.id.turn_number_inductor_design);
        awgTextView = findViewById(R.id.awg_inductor_design);
        parallelConductorsTextView = findViewById(R.id.parallel_conductors_inductor_design);

        // Inputs
        jmaxEditText = findViewById(R.id.jmax_constant_inductor_design);
        bmaxEditText = findViewById(R.id.bmax_inductor_design);
        kuEditText = findViewById(R.id.ku_inductor_design);

        // Button
        tableBtn = findViewById(R.id.table_button_inductor_design);
        exampleBtn = findViewById(R.id.example_inductor_design_btn);

        // Toggle Button
        resultsInductorDesign = findViewById(R.id.results_inductor_design);

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

    private void setInductorImage() {
        inductorImage.setImageResource(R.drawable.inductor_model);
    }

    @SuppressLint("SetTextI18n")
    public void updateUITexts() {
        // Writing Text
        coreModelText.setText("Core Model");
        airGapText.setText("Air Gap (mm)");
        sizePercentText.setText("Size Percent (%)");
        turnNumberText.setText("Number of Turns");
        awgText.setText("AWG");
        parallelConductorsText.setText("Parallel Conductors");
    }

    public void updateUIValues(double airGap, double areaPercentage, double turnNumber,
                               double awg, double parallelConductors, String coreModel) {
        coreModelTextView.setText(coreModel);
        airGapTextView.setText(Helpers.stringFormat(airGap*1000));
        sizePercentTextView.setText(Helpers.stringFormat(areaPercentage));
        turnNumberTextView.setText(String.format(Locale.US, "%d", (int) turnNumber));
        awgTextView.setText(String.format(Locale.US, "%d", (int) awg));
        parallelConductorsTextView.setText(String.format(Locale.US, "%d",
                (int) parallelConductors));
    }

    public void updateUILayouts() {
        sizePercentLayout.setVisibility(View.VISIBLE);
        airGapLayout.setVisibility(View.VISIBLE);
        coreModelLayout.setVisibility(View.VISIBLE);
        turnNumberLayout.setVisibility(View.VISIBLE);
        awgLayout.setVisibility(View.VISIBLE);
        parallelConductorsLayout.setVisibility(View.VISIBLE);
    }

    public void setTableButtonVisible() {
        tableBtn.setVisibility(View.VISIBLE);
    }

    public void setCoreError(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isEmpty() {
        EditText[] fields = {jmaxEditText, bmaxEditText, kuEditText};
        for (EditText field : fields) {
            if (field.getText().toString().isEmpty()) {
                showToast(this, "Error! Something is empty");
                return true;
            }
        }
        return false;
    }

    public Double getJmax() {
        return Double.parseDouble(jmaxEditText.getText().toString()); // current density
    }

    public Double getBmax() {
        return Double.parseDouble(bmaxEditText.getText().toString()); // flux density
    }

    public Double getKu() {
        return Double.parseDouble(kuEditText.getText().toString()) / 100; // winding fill factor
    }
}