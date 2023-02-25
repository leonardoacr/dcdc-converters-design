package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.dcdcconvertersdesign.convertersutilities.ConvertersUtils;
import static com.example.dcdcconvertersdesign.convertersutilities.ConvertersUtils.*;

public class BuckBoostConverter extends AppCompatActivity {
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters);
        ConvertersUtils convertersUtils = new ConvertersUtils();
        convertersUtils.createObjects(this);
        Bundle data = getIntent().getExtras();

        if(data != null) {
            // Recovering data MainActivity
            ConvertersUtils.recoverMainActivityData(data);
            convertersUtils.setConverterInfo();
            convertersUtils.formatValues();

            // Simulation
            convertersUtils.Simulation.setOnClickListener(v -> {
                // Continuous Conduction Mode (CCM)
                if(Inductance_Crit_n <= Inductance_n){
                    Intent intentSimulationDefinitions = new Intent(
                            BuckBoostConverter.this, SimulationDefinitions.class);
                    ConvertersUtils.sendSimulationData(BuckBoostConverter.this, intentSimulationDefinitions);
                } else {
                    convertersUtils.ModeWarning.setText("Simulation not available for these parameters");
                }
            });

            // Advanced
            convertersUtils.Advanced.setOnClickListener(v -> {
                // Sending data to Advanced
                Intent intentAdvanced = new Intent( BuckBoostConverter.this, Advanced.class);
                ConvertersUtils.sendAdvancedData(BuckBoostConverter.this, intentAdvanced);
            });
        }
    }
}
