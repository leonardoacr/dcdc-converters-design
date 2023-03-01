package com.example.dcdcconvertersdesign;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.InputStream;

public class InductorProject extends AppCompatActivity {

    InputStream inputStream;
    ToggleButton Results;
    Button Table;
    TextView Core_Model_T, Air_Gap_T, size_percent_T, Turn_Number_T, AWG_T, Condutores_em_paralelo_T;
    TextView Core_Model, Air_Gap, size_percent, Turn_Number, AWG, Condutores_em_paralelo;
    EditText Jmax, Bmax, Ku;
    String Modelo_Nucleo;
    String csvLine;
    double Jmax_n, Bmax_n, Ku_n;
    double Inductance_n, ILrms_n, f_n, Delta_IL_n;
    double Ap_calculado, Ap, Ae, Aw, flag_geral, erro, Diametro_Maximo, Diametro_do_Cobre;
    double Entreferro, Porcentagem_de_area, Porcentagem_de_area_util, u0, ur, p, pi = 3.1415;
    double Sf, St, Area_condutor_isolado, Numero_de_espiras, AWG_n, Condutores_em_paralelo_n;
    int  flag, i, i_N, Nc, Nl, Cl, Cc;

        // Tables
        String[] Modelos =
                {"EE-20/15",
                "EE-30/07",
                "EE-30/14",
                "EE-42/15",
                "EE-42/20",
                "EE-55/21",
                "EE-65/13",
                "EE-65/26",
                "EE-65/39"};

        double[][] Nucleos =
                {{0.312, 0.26, 0.08112},
                {0.6, 0.8, 0.48},
                {1.2, 0.85, 1.02},
                {1.81, 1.57, 2.8417},
                {2.4, 1.57, 3.768},
                {3.54, 2.5, 8.85},
                {2.66, 3.7, 9.842},
                {5.32, 3.7, 19.684},
                {7.98, 3.7, 29.526}};


        double[][] Condutores =
                        {{10, 0.259, 0.273, 23.679, 0.052620, 0.058572},
                        {11, 0.231, 0.244, 18.778, 0.041729, 0.046738},
                        {12, 0.205, 0.218, 14.892, 0.033092, 0.037309},
                        {13, 0.183, 0.195, 11.809, 0.026243, 0.029793},
                        {14, 0.163, 0.174, 9.365, 0.020811, 0.0238},
                        {15, 0.145, 0.156, 7.427, 0.016504, 0.019021},
                        {16, 0.129, 0.139, 5.89, 0.013088, 0.015207},
                        {17, 0.115, 0.124, 4.671, 0.010379, 0.012164},
                        {18, 0.102, 0.111, 3.704, 0.008231, 0.009735},
                        {19, 0.091, 0.1, 2.937, 0.006527, 0.007794},
                        {20, 0.081, 0.089, 2.329, 0.005176, 0.006244},
                        {21, 0.072, 0.08, 1.847, 0.004105, 0.005004},
                        {22, 0.064, 0.071, 1.465, 0.003255, 0.004013},
                        {23, 0.057, 0.064, 1.162, 0.002582, 0.003221},
                        {24, 0.051, 0.057, 0.921, 0.002047, 0.002586},
                        {25, 0.045, 0.051, 0.731, 0.001624, 0.0020778},
                        {26, 0.04, 0.046, 0.579, 0.001287, 0.001671},
                        {27, 0.036, 0.041, 0.459, 0.001021, 0.001344},
                        {28, 0.032, 0.037, 0.364, 0.00081, 0.001083},
                        {29, 0.029, 0.033, 0.289, 0.000642, 0.000872},
                        {30, 0.025, 0.03, 0.229, 0.000509, 0.000704},
                        {31, 0.023, 0.027, 0.182, 0.000404, 0.000568},
                        {32, 0.02, 0.024, 0.144, 0.00032, 0.000459},
                        {33, 0.018, 0.022, 0.114, 0.000254, 0.000371},
                        {34, 0.016, 0.02, 0.091, 0.000201, 0.0003},
                        {35,0.014, 0.018, 0.072, 0.00016, 0.000243},
                        {36, 0.013, 0.016, 0.057, 0.000127, 0.000197},
                        {37, 0.011, 0.014, 0.045, 0.0001, 0.00016},
                        {38, 0.01, 0.013, 0.036, 0.00008, 0.00013},
                        {39, 0.009, 0.012, 0.028, 0.000063, 0.000106},
                        {40, 0.008, 0.01, 0.023, 0.00005, 0.000086},
                        {41, 0.007, 0.009, 0.018, 0.00004, 0.00007}};

