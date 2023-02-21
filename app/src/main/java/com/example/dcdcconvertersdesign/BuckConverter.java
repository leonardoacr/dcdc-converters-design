package com.example.dcdcconvertersdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BuckConverter extends AppCompatActivity {

    double Inductance_n, Inductance_Crit_n, Ii_n, Io_n, Is_n, Id_n, Vo_n,
            Vi_n, f_n, Delta_IL_n, Delta_VC_n, ILrms_n, Flag, Flag_Reverse = 0;
    TextView Duty_Cycle, Resistance, Capacitance, Inductance;
    TextView Inductance_T, Capacitance_T, ModeWarning;
    Button Simulation, Advanced;

    private void createObjects()
    {
        // Values
        Duty_Cycle = (TextView) findViewById(R.id.Duty_Cycle);
        Resistance = (TextView) findViewById(R.id.Resistance);
        Capacitance = (TextView) findViewById(R.id.Capacitance);
        Inductance = (TextView) findViewById(R.id.Inductance);

        // Texts
        Inductance_T = findViewById(R.id.Inductance_T);
        Capacitance_T = findViewById(R.id.Capacitance_T);
        ModeWarning = (TextView) findViewById(R.id.ModeWarning);

        // Buttons
        Simulation = (Button) findViewById(R.id.Simulation);
        Advanced = (Button) findViewById(R.id.Advanced);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buck_converter);
        createObjects();

        Bundle data = getIntent().getExtras();

        if(data != null) {

            // Recovering data MainActivity
            double Duty_Cycle_n = data.getDouble("Duty_Cycle");
            double Resistance_n = data.getDouble("Resistance");
            double Capacitance_n = data.getDouble("Capacitance");
            Inductance_n = data.getDouble("Inductance");
            Inductance_Crit_n = data.getDouble("Inductance_Crit");
            Ii_n = data.getDouble("Input_Current");
            Io_n = data.getDouble("Output_Current");
            Is_n = data.getDouble("Switch_Current");
            Id_n = data.getDouble("Diode_Current");
            Vi_n = data.getDouble("Input_Voltage");
            Vo_n = data.getDouble("Output_Voltage");
            f_n = data.getDouble("Frequency");
            Delta_IL_n = data.getDouble("DeltaIL");
            Delta_VC_n = data.getDouble("DeltaVC");
            ILrms_n = data.getDouble("ILrms");

            Flag = data.getDouble("Flag");

            // Writing Values
            Duty_Cycle.setText(String.format("%.2f", Duty_Cycle_n*100));
            Resistance.setText(String.format("%.2f", Resistance_n));

            // Capacitance
            if(Capacitance_n > 1){
                Capacitance.setText(String.format("%.4f", Capacitance_n));
                Capacitance_T.setText("Capacitance (F)");
            }
            if(Capacitance_n >= 1e-3 && Capacitance_n < 1) {
                Capacitance.setText(String.format("%.4f", Capacitance_n*1e3));
                Capacitance_T.setText("Capacitance (mF)");
            }
            if(Capacitance_n < 1e-3 && Capacitance_n >= 1e-6) {
                Capacitance.setText(String.format("%.4f", Capacitance_n*1e6));
                Capacitance_T.setText("Capacitance (uF)");
            }
            if(Capacitance_n < 1e-6){
                Capacitance.setText(String.format("%.4f", Capacitance_n*1e9));
                Capacitance_T.setText("Capacitance (nF)");
            }

            //Inductance
            if(Inductance_n > 1){
                Inductance.setText(String.format("%.4f", Inductance_n));
                Inductance_T.setText("Inductance (H)");
            }
            if(Inductance_n >= 1e-3 && Inductance_n < 1) {
                Inductance.setText(String.format("%.4f", Inductance_n * 1e3));
                Inductance_T.setText("Inductance (mH)");
            }
            if(Inductance_n < 1e-3 && Inductance_n >= 1e-6) {
                Inductance.setText(String.format("%.4f", Inductance_n * 1e6));
                Inductance_T.setText("Inductance (uH)");
            }
            if(Inductance_n < 1e-6){
                Inductance.setText(String.format("%.4f", Inductance_n*1e9));
                Inductance_T.setText("Inductance (nH)");
            }

            // Simulation
            Simulation.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if(Inductance_Crit_n <= Inductance_n){
                        // Continuous Conduction Mode (CCM)
                        Intent intentSimulation = new Intent(BuckConverter.this, Simulation.class);
                        Bundle simulationData = new Bundle();
                        simulationData.putDouble("Output_Voltage", Vo_n);
                        simulationData.putDouble("Input_Voltage", Vi_n);
                        simulationData.putDouble("Duty_Cycle", Duty_Cycle_n);
                        simulationData.putDouble("Inductance", Inductance_n);
                        simulationData.putDouble("Capacitance", Capacitance_n);
                        simulationData.putDouble("Resistance", Resistance_n);
                        simulationData.putDouble("Frequency", f_n);
                        simulationData.putDouble("Flag", Flag);

                        intentSimulation.putExtras(simulationData);

                        startActivity(intentSimulation);
                    } else {
                        ModeWarning.setText("Simulation not available for these parameters");
                    }
                }
            });

            // Advanced
            Advanced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Sending data to Advanced
                    Intent intentAdvanced = new Intent( BuckConverter.this, Advanced.class);
                    Bundle data2 = new Bundle();
                    data2.putDouble("Inductance", Inductance_n);
                    data2.putDouble("Inductance_Crit", Inductance_Crit_n);
                    data2.putDouble("Input_Current", Ii_n);
                    data2.putDouble("Output_Current", Io_n);
                    data2.putDouble("Switch_Current", Is_n);
                    data2.putDouble("Diode_Current", Id_n);
                    data2.putDouble("Output_Voltage", Vo_n);
                    data2.putDouble("Frequency", f_n);
                    data2.putDouble("DeltaIL", Delta_IL_n);
                    data2.putDouble("DeltaVC", Delta_VC_n);
                    data2.putDouble("ILrms", ILrms_n);
                    data2.putDouble("Flag", Flag);
                    data2.putDouble("Flag_Reverse", Flag_Reverse);

                    intentAdvanced.putExtras(data2);

                    startActivity( intentAdvanced );
                    }
            });
        }
    }
}
