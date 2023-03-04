package com.example.dcdcconvertersdesign.convertersutilities;

import static com.example.dcdcconvertersdesign.ReverseDesign.*;

import android.util.Log;

public class CalculateConverterVariablesReverse {
    private static String TAG = "CalculateReverse";
    public static void buckCalculations() {
        // Pre-global-variables
        outputPower = Math.pow(outputVoltage, 2) / resistance;
        outputCurrent = outputVoltage / resistance;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = outputCurrent;
        switchCurrent = inputCurrent;
        diodeCurrent =  outputCurrent - inputCurrent;

        isCCM = checkBuckConductionMode();

        if (isCCM) {
            // CCM mode
            dutyCycleIdeal = outputVoltage / inputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / outputCurrent;
            inductorCurrentRMS = outputCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / outputCurrent, 2)));
            deltaCapacitorVoltage = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (16 * inductance * capacitance * Math.pow(frequency, 2));
            rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
        } else {
            // DCM mode
            dutyCycleIdeal = 2 * outputPower / (deltaInductorCurrent * inputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / outputCurrent;
            deltaCapacitorVoltage = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (16 * inductance * capacitance * Math.pow(frequency, 2));
            rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
            double dcm2 = inductorCurrentMax * frequency * inductance / outputVoltage;
            double dcm1 = 1 - dutyCycle - dcm2;
            inductorCurrentRMS = deltaInductorCurrent*Math.sqrt((dutyCycle + dcm1)/3);
        }
    }

    private static boolean checkBuckConductionMode() {
        dutyCycleIdeal = outputVoltage / inputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductanceCritical = dutyCycleIdeal * (inputVoltage - outputVoltage) * resistance / (2 * frequency * outputVoltage);
        return inductanceCritical <= inductance;
    }

    public static void boostCalculations() {
        // Pre-global-variables
        outputPower = (Math.pow(outputVoltage, 2)) / resistance;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = inputCurrent;

        Log.d(TAG, "Check variables before MODE: " +
                "Resistance = " + resistance +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Inductor Current = " + inductorCurrent +
                ", Max Output Voltage = " + outputVoltageMax +
                ", Min Output Voltage = " + outputVoltageMin +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage);

        isCCM = checkBoostConductionMode();
        Log.d(TAG, isCCM + " " +
                inductanceCritical + " " + inductance + " " + dutyCycle);

        if(isCCM) {
            dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
            rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
            inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        } else {
            // DCM mode
            dutyCycleIdeal = (1 / inputVoltage) * Math.sqrt((2 * inductance * outputVoltage *
                    frequency * (outputVoltage - inputVoltage)) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = Math.sqrt((2 * outputVoltage * (outputVoltage - inputVoltage)) /
                    (resistance * frequency * inductance));
            rippleInductorCurrent = 100 * deltaInductorCurrent / inputCurrent;
            inductorCurrentMax = deltaInductorCurrent;
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            inductorCurrentRMS = inputCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }

        deltaCapacitorVoltage = 2 * (outputCurrent * dutyCycle) / (capacitance * frequency);
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;
        outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);

        Log.d(TAG, "Check variables after the MODE: " +
                "Duty Cycle Ideal = " + dutyCycleIdeal +
                ", Duty Cycle = " + dutyCycle +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Switch Current = " + switchCurrent +
                ", Diode Current = " + diodeCurrent +
                ", Max Inductor Current = " + inductorCurrentMax +
                ", Min Inductor Current = " + inductorCurrentMin +
                ", Delta Inductor Current = " + deltaInductorCurrent +
                ", Max Output Voltage = " + outputVoltageMax +
                ", Min Output Voltage = " + outputVoltageMin +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage +
                ", Inductance = " + inductance +
                ", Inductor Current RMS = " + inductorCurrentRMS +
                ", Resistance = " + resistance +
                ", Inductance = " + inductance +
                ", Capacitance = " + capacitance);
    }

    private static boolean checkBoostConductionMode() {
        dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }

    public static void buckBoostCalculations() {
        // Pre-global-variables
        outputPower = (Math.pow(outputVoltage, 2)) / resistance;
        outputCurrent = outputVoltage / resistance;
        inputCurrent = outputPower / inputVoltage;
        switchCurrent = inputCurrent;
        diodeCurrent = outputCurrent;
        inductorCurrent = outputCurrent + inputCurrent;

        Log.d(TAG, "Check variables before MODE: " +
                "Output Power = " + outputPower +
                ", Output Voltage = " + outputVoltage +
                ", Output Current = " + outputCurrent +
                ", Input Voltage = " + inputVoltage +
                ", Input Current = " + inputCurrent +
                ", Switch Current = " + switchCurrent +
                ", Diode Current = " + diodeCurrent +
                ", Inductor Current = " + inductorCurrent);


        isCCM = checkBuckBoostConductionMode();
        Log.d(TAG, isCCM + " " +
                inductanceCritical + " " + inductance + " " + dutyCycle);

        if(isCCM) {
            // CCM Mode
            dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            deltaInductorCurrent = (inputVoltage * dutyCycle) / (frequency * inductance);
            rippleInductorCurrent = (100 * deltaInductorCurrent / inductorCurrent);
            inductorCurrentMax = inductorCurrent + inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inductorCurrent - inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentRMS = Math.sqrt(Math.pow(inductorCurrentMax, 2) +
                    Math.pow(deltaInductorCurrent / 12, 2));
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) /
                    (2 * outputPower * frequency);
        } else {
            // DCM Mode
            deltaInductorCurrent = Math.sqrt(2 * outputCurrent * outputVoltage /
                    (inductance * frequency));
            inductorCurrentMax= deltaInductorCurrent;
            inductorCurrentMin = 0;
            rippleInductorCurrent = 100 * inductorCurrentMax / inductorCurrent;
            dutyCycleIdeal = (outputVoltage / inputVoltage) *
                    Math.sqrt((2 * inductance * frequency) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
        }
        deltaCapacitorVoltage =  (outputCurrent * dutyCycle * 2) / (capacitance * frequency);
        rippleCapacitorVoltage = 100 * deltaCapacitorVoltage / outputVoltage;

        Log.d(TAG, "Check variables after the MODE: " +
                "Duty Cycle Ideal = " + dutyCycleIdeal +
                ", Duty Cycle = " + dutyCycle +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Switch Current = " + switchCurrent +
                ", Diode Current = " + diodeCurrent +
                ", Max Inductor Current = " + inductorCurrentMax +
                ", Min Inductor Current = " + inductorCurrentMin +
                ", Delta Inductor Current = " + deltaInductorCurrent +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage +
                ", Inductance = " + inductance +
                ", Inductor Current RMS = " + inductorCurrentRMS +
                ", Resistance = " + resistance +
                ", Inductance = " + inductance +
                ", Capacitance = " + capacitance);
    }

    private static boolean checkBuckBoostConductionMode() {
        dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
        dutyCycle = ((outputVoltage) / (inputVoltage + outputVoltage)) / (efficiency / 100);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }
}