    public void createObjects() {

        // Text
        Core_Model_T = (TextView) findViewById(R.id.Core_Model_T);
        Air_Gap_T = (TextView) findViewById(R.id.Air_Gap_T);
        size_percent_T = (TextView) findViewById(R.id.size_percent_T);
        Turn_Number_T = (TextView) findViewById(R.id.Turn_Number_T);
        AWG_T = (TextView) findViewById(R.id.AWG_T);
        Condutores_em_paralelo_T = (TextView) findViewById(R.id.Condutores_em_paralelo_T);

        // Text Values
        Core_Model = (TextView) findViewById(R.id.Core_Model);
        Air_Gap = (TextView) findViewById(R.id.Air_Gap);
        size_percent = (TextView) findViewById(R.id.size_percent);
        Turn_Number = (TextView) findViewById(R.id.Turn_Number);
        AWG = (TextView) findViewById(R.id.AWG);
        Condutores_em_paralelo = (TextView) findViewById(R.id.Condutores_em_paralelo);

        // Values
        Jmax = (EditText) findViewById(R.id.Jmax);
        Bmax = (EditText) findViewById(R.id.Bmax);
        Ku = (EditText) findViewById(R.id.Ku);

        // Button
        Table = (Button) findViewById(R.id.Table);

        // Toggle Button
        Results = (ToggleButton) findViewById(R.id.Results);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inductor_project);
        createObjects();

