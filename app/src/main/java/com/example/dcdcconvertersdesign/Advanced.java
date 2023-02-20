package com.example.dcdcconvertersdesign;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Advanced extends AppCompatActivity {

    double Inductance_n, Inductance_Crit_n, Ii_n, Io_n, Is_n, Id_n, Vo_n, f_n, Delta_IL_n, Delta_VC_n, ILrms_n, Flag, Flag_Reverse;
    TextView Inductance_Crit, Input_Current, Output_Current, Switch_Current, Diode_Current, Delta_IL, Delta_VC, Mode, obsinductor;
    Button Snubber_Project, Inductor_Project;

    private void createObjects() {
        Inductance_Crit = (TextView) findViewById(R.id.Inductance_Crit);
        Input_Current = (TextView) findViewById(R.id.Input_Current);
        Output_Current = (TextView) findViewById(R.id.Output_Current);
        Switch_Current = (TextView) findViewById(R.id.Switch_Current);
        Diode_Current = (TextView) findViewById(R.id.Diode_Current);
        Delta_IL = (TextView) findViewById(R.id.Delta_IL);
        Delta_VC = (TextView) findViewById(R.id.Delta_VC);
        Mode = (TextView) findViewById(R.id.Mode);
        obsinductor = findViewById(R.id.obsinductor);

        // Buttons
        Snubber_Project = (Button) findViewById(R.id.Snubber_Project);
        Inductor_Project = (Button) findViewById(R.id.Inductor_Project);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced);
        createObjects();

        Bundle data2 = getIntent().getExtras();

        if (data2 != null) {
            // Recovering data
            Inductance_n = data2.getDouble("Inductance");
            Inductance_Crit_n = data2.getDouble("Inductance_Crit");
            Ii_n = data2.getDouble("Input_Current");
            Io_n = data2.getDouble("Output_Current");
            Is_n = data2.getDouble("Switch_Current");
            Id_n = data2.getDouble("Diode_Current");
            Vo_n = data2.getDouble("Output_Voltage");
            f_n = data2.getDouble("Frequency");
            Delta_IL_n = data2.getDouble("DeltaIL");
            Delta_VC_n = data2.getDouble("DeltaVC");
            ILrms_n = data2.getDouble("ILrms");
            Flag = data2.getDouble("Flag");
            Flag_Reverse = data2.getDouble("Flag_Reverse");

            // Writing Values
            Inductance_Crit.setText(String.format("%.2f", Inductance_Crit_n * 1e6));
            Input_Current.setText(String.format("%.2f", Ii_n));
            Output_Current.setText(String.format("%.2f", Io_n));
            Switch_Current.setText(String.format("%.2f", Is_n));
            Diode_Current.setText(String.format("%.2f", Id_n));
            Delta_IL.setText(String.format("%.2f", Delta_IL_n));
            Delta_VC.setText(String.format("%.2f", Delta_VC_n));

            // Writing Mode
            if(Inductance_Crit_n <= Inductance_n){
                Mode.setText("Continuous Conduction Mode (CCM)");
            }
            else{
                Mode.setText("Discontinuous Conduction Mode (DCM)");
            }

            if(Flag_Reverse == 1){
                obsinductor.setText("For the reverse mode, the designed inductor shows the ideal component for the design");
            }


            // Snubber Project
            Snubber_Project.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Sending data to Snubber Project
                    Intent intentSnubber = new Intent(Advanced.this, SnubberProject.class);
                    Bundle data3 = new Bundle();

                    data3.putDouble("Output_Current", Io_n);
                    data3.putDouble("Input_Current", Ii_n);
                    data3.putDouble("Output_Voltage", Vo_n);
                    data3.putDouble("Frequency", f_n);
                    data3.putDouble("Flag", Flag);

                    intentSnubber.putExtras(data3);

                    startActivity(intentSnubber);
                }
            });

            // Inductor Project
            Inductor_Project.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Sending data to Inductor Project
                    Intent intentInductor = new Intent(Advanced.this, InductorProject.class);
                    Bundle data4 = new Bundle();

                    data4.putDouble("Inductance", Inductance_n);
                    data4.putDouble("ILrms", ILrms_n);
                    data4.putDouble("DeltaIL", Delta_IL_n);
                    data4.putDouble("Frequency", f_n);

                    intentInductor.putExtras(data4);

                    startActivity(intentInductor);
                }
            });
        }
    }
}
