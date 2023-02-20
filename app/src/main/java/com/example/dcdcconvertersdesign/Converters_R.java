package com.example.dcdcconvertersdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Converters_R extends AppCompatActivity {

    // Define
    double Ii_n, Io_n, Id_n, Is_n, Vo_n, f_n, Delta_IL_n, Delta_VC_n, ILrms_n;
    double Po_n, Ripple_IL_n, Ripple_VC_n, Duty_Cycle_n, Inductance_Crit_n, L_n;
    int Flag, Flag_Reverse = 1;
    TextView Title, Po_T, Ripple_IL_T, Ripple_VC_T, Duty_Cycle_T;
    TextView Po, Ripple_IL, Ripple_VC, Duty_Cycle;
    ImageView Converter;
    Button Advanced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converters__r);
        createObjects();

        Bundle data = getIntent().getExtras();

        if(data != null) {

            // Recovering data Converters
            // Values
            Duty_Cycle_n = data.getDouble("Duty_Cycle");
            Inductance_Crit_n = data.getDouble("Inductance_Crit");
            Ripple_IL_n = data.getDouble("Rippleil");
            Ripple_VC_n = data.getDouble("Ripplevc");
            Po_n = data.getDouble("Po");
            L_n = data.getDouble("Inductance");

            Flag = data.getInt("Flag");

            // Advanced
            Ii_n = data.getDouble("Input_Current");
            Io_n = data.getDouble("Output_Current");
            Is_n = data.getDouble("Switch_Current");
            Id_n = data.getDouble("Diode_Current");
            Vo_n = data.getDouble("Output_Voltage");
            f_n = data.getDouble("Frequency");
            Delta_IL_n = data.getDouble("DeltaIL");
            Delta_VC_n = data.getDouble("DeltaVC");
            ILrms_n = data.getDouble("ILrms");

            Write_Results();

            // Advanced
            Advanced.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Sending data to Advanced
                    Intent intentAdvanced = new Intent( getApplicationContext(), Advanced.class);
                    Bundle data2 = new Bundle();
                    data2.putDouble("Inductance", L_n);
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

    public void createObjects() {
        // Texts
        Title = findViewById(R.id.Title);
        Po_T = findViewById(R.id.Po_T);
        Ripple_IL_T = findViewById(R.id.Ripple_IL_T);
        Ripple_VC_T = findViewById(R.id.Ripple_VC_T);
        Duty_Cycle_T = findViewById(R.id.Duty_Cycle_T);

        // Values
        Po = findViewById(R.id.Po);
        Ripple_IL = findViewById(R.id.Ripple_IL);
        Ripple_VC = findViewById(R.id.Ripple_VC);
        Duty_Cycle = findViewById(R.id.Duty_Cycle);

        // Button
        Advanced = findViewById(R.id.Advanced);

        // Image
        Converter = findViewById(R.id.Converter);
    }

    public void Write_Results() {
        // Texts
        Po_T.setText("Power Output (W)");
        Ripple_IL_T.setText("Ripple Inductor Current (%)");
        Ripple_VC_T.setText("Ripple Capacitor Voltage (%)");
        Duty_Cycle_T.setText("Duty Cycle (%)");

        // Values
        Po.setText(String.format("%.2f", Po_n));
        Ripple_IL.setText(String.format("%.2f", Ripple_IL_n));
        Ripple_VC.setText(String.format("%.2f", Ripple_VC_n));
        Duty_Cycle.setText(String.format("%.2f", Duty_Cycle_n*100));

        // Image print and Title
        if(Flag == 1){
            Converter.setImageResource(R.drawable.buck);
            Title.setText("Buck Converter");
        }
        if(Flag == 2){
            Converter.setImageResource(R.drawable.boost);
            Title.setText("Boost Converter");
        }
        if(Flag == 3){
            Converter.setImageResource(R.drawable.buck_boost);
            Title.setText("Buck-Boost Converter");
        }

    }
}
