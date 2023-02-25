package com.example.dcdcconvertersdesign.convertersutilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.R;

import org.w3c.dom.Text;

import java.util.Locale;

public class ConvertersUtils {
    private static String TAG = "ConverterUtils";
    public static double Inductance_n, Inductance_Crit_n, Ii_n, Io_n, Is_n, Id_n, Vo_n,
            Vi_n, f_n, Delta_IL_n, Delta_VC_n, ILrms_n,
            Duty_Cycle_n, Resistance_n, Capacitance_n;
    public static int flag, flag_Reverse = 0;
    public TextView Duty_Cycle, Resistance, Capacitance, Inductance;
    public TextView Inductance_T, Capacitance_T, ModeWarning, converterTitle;
    public ImageView converterFigure;
    public Button Simulation, Advanced;
    public void createObjects(Activity activity)
    {
        // Values
        Duty_Cycle = (TextView) activity.findViewById(R.id.Duty_Cycle);
        Resistance = (TextView) activity.findViewById(R.id.Resistance);
        Capacitance = (TextView) activity.findViewById(R.id.Capacitance);
        Inductance = (TextView) activity.findViewById(R.id.Inductance);

        // Texts
        Inductance_T = activity.findViewById(R.id.Inductance_T);
        Capacitance_T = activity.findViewById(R.id.Capacitance_T);
        ModeWarning = (TextView) activity.findViewById(R.id.ModeWarning);
        converterTitle = (TextView) activity.findViewById(R.id.Results);

        // Image
        converterFigure = (ImageView) activity.findViewById(R.id.imageView);

        // Buttons
        Simulation = (Button) activity.findViewById(R.id.Simulation);
        Advanced = (Button) activity.findViewById(R.id.Advanced);
    }

    public void setConverterInfo() {
//        Log.d(TAG, "Are you here???? flag: " + flag);
        if(flag == 1) {
            converterTitle.setText(R.string.buckText);
            converterFigure.setImageResource(R.drawable.buck);
        }
        if(flag == 2) {
            converterTitle.setText(R.string.boostText);
            converterFigure.setImageResource(R.drawable.boost);
        }
        if(flag == 3) {
            converterTitle.setText(R.string.buckBoostText);
            converterFigure.setImageResource(R.drawable.buck_boost);
        }
    }
    public void formatValues() {
        // Writing Values
        Duty_Cycle.setText(stringFormat(Duty_Cycle_n*100));
        Resistance.setText(stringFormat(Resistance_n));

        // Capacitance
        if(Capacitance_n > 1){
            Capacitance.setText(stringFormat(Capacitance_n));
            Capacitance_T.setText(R.string.capacitance_F);
        }
        if(Capacitance_n >= 1e-3 && Capacitance_n < 1) {
            Capacitance.setText(stringFormat(Capacitance_n*1e3));
            Capacitance_T.setText(R.string.capacitance_mF);
        }
        if(Capacitance_n < 1e-3 && Capacitance_n >= 1e-6) {
            Capacitance.setText(stringFormat(Capacitance_n*1e6));
            Capacitance_T.setText(R.string.capacitance_uF);
        }
        if(Capacitance_n < 1e-6){
            Capacitance.setText(stringFormat(Capacitance_n*1e9));
            Capacitance_T.setText(R.string.capacitance_nF);
        }

        //Inductance
        if(Inductance_n > 1){
            Inductance.setText(stringFormat(Inductance_n));
            Inductance_T.setText(R.string.inductance_H);
        }
        if(Inductance_n >= 1e-3 && Inductance_n < 1) {
            Inductance.setText(stringFormat(Inductance_n * 1e3));
            Inductance_T.setText(R.string.inductance_mH);
        }
        if(Inductance_n < 1e-3 && Inductance_n >= 1e-6) {
            Inductance.setText(stringFormat(Inductance_n * 1e6));
            Inductance_T.setText(R.string.inductance_uH);
        }
        if(Inductance_n < 1e-6){
            Inductance.setText(stringFormat(Inductance_n*1e9));
            Inductance_T.setText(R.string.inductance_nH);
        }
    }

    private static String stringFormat(double input) {
        return String.format(Locale.US, "%.2f", input);
    }

    public static void recoverMainActivityData(Bundle data) {
        Duty_Cycle_n = data.getDouble("Duty_Cycle");
        Resistance_n = data.getDouble("Resistance");
        Capacitance_n = data.getDouble("Capacitance");
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
        flag = data.getInt("Flag");
    }
    public static void sendAdvancedData(Activity activity, Intent intentAdvanced) {
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
        data2.putInt("Flag", flag);
        data2.putInt("Flag_Reverse", flag_Reverse);
        intentAdvanced.putExtras(data2);
        activity.startActivity( intentAdvanced );
    }
    public static void sendSimulationData(Activity activity, Intent intentSimulationDefinitions) {
        Bundle simulationData = new Bundle();
        simulationData.putDouble("Output_Voltage", Vo_n);
        simulationData.putDouble("Input_Voltage", Vi_n);
        simulationData.putDouble("Duty_Cycle", Duty_Cycle_n);
        simulationData.putDouble("Inductance", Inductance_n);
        simulationData.putDouble("Capacitance", Capacitance_n);
        simulationData.putDouble("Resistance", Resistance_n);
        simulationData.putDouble("Frequency", f_n);
        simulationData.putInt("Flag", flag);
        intentSimulationDefinitions.putExtras(simulationData);
        activity.startActivity(intentSimulationDefinitions);
    }
}