        Results.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if(b){
                    if (isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Error! Something is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Values();
                    InductorProjectCalculations();

                    // Writing Text
                    Core_Model_T.setText("Core Model");
                    Air_Gap_T.setText("Air Gap (mm)");
                    size_percent_T.setText("Size Percent (%)");
                    Turn_Number_T.setText("Number of Turns");
                    AWG_T.setText("AWG");
                    Condutores_em_paralelo_T.setText("Parallel Conductors");

                    // Writing Values
                    Core_Model.setText(Modelo_Nucleo);
                    Air_Gap.setText(String.format("%.4f", Entreferro*1000));
                    size_percent.setText(String.format("%.2f", Porcentagem_de_area));
                    Turn_Number.setText(String.format("%.0f", Numero_de_espiras));
                    AWG.setText(String.format("%.0f", AWG_n));
                    Condutores_em_paralelo.setText(String.format("%.0f", Condutores_em_paralelo_n));

                    Table.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(InductorProject.this, Table.class);

                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }

    public boolean isEmpty() {
        return Jmax.getText().toString().isEmpty() ||
                Bmax.getText().toString().isEmpty() ||
                Ku.getText().toString().isEmpty();
    }

    public void Values() {
        // Convert Values to Double
        Jmax_n = Double.parseDouble(Jmax.getText().toString());
        Bmax_n = Double.parseDouble(Bmax.getText().toString());
        Ku_n = Double.parseDouble(Ku.getText().toString()) / 100;
    }

    public void InductorProjectCalculations() {
        // Recovering necessary data
        Bundle data4 = getIntent().getExtras();
        Inductance_n = data4.getDouble("Inductance");
        ILrms_n = data4.getDouble("ILrms");
        Delta_IL_n = data4.getDouble("DeltaIL");
        f_n = data4.getDouble("Frequency");

        // Cálculo do produto das áreas
        Ap_calculado = Inductance_n * Delta_IL_n * ILrms_n * 1e4 / (Ku_n * Bmax_n * Jmax_n);
        Nc = 2;
        Nl = 8;
        flag_geral = 0;
        erro = 0;

        while (flag_geral == 0) {

            flag = 0;

            if (Ap_calculado <= 29.526 && Ap_calculado >= 0.08112) {
                for (i = Nl; i >= 0; i--){
                    if (i > 0 && flag == 0) {
                        if (Nucleos[i - 1][Nc] <= Ap_calculado) {
                            Ap = Nucleos[i][Nc];
                            Modelo_Nucleo = Modelos[i];
                            Ae = Nucleos[i][0];
                            i_N = i;
                            flag = 1;
                        }
                    }
                }
            }

            if (Ap_calculado > 29.526) {
                i_N = Nl;
                Ap = Nucleos[Nl][Nc];
                Modelo_Nucleo = Modelos[Nl];
                Ae = Nucleos[Nl][0];
            }

            if (Ap_calculado < 0.08112) {
                i_N = 0;
                Ap = Nucleos[0][Nc];
                Modelo_Nucleo = Modelos[0];
                Ae = Nucleos[0][0];
            }

            Aw = Ap / Ae;
            // Cálculo do Condutor a ser utilizado
            Numero_de_espiras = Inductance_n * (ILrms_n + Delta_IL_n) * 1e4 / (Bmax_n * Ae);
            St = ILrms_n / Jmax_n;
            p = 1.72e-4;
            u0 = 4 * pi * 1e-7;
            ur = 1;
            Diametro_Maximo = 2 * Math.sqrt(p / (pi * u0 * ur * f_n));

            // Buscando na tabela
            Cl = 31;
            Cc = 5;
            flag = 0;

            if (Diametro_Maximo >= 0.007 && Diametro_Maximo <= 0.259) {
                for (i = 0; i <= Cl; i++){
                    if (flag == 0) {
                        if (Condutores[i + 1][1] < Diametro_Maximo) {
                            Diametro_do_Cobre = Condutores[i + 1][1];
                            AWG_n = Condutores[i + 1][0];
                            Sf = Condutores[i + 1][4];
                            Area_condutor_isolado = Condutores[i + 1][5];
                            flag = 1;
                        }
                    }
                }
            }

            if (Diametro_Maximo < 0.007) {
                Diametro_do_Cobre = Condutores[Cl][1];
                AWG_n = Condutores[Cl][0];
                Sf = Condutores[Cl][4];
                Area_condutor_isolado = Condutores[Cl][5];
            }

            if (Diametro_Maximo > 0.259) {
                Diametro_do_Cobre = Condutores[0][1];
                AWG_n = Condutores[0][0];
                Sf = Condutores[0][4];
                Area_condutor_isolado = Condutores[0][5];
            }
            Condutores_em_paralelo_n = St / Sf;

            // Pega o próximo número inteiro maior que o calculado
            Numero_de_espiras = Math.ceil(Numero_de_espiras);
            Condutores_em_paralelo_n = Math.ceil(Condutores_em_paralelo_n);

            Porcentagem_de_area = Condutores_em_paralelo_n * Numero_de_espiras * Area_condutor_isolado * 100 / Aw;
            Porcentagem_de_area_util = 100;

            if (Porcentagem_de_area <= Porcentagem_de_area_util) {
                flag_geral = 1;
            }

            if (Porcentagem_de_area > Porcentagem_de_area_util) {
                if (1 + i_N > Nl) {
                    Toast.makeText(getApplicationContext(), "Error! Doesn't exist core registered for this operation frequency", Toast.LENGTH_SHORT).show();
                    erro = 1;
                    return;
                } else {
                    Ap_calculado = Nucleos[1 + i_N][Nc] - 1e-3;
                }
            }
        }
            Entreferro = Math.pow(Numero_de_espiras, 2) * u0 * ur * Ae * 1e-4 / Inductance_n;

    }
}