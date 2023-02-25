package com.example.dcdcconvertersdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class UsualDesign extends AppCompatActivity {
    private String TAG = "UsualDesign";
    double Vi_n, Vo_n, Po_n, Ripple_IL_n, Ripple_VC_n, f_n, n_n, Is_n, Id_n, ILrms_n;
    double Ii_n, Io_n, ILmax_n, ILmin_n, Vomax_n, Vomin_n, Delta_IL_n, Delta_VC_n;
    double Duty_Cycle_n, Resistance_n, Capacitance_n, Inductance_n, Inductance_Crit_n;
    private int flag;
    EditText Vi, Vo, Po, Ripple_IL, Ripple_VC, f, n;
    Button Buck, Boost, Buck_Boost;

    public void createObjects() {
        // Values
        Vi = findViewById(R.id.Vi);
        Vo = findViewById(R.id.Vo);
        Po = findViewById(R.id.Po);
        Ripple_IL = findViewById(R.id.Ripple_IL);
        Ripple_VC = findViewById(R.id.Ripple_VC);
        f = findViewById(R.id.f);
        n = findViewById(R.id.n);

        // Buttons
        Buck = findViewById(R.id.Buck);
        Boost = findViewById(R.id.Boost);
        Buck_Boost = findViewById(R.id.Buck_Boost);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usual_design);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon);
        createObjects();

        // Buck Converter
        Buck.setOnClickListener(v -> {
            if (isEmpty()) {
                Toast.makeText(UsualDesign.this, "Error! Something is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            getValues();
            Buck_Calculations();
            Log.d(TAG, "wtf");

            if (Vi_n > Vo_n && Duty_Cycle_n <= 0.95 && Duty_Cycle_n >= 0.05 && Resistance_n > 0 && Inductance_n > 0 && Capacitance_n > 0 && n_n <= 100) {
                flag = 1;
                Log.d(TAG, "Flag: " + flag);
                // Sending data
                Intent intent = new Intent(UsualDesign.this, BuckConverter.class);
                Bundle data = new Bundle();

                setBundleVariables(data);
                intent.putExtras(data);
                startActivity(intent);
            }
            verifyInputErrorsBuck();
        });

        // Boost Converter
        Boost.setOnClickListener(v -> {
            if (isEmpty()) {
                Toast.makeText(UsualDesign.this, "Error! Something is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            getValues();
            Boost_Calculations();

            if (Vi_n < Vo_n && Duty_Cycle_n <= 0.95 && Duty_Cycle_n >= 0.05 && Resistance_n > 0 && Inductance_n > 0 && Capacitance_n > 0 && n_n <= 100) {
                flag = 2;

                // Sending data
                Intent intent = new Intent(UsualDesign.this, BoostConverter.class);
                Bundle data = new Bundle();

                setBundleVariables(data);
                intent.putExtras(data);
                startActivity(intent);
            }
            verifyInputErrorsBoost();
        });

        // Buck_Boost Calculations
        Buck_Boost.setOnClickListener(v -> {
            if (isEmpty()) {
                Toast.makeText(UsualDesign.this, "Error! Something is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            getValues();
            Buck_Boost_Calculations();

            if (Vi_n != Vo_n && Duty_Cycle_n <= 0.95 && Duty_Cycle_n >= 0.05 && Resistance_n > 0 && Inductance_n > 0 && Capacitance_n > 0 && n_n <= 100) {
                flag = 3;

                // Sending data
                Intent intent = new Intent(UsualDesign.this, BuckBoostConverter.class);
                Bundle data = new Bundle();

                setBundleVariables(data);
                intent.putExtras(data);
                startActivity(intent);
            }
            verifyInputErrorsBuckBoost();
        });
    }
    private boolean isEmpty() {
        return Vi.getText().toString().isEmpty() ||
                Vo.getText().toString().isEmpty() ||
                Po.getText().toString().isEmpty() ||
                Ripple_IL.getText().toString().isEmpty() ||
                Ripple_VC.getText().toString().isEmpty() ||
                f.getText().toString().isEmpty() ||
                n.getText().toString().isEmpty();
    }
    private void getValues() {
        // Convert Values to Double
        Vi_n = Double.parseDouble(Vi.getText().toString());
        Vo_n = Double.parseDouble(Vo.getText().toString());
        Po_n = Double.parseDouble(Po.getText().toString());
        Ripple_IL_n = Double.parseDouble(Ripple_IL.getText().toString());
        Ripple_VC_n = Double.parseDouble(Ripple_VC.getText().toString());
        f_n = Double.parseDouble(f.getText().toString()) * 1000;
        n_n = Double.parseDouble(n.getText().toString());
    }
    private void setBundleVariables(Bundle data){
        data.putDouble("Duty_Cycle", Duty_Cycle_n);
        data.putDouble("Resistance", Resistance_n);
        data.putDouble("Capacitance", Capacitance_n);
        data.putDouble("Inductance", Inductance_n);
        data.putDouble("Inductance_Crit", Inductance_Crit_n);
        data.putDouble("Input_Current", Ii_n);
        data.putDouble("Output_Current", Io_n);
        data.putDouble("Switch_Current", Is_n);
        data.putDouble("Diode_Current", Id_n);
        data.putDouble("Input_Voltage", Vi_n);
        data.putDouble("Output_Voltage", Vo_n);
        data.putDouble("Frequency", f_n);
        data.putDouble("DeltaIL", Delta_IL_n);
        data.putDouble("DeltaVC", Delta_VC_n);
        data.putDouble("ILrms", ILrms_n);
        data.putInt("Flag", flag);
    }
    private void Buck_Calculations() {
        Duty_Cycle_n = (Vo_n / Vi_n) / (n_n / 100);
        Resistance_n = (Math.pow(Vo_n, 2)) / Po_n;
        Io_n = Vo_n / Resistance_n;
        Ii_n = Duty_Cycle_n * Io_n;
        Is_n = Duty_Cycle_n * Io_n;
        Id_n = (1 - Duty_Cycle_n) * Io_n;
        ILmax_n = Io_n + Io_n * (Ripple_IL_n / 200);
        ILmin_n = Io_n - Io_n * (Ripple_IL_n / 200);
        Vomax_n = Vo_n + Vo_n * (Ripple_VC_n / 200);
        Vomin_n = Vo_n - Vo_n * (Ripple_VC_n / 200);
        Delta_IL_n = ILmax_n - ILmin_n;
        Delta_VC_n = Vomax_n - Vomin_n;
        ILrms_n = Io_n * Math.sqrt(1 + ((0.08333333333) * Math.pow(Delta_IL_n / Io_n, 2)));
        Inductance_n = (Vi_n * (1 - Duty_Cycle_n) * Duty_Cycle_n) / (f_n * Delta_IL_n);
        Inductance_Crit_n = (Vi_n * (1 - Duty_Cycle_n) * Duty_Cycle_n) / (2 * f_n * Io_n);
        Capacitance_n = (Vi_n * (1 - Duty_Cycle_n) * Duty_Cycle_n) / (8 * Inductance_n * Delta_VC_n * Math.pow(f_n, 2));
    }
    private void Boost_Calculations() {
        Duty_Cycle_n = ((Vo_n - Vi_n) / Vo_n) / (n_n / 100);
        Resistance_n = (Math.pow(Vo_n, 2)) / Po_n;
        Io_n = Vo_n / Resistance_n;
        Ii_n = Io_n / (1 - Duty_Cycle_n);
        Is_n = Duty_Cycle_n * Ii_n;
        Id_n = (1 - Duty_Cycle_n) * Ii_n;
        ILrms_n = Ii_n;
        ILmax_n = Ii_n + Ii_n * (Ripple_IL_n / 200);
        ILmin_n = Ii_n - Ii_n * (Ripple_IL_n / 200);
        Vomax_n = Vo_n + Vo_n * (Ripple_VC_n / 200);
        Vomin_n = Vo_n - Vo_n * (Ripple_VC_n / 200);
        Delta_IL_n = ILmax_n - ILmin_n;
        Delta_VC_n = Vomax_n - Vomin_n;
        Inductance_n = (Vi_n * Duty_Cycle_n) / (f_n * Delta_IL_n);
        Inductance_Crit_n = (Math.pow(Vi_n, 2) * Duty_Cycle_n) / (2 * Po_n * f_n);
        Capacitance_n = (Io_n * Duty_Cycle_n) / (Delta_VC_n * f_n);
    }
    private void Buck_Boost_Calculations() {
        Duty_Cycle_n = ((Vo_n) / (Vi_n + Vo_n)) / (n_n / 100);
        Resistance_n = (Math.pow(Vo_n, 2)) / Po_n;
        Io_n = Vo_n / Resistance_n;
        Ii_n = Io_n / (1 - Duty_Cycle_n);
        Is_n = Duty_Cycle_n * Ii_n;
        Id_n = (1 - Duty_Cycle_n) * Ii_n;
        ILmax_n = Ii_n + Ii_n * (Ripple_IL_n / 200);
        ILmin_n = Ii_n - Ii_n * (Ripple_IL_n / 200);
        Vomax_n = Vo_n + Vo_n * (Ripple_VC_n / 200);
        Vomin_n = Vo_n - Vo_n * (Ripple_VC_n / 200);
        Delta_IL_n = ILmax_n - ILmin_n;
        Delta_VC_n = Vomax_n - Vomin_n;
        ILrms_n = Math.sqrt(Math.pow(ILmax_n, 2) + Math.pow(Delta_IL_n / 12, 2));
        Inductance_n = (Vi_n * Duty_Cycle_n) / (f_n * Delta_IL_n);
        Inductance_Crit_n = (Math.pow(Vi_n, 2) * Duty_Cycle_n) / (2 * Po_n * f_n);
        Capacitance_n = (Io_n * Duty_Cycle_n) / (Delta_VC_n * f_n);
    }
    private void verifyInputErrorsBuck() {
        if (Vi_n < Vo_n) {
            Toast.makeText(UsualDesign.this, "Error! Output bigger than Input", Toast.LENGTH_SHORT).show();
        }
        if (Vi_n == Vo_n) {
            Toast.makeText(UsualDesign.this, "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
        }
        if ((Duty_Cycle_n > 0.95 || Duty_Cycle_n < 0.05) && Vi_n != Vo_n && Vi_n > Vo_n && n_n <= 100) {
            Toast.makeText(UsualDesign.this, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
        }
        if (Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05 && Vi_n != Vo_n && n_n <= 100 && Resistance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Resistance is negative", Toast.LENGTH_SHORT).show();
        }
        if ((Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05) && Vi_n != Vo_n && n_n <= 100 && Capacitance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Capacitance is negative", Toast.LENGTH_SHORT).show();
        }
        if ((Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05) && Vi_n != Vo_n && n_n <= 100 && Inductance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Inductance is negative", Toast.LENGTH_SHORT).show();
        }
        if (n_n > 100) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Efficiency is bigger then 100%", Toast.LENGTH_SHORT).show();
        }
    }
    private void verifyInputErrorsBoost() {
        if (Vi_n == Vo_n) {
            Toast.makeText(UsualDesign.this, "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
        }
        if (Vi_n > Vo_n) {
            Toast.makeText(UsualDesign.this, "Error! Input bigger than Output", Toast.LENGTH_SHORT).show();
        }
        if ((Duty_Cycle_n > 0.95 || Duty_Cycle_n < 0.05) && Vi_n != Vo_n && Vi_n < Vo_n && n_n <= 100) {
            Toast.makeText(UsualDesign.this, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
        }
        if (Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05 && Vi_n != Vo_n && Resistance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Resistance is negative", Toast.LENGTH_SHORT).show();
        }
        if (Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05 && Vi_n != Vo_n && Capacitance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Capacitance is negative", Toast.LENGTH_SHORT).show();
        }
        if (Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05 && Vi_n != Vo_n && Inductance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Inductance is negative", Toast.LENGTH_SHORT).show();
        }
        if (n_n > 100) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Efficiency is bigger then 100%", Toast.LENGTH_SHORT).show();
        }
    }
    private void verifyInputErrorsBuckBoost() {
        if (Vi_n == Vo_n) {
            Toast.makeText(UsualDesign.this, "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
        }
        if ((Duty_Cycle_n > 0.95 || Duty_Cycle_n < 0.05) && Vi_n != Vo_n && n_n <= 100) {
            Toast.makeText(UsualDesign.this, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
        }
        if (Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05 && Vi_n != Vo_n && Resistance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Resistance is negative", Toast.LENGTH_SHORT).show();
        }
        if ((Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05) && Vi_n != Vo_n && Capacitance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Capacitance is negative", Toast.LENGTH_SHORT).show();
        }
        if (Duty_Cycle_n < 0.95 && Duty_Cycle_n > 0.05 && Vi_n != Vo_n && Inductance_n < 0) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Inductance is negative", Toast.LENGTH_SHORT).show();
        }
        if (n_n > 100) {
            Toast.makeText(getApplicationContext(), "This project is not possible. Efficiency is bigger then 100%", Toast.LENGTH_SHORT).show();
        }
    }
}