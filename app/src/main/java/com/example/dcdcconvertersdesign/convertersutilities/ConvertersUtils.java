package com.example.dcdcconvertersdesign.convertersutilities;

import static com.example.dcdcconvertersdesign.helpers.Helpers.stringFormat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dcdcconvertersdesign.R;

public class ConvertersUtils {
    private static String TAG = "ConverterUtils";
    public static double inductance, inductanceCritical, inputCurrent, outputCurrent,
            inductorCurrent, switchCurrent, diodeCurrent, outputVoltage, inputVoltage, frequency,
            deltaInductorCurrent, rippleCapacitorVoltage, inductorCurrentRMS, dutyCycle,
            dutyCycleIdeal, resistance, capacitance;
    public static int flag, flagReverse = 0;

    public Button simulationBtn, advancedBtn;

    public static void sendAdvancedData(Activity activity, Intent intentAdvanced) {
        Bundle data2 = new Bundle();
        data2.putDouble("Inductance", inductance);
        data2.putDouble("Inductance_Crit", inductanceCritical);
        data2.putDouble("Input_Current", inputCurrent);
        data2.putDouble("Output_Current", outputCurrent);
        data2.putDouble("Inductor_Current", inductorCurrent);
        data2.putDouble("Switch_Current", switchCurrent);
        data2.putDouble("Diode_Current", diodeCurrent);
        data2.putDouble("Output_Voltage", outputVoltage);
        data2.putDouble("Frequency", frequency);
        data2.putDouble("DeltaIL", deltaInductorCurrent);
        data2.putDouble("DeltaVC", rippleCapacitorVoltage);
        data2.putDouble("ILrms", inductorCurrentRMS);
        data2.putInt("Flag", flag);
        data2.putInt("Flag_Reverse", flagReverse);
        intentAdvanced.putExtras(data2);
        activity.startActivity( intentAdvanced );
        Log.d(TAG, "ConvertersUtil: " + flag);
    }
    public static void sendSimulationData(Activity activity, Intent intentSimulationDefinitions) {
        Bundle simulationData = new Bundle();
        simulationData.putDouble("Output_Voltage", outputVoltage);
        simulationData.putDouble("Input_Voltage", inputVoltage);
        simulationData.putDouble("Duty_Cycle", dutyCycle);
        simulationData.putDouble("Duty_Cycle_Ideal", dutyCycleIdeal);
        simulationData.putDouble("Inductance", inductance);
        simulationData.putDouble("Capacitance", capacitance);
        simulationData.putDouble("Resistance", resistance);
        simulationData.putDouble("Frequency", frequency);
        simulationData.putInt("Flag", flag);
        intentSimulationDefinitions.putExtras(simulationData);
        activity.startActivity(intentSimulationDefinitions);
    }
}
