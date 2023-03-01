package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesign.convertersutilities.ConvertersUtils;
import com.example.dcdcconvertersdesign.helpers.Helpers;

import static com.example.dcdcconvertersdesign.UsualDesign.inductanceCritical;
import static com.example.dcdcconvertersdesign.convertersutilities.ConvertersUtils.*;

import java.util.Objects;

public class BoostConverter extends AppCompatActivity {
//    private String TAG = "BoostConverter";
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters);
        Helpers.setMinActionBar(this);
        ConvertersUtils convertersUtils = new ConvertersUtils();
        convertersUtils.createObjects(this);
        Bundle data = getIntent().getExtras();

        if(data != null) {
            // Recovering data MainActivity
            ConvertersUtils.recoverMainActivityData(data);
            convertersUtils.setConverterInfo();
            convertersUtils.formatAndSetValues();

            // Simulation
            convertersUtils.Simulation.setOnClickListener(v -> {
                // Continuous Conduction Mode (CCM)
                if(inductanceCritical <= inductance){
                    Intent intentSimulationDefinitions = new Intent(
                            BoostConverter.this, SimulationParameters.class);
                    ConvertersUtils.sendSimulationData(BoostConverter.this, intentSimulationDefinitions);
                } else {
                    convertersUtils.modeWarning.setText("Simulation not available for DCM Mode");
                }
            });

            // Advanced
            convertersUtils.Advanced.setOnClickListener(v -> {
                // Sending data to Advanced
                Intent intentAdvanced = new Intent( BoostConverter.this, Advanced.class);
                ConvertersUtils.sendAdvancedData(BoostConverter.this, intentAdvanced);
            });
        }
    }
}
