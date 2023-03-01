package com.example.dcdcconvertersdesign;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SnubberProject extends AppCompatActivity {
    double Ii_n, Io_n, Vo_n, f_n, Flag;
    double Cs_n, Rs_n, Ps_n, Tdelayoff_n, Tdelayfall_n;
    TextView Cs, Rs, Ps, Cs_T, Rs_T, Ps_T;
    EditText Tdelayoff, Tdelayfall;
    ImageView Snubberimg;
    ToggleButton Results;

    public void createObjects() {
        // Text
        Cs_T = (TextView) findViewById(R.id.Cs_T);
        Rs_T = (TextView) findViewById(R.id.Rs_T);
        Ps_T = (TextView) findViewById(R.id.Ps_T);

        // Text Values
        Cs = (TextView) findViewById(R.id.Cs);
        Rs = (TextView) findViewById(R.id.Rs);
        Ps = (TextView) findViewById(R.id.Ps);

        // Values
        Tdelayoff = (EditText) findViewById(R.id.Tdelayoff);
        Tdelayfall = (EditText) findViewById(R.id.Tdelayfall);

        // Toggle Button
        Results = (ToggleButton) findViewById(R.id.Results);

        // Image
        Snubberimg = (ImageView) findViewById(R.id.Snubberimg);

    }

    public void Values() {
        // Convert Values to Double
        Tdelayoff_n = Double.parseDouble(Tdelayoff.getText().toString());
        Tdelayfall_n = Double.parseDouble(Tdelayfall.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snubber_project);
        createObjects();

        // Recovering data
        Bundle data3 = getIntent().getExtras();

        if (data3 != null) {
            Ii_n = data3.getDouble("Input_Current");
            Io_n = data3.getDouble("Output_Current");
            Vo_n = data3.getDouble("Output_Voltage");
            f_n = data3.getDouble("Frequency");
            Flag = data3.getDouble("Flag");

            // Writing Results
            Results.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                    if(b) {

                        if (isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Error! Something is empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Values();
                        Snubber_Calculations();

                        Cs.setText(String.format("%.2f", Cs_n * 1e9));
                        Rs.setText(String.format("%.2f", Rs_n));
                        Ps.setText(String.format("%.2f", Ps_n * 1e3));

                        Cs_T.setText("Snubber Capacitor (nF)");
                        Rs_T.setText("Snubber Resistor (Ohms)");
                        Ps_T.setText("Power Dissipated (mW)");

                        if(Flag == 1){
                            Snubberimg.setImageResource(R.drawable.snubberbuck);
                        }
                        if(Flag == 2){
                            Snubberimg.setImageResource(R.drawable.snubberboost);
                        }
                        if(Flag == 3){
                            Snubberimg.setImageResource(R.drawable.snubberbuckboost);
                        }
                    }
                }
            });
        }
    }
    public boolean isEmpty(){
        return Tdelayoff.getText().toString().isEmpty() ||
                Tdelayfall.getText().toString().isEmpty();
    }

        public void Snubber_Calculations ()
        {
            // Buck (talvez esteja errado e n seja Vo_n e sim Vi - Vo)
            if (Flag == 1) {
                Cs_n = Io_n * (Tdelayfall_n + Tdelayoff_n) * 1e-9 / (2 * Vo_n);
                Rs_n = Vo_n / (0.2 * Io_n);
                Ps_n = 0.5 * Cs_n * Math.pow(Vo_n, 2) * f_n;
            }
            // Boost
            if (Flag == 2) {
                Cs_n = Ii_n * (Tdelayfall_n + Tdelayoff_n) * 1e-9 / (2 * Vo_n);
                Rs_n = Vo_n / (0.2 * Ii_n);
                Ps_n = 0.5 * Cs_n * Math.pow(Vo_n, 2) * f_n;
            }
            // Buck Boost (Tambem, e ver a corrente certa)
            if (Flag == 3) {
                Cs_n = Ii_n * (Tdelayfall_n + Tdelayoff_n) * 1e-9 / (2 * Vo_n);
                Rs_n = Vo_n / (0.2 * Ii_n);
                Ps_n = 0.5 * Cs_n * Math.pow(Vo_n, 2) * f_n;
            }
        }
}
