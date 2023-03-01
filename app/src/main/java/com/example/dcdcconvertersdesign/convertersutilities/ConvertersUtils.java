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

import org.w3c.dom.Text;

import java.util.Locale;

public class ConvertersUtils {
    private static String TAG = "ConverterUtils";
    public static double inductance, inductanceCritical, inputCurrent, outputCurrent,
            inductorCurrent, switchCurrent, diodeCurrent, outputVoltage, inputVoltage, frequency,
            deltaInductorCurrent, rippleCapacitorVoltage, inductorCurrentRMS, dutyCycle,
            dutyCycleIdeal, resistance, capacitance;
    public static int flag, flagReverse = 0;
    public static boolean isCCM;
    public TextView dutyCycleTextView, resistanceTextView, capacitanceTextView, inductanceTextView;
    public TextView dutyCycleText, resistanceText, inductanceText, capacitanceText, modeWarning,
            converterTitle, modeTextView;
    public ImageView converterFigure;
    public Button Simulation, Advanced;
    public void createObjects(Activity activity)
    {
        // Values
        dutyCycleTextView = (TextView) activity.findViewById(R.id.duty_cycle);
        resistanceTextView = (TextView) activity.findViewById(R.id.resistance);
        capacitanceTextView = (TextView) activity.findViewById(R.id.capacitance);
        inductanceTextView = (TextView) activity.findViewById(R.id.inductance);

        // Texts
        dutyCycleText = activity.findViewById(R.id.duty_cycle_text);
        resistanceText = activity.findViewById(R.id.resistance_text);
        inductanceText = activity.findViewById(R.id.inductance_text);
        capacitanceText = activity.findViewById(R.id.capacitance_text);
        modeWarning = (TextView) activity.findViewById(R.id.mode_warning);
        converterTitle = (TextView) activity.findViewById(R.id.converter_title);
        modeTextView = (TextView) activity.findViewById(R.id.mode);

        // Image
        converterFigure = (ImageView) activity.findViewById(R.id.converter_image);

        // Buttons
        Simulation = (Button) activity.findViewById(R.id.simulation_btn);
        Advanced = (Button) activity.findViewById(R.id.advanced_btn);
    }

    public void setConverterInfo() {
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
    public void formatAndSetValues() {
        // Writing Values
        String formattedUnit;

        formattedUnit = stringFormat(dutyCycle*100) + " %";
        dutyCycleTextView.setText(formattedUnit);

        dutyCycleText.setText(R.string.duty_cycle_text);
        resistanceText.setText(R.string.resistance);
        capacitanceText.setText(R.string.capacitance);
        inductanceText.setText(R.string.inductance);

        modeTextView.setText(isCCM ? "Continuous Conduction Mode\n (CCM)" :
                "Discontinuous Conduction Mode\n (DCM)");

        // Resistance
        if (resistance > 1e6) {
            formattedUnit = stringFormat(resistance / 1e6) + " MΩ";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance > 1e3) {
            formattedUnit = stringFormat(resistance / 1e3) + " kΩ";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance >= 1) {
            formattedUnit = stringFormat(resistance) + " Ω";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance >= 1e-3) {
            formattedUnit = stringFormat(resistance / 1e-3) + " mΩ";
            resistanceTextView.setText(formattedUnit);
        } else if (resistance >= 1e-6) {
            formattedUnit = stringFormat(resistance / 1e-6) + " μΩ";
            resistanceTextView.setText(formattedUnit);
        } else {
            formattedUnit = stringFormat(resistance / 1e-9) + " nΩ";
            resistanceTextView.setText(formattedUnit);
        }

        // Capacitance
        if(capacitance > 1){
            formattedUnit = stringFormat(capacitance) + " F";
            capacitanceTextView.setText(formattedUnit);
        }
        if(capacitance >= 1e-3 && capacitance < 1) {
            formattedUnit = stringFormat(capacitance * 1e3) + " mF";
            capacitanceTextView.setText(formattedUnit);
        }
        if(capacitance < 1e-3 && capacitance >= 1e-6) {
            formattedUnit = stringFormat(capacitance * 1e6) + " μF";
            capacitanceTextView.setText(formattedUnit);
        }
        if(capacitance < 1e-6){
            formattedUnit = stringFormat(capacitance * 1e9) + " nF";
            capacitanceTextView.setText(formattedUnit);
        }

        //Inductance
        if(inductance > 1){
            formattedUnit = stringFormat(inductance) + " H";
            inductanceTextView.setText(formattedUnit);
        }
        if(inductance >= 1e-3 && inductance < 1) {
            formattedUnit = stringFormat(inductance * 1e3) + " mF";
            inductanceTextView.setText(formattedUnit);
        }
        if(inductance < 1e-3 && inductance >= 1e-6) {
            formattedUnit = stringFormat(inductance * 1e6) + " μF";
            inductanceTextView.setText(formattedUnit);
        }
        if(inductance < 1e-6){
            formattedUnit = stringFormat(inductance * 1e9) + " nF";
            inductanceTextView.setText(formattedUnit);
        }
    }

    public static void recoverMainActivityData(Bundle data) {
        dutyCycle = data.getDouble("Duty_Cycle");
        dutyCycleIdeal = data.getDouble("Duty_Cycle_Ideal");
        resistance = data.getDouble("Resistance");
        capacitance = data.getDouble("Capacitance");
        inductance = data.getDouble("Inductance");
        inductanceCritical = data.getDouble("Inductance_Crit");
        inputCurrent = data.getDouble("Input_Current");
        outputCurrent = data.getDouble("Output_Current");
        inductorCurrent = data.getDouble("Inductor_Current");
        switchCurrent = data.getDouble("Switch_Current");
        diodeCurrent = data.getDouble("Diode_Current");
        inputVoltage = data.getDouble("Input_Voltage");
        outputVoltage = data.getDouble("Output_Voltage");
        frequency = data.getDouble("Frequency");
        deltaInductorCurrent = data.getDouble("DeltaIL");
        rippleCapacitorVoltage = data.getDouble("DeltaVC");
        inductorCurrentRMS = data.getDouble("ILrms");
        isCCM = data.getBoolean("is_ccm");
        flag = data.getInt("Flag");
    }
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
