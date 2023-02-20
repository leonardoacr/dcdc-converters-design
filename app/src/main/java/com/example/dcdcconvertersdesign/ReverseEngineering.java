package com.example.dcdcconvertersdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ReverseEngineering extends AppCompatActivity {

    int Flag = 0;
    double Vi_n, Vo_n, Po_n, Ripple_IL_n, Ripple_VC_n, f_n, n_n, Is_n, Id_n, ILrms_n;
    double Ii_n, Io_n, ILmax_n, ILmin_n, Vomax_n, Vomin_n, Delta_IL_n, Delta_VC_n;
    double R_n, L_n, C_n, Duty_Cycle_n, Inductance_Crit_n;
    EditText L, Re, C, Vi, Vo, f;
    Button Buck, Boost, Buck_Boost;

    public void createObjects() {
        // Input Values
        Vi = findViewById(R.id.Vi);
        Vo = findViewById(R.id.Vo);
        f = findViewById(R.id.f);
        L = findViewById(R.id.L);
        Re = findViewById(R.id.Re);
        C = findViewById(R.id.C);

        // Buttons
        Buck = findViewById(R.id.Buck);
        Boost = findViewById(R.id.Boost);
        Buck_Boost = findViewById(R.id.Buck_Boost);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse_engineering);
        createObjects();

        // Teste de botões
        Buck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error! Something is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputValues();
                Buck_Calculations();

                if (Vi_n > Vo_n && Duty_Cycle_n <= 0.95 && Duty_Cycle_n >= 0.05) {
                    Write_Results();
                }
                if (Vi_n < Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Output bigger than Input", Toast.LENGTH_SHORT).show();
                }
                if (Vi_n == Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
                }
                if((Duty_Cycle_n > 0.95 || Duty_Cycle_n < 0.05) && Vi_n != Vo_n && Vi_n > Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Boost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error! Something is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputValues();
                Boost_Calculations();

                if(Vi_n < Vo_n && Duty_Cycle_n <= 0.95 && Duty_Cycle_n >= 0.05) {
                    Write_Results();
                }
                if (Vi_n == Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
                }
                if(Vi_n > Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Input bigger than Output", Toast.LENGTH_SHORT ).show();
                }
                if((Duty_Cycle_n > 0.95 || Duty_Cycle_n < 0.05) && Vi_n != Vo_n && Vi_n < Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Buck_Boost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Error! Something is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                InputValues();
                BuckBoost_Calculations();

                if (Vi_n != Vo_n && Duty_Cycle_n <= 0.95 && Duty_Cycle_n >= 0.05) {
                    Write_Results();
                }
                if (Vi_n == Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
                }
                if((Duty_Cycle_n > 0.95 || Duty_Cycle_n < 0.05) && Vi_n != Vo_n) {
                    Toast.makeText(getApplicationContext(), "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public boolean isEmpty(){
        return Vi.getText().toString().isEmpty() ||
                Vo.getText().toString().isEmpty() ||
                L.getText().toString().isEmpty() ||
                Re.getText().toString().isEmpty() ||
                C.getText().toString().isEmpty() ||
                f.getText().toString().isEmpty();
    }

    public void InputValues() {
        Vi_n = Double.parseDouble(Vi.getText().toString());
        Vo_n = Double.parseDouble(Vo.getText().toString());
        R_n = Double.parseDouble(Re.getText().toString());
        L_n = Double.parseDouble(L.getText().toString()) / 1e3;
        C_n = Double.parseDouble(C.getText().toString()) / 1e6;
        f_n = Double.parseDouble(f.getText().toString()) * 1e3;
    }

    public void Buck_Calculations() {
        Duty_Cycle_n = Vo_n / Vi_n;
        Po_n = Math.pow(Vo_n, 2) / R_n;
        Io_n = Vo_n / R_n;
        Ii_n = Duty_Cycle_n * Io_n;
        Is_n = Duty_Cycle_n * Io_n;
        Id_n = (1 - Duty_Cycle_n) * Io_n;
        Delta_IL_n = (Vi_n * (1 - Duty_Cycle_n) * Duty_Cycle_n) / (f_n * L_n);
        Delta_VC_n = (Vi_n * (1 - Duty_Cycle_n) * Duty_Cycle_n) / (8 * L_n * C_n * Math.pow(f_n, 2));
        Ripple_IL_n = 100 * Delta_IL_n / Io_n;
        Ripple_VC_n = 100 * Delta_IL_n / Vo_n;
        Inductance_Crit_n = (Vi_n * (1 - Duty_Cycle_n) * Duty_Cycle_n) / (2 * f_n * Io_n);
        Flag = 1;
    }

    public void Boost_Calculations() {
        Duty_Cycle_n = (Vo_n - Vi_n) / Vo_n;
        Po_n = (Math.pow(Vo_n, 2)) / R_n;
        Io_n = Vo_n / R_n;
        Ii_n = Io_n / (1 - Duty_Cycle_n);
        Is_n = Duty_Cycle_n * Ii_n;
        Id_n = (1 - Duty_Cycle_n) * Ii_n;
        ILrms_n = Ii_n;
        Delta_IL_n = (Vi_n * Duty_Cycle_n) / (f_n * L_n);
        Delta_VC_n = (Io_n * Duty_Cycle_n) / (C_n * f_n);
        Ripple_IL_n = 100 * Delta_IL_n / Ii_n;
        Ripple_VC_n = 100 * Delta_VC_n / Vo_n;
        Inductance_Crit_n = (Math.pow(Vi_n, 2) * Duty_Cycle_n) / (2 * Po_n * f_n);
        Flag = 2;
    }

    public void BuckBoost_Calculations() {
        Duty_Cycle_n = (Vo_n) / (Vi_n + Vo_n);
        Po_n = (Math.pow(Vo_n, 2)) / R_n;
        Io_n = Vo_n / R_n;
        Ii_n = Io_n / (1 - Duty_Cycle_n);
        Is_n = Duty_Cycle_n * Ii_n;
        Id_n = (1 - Duty_Cycle_n) * Ii_n;
        Delta_IL_n = (Vi_n * Duty_Cycle_n) / (f_n * L_n);
        Delta_VC_n = (Io_n * Duty_Cycle_n) / (C_n * f_n);
        Ripple_IL_n = 100 * Delta_IL_n / Ii_n;
        Ripple_VC_n = 100 * Delta_VC_n / Vo_n;
        Inductance_Crit_n = (Math.pow(Vi_n, 2) * Duty_Cycle_n) / (2 * Po_n * f_n);
        Flag = 3;
    }

    public void Write_Results() {
        Intent intent = new Intent(getApplicationContext(), Converters_R.class);
        Bundle data = new Bundle();

        // Sending Data to Write Values
        data.putDouble("Duty_Cycle", Duty_Cycle_n);
        data.putDouble("Inductance_Crit", Inductance_Crit_n);
        data.putDouble("Ripplevc", Ripple_VC_n);
        data.putDouble("Rippleil", Ripple_IL_n);
        data.putDouble("Po", Po_n);
        data.putDouble("Inductance", L_n);
        data.putInt("Flag", Flag);

        // Sending data to Advanced
        data.putDouble("Input_Current", Ii_n);
        data.putDouble("Output_Current", Io_n);
        data.putDouble("Switch_Current", Is_n);
        data.putDouble("Diode_Current", Id_n);
        data.putDouble("Output_Voltage", Vo_n);
        data.putDouble("Frequency", f_n);
        data.putDouble("DeltaIL", Delta_IL_n);
        data.putDouble("DeltaVC", Delta_VC_n);
        data.putDouble("ILrms", ILrms_n);

        intent.putExtras(data);

        startActivity(intent);
    }
}

// Here, the user has this values:
// L, R, C, Vi, Vo, f
// And, need:
// Po, Ripple_IL, Ripple_VC


