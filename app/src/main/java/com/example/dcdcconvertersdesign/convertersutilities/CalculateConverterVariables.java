package com.example.dcdcconvertersdesign.convertersutilities;

import static com.example.dcdcconvertersdesign.UsualDesign.*;

import android.util.Log;

public class CalculateConverterVariables {
    public static void buckCalculations() {
        // Pre-global-variables
        resistance = Math.pow(outputVoltage, 2) / outputPower;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = outputCurrent;
        switchCurrent = inputCurrent;
        diodeCurrent = outputCurrent - inputCurrent;
        outputVoltageMax = outputVoltage + outputVoltage * rippleCapacitorVoltage / 200.0;
        outputVoltageMin = outputVoltage - outputVoltage * rippleCapacitorVoltage / 200.0;
        deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;

        isCCM = checkBuckConductionMode();
        Log.d("CalculateVariables", isCCM + " " +
                inductanceCritical + " " + inductance + " " + dutyCycle);

        if (isCCM) {
            // CCM mode
            dutyCycleIdeal = outputVoltage / inputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductorCurrentMax = outputCurrent + outputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = outputCurrent - outputCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = (inductorCurrentMax - inductorCurrentMin);
            inductorCurrentRMS = outputCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / outputCurrent, 2)));

            inductance = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                    (frequency * deltaInductorCurrent);
        } else {
            // DCM mode
            inductorCurrentMax = outputCurrent * (rippleInductorCurrent / 100);
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            dutyCycleIdeal = 2 * outputPower / (deltaInductorCurrent * inputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductance = dutyCycle * (inputVoltage - outputVoltage) /
                    (frequency * inductorCurrentMax);
            double dcm2 = inductorCurrentMax * frequency * inductance / outputVoltage;
            double dcm1 = 1 - dutyCycle - dcm2;
            inductorCurrentRMS = deltaInductorCurrent*Math.sqrt((dutyCycle + dcm1)/3);
        }

        capacitance = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (
                16 * inductance * deltaCapacitorVoltage * Math.pow(frequency, 2));

        Log.d("CalculateVariables", isCCM + " " + inductanceCritical + " " + inductance + " " + dutyCycle);
    }

    private static boolean checkBuckConductionMode() {
        dutyCycleIdeal = outputVoltage / inputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductorCurrentMax = outputCurrent + outputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = outputCurrent - outputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = (inductorCurrentMax - inductorCurrentMin);
        inductance = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                (frequency * deltaInductorCurrent);
        inductanceCritical = dutyCycleIdeal * (inputVoltage - outputVoltage) * resistance / (2 * frequency * outputVoltage);
        return inductanceCritical <= inductance;
    }

    public static void boostCalculations() {
        // Pre-global-variables
        resistance = Math.pow(outputVoltage, 2) / outputPower;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        inductorCurrent = inputCurrent;
        outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);
        deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;
        String TAG = "CalculateVariables";
        Log.d(TAG, "Check variables before MODE: " +
                "Resistance = " + resistance +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
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
            inductorCurrentRMS = inputCurrent;
            inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
            inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        } else {
            // DCM mode
            inductorCurrentMax = inputCurrent * (rippleInductorCurrent / 100);
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            inductance = 2 * outputVoltage * (outputVoltage - inputVoltage) /
                    (resistance * frequency * Math.pow(deltaInductorCurrent, 2));
            dutyCycleIdeal = (1 / inputVoltage) * Math.sqrt((2 * inductance * outputVoltage *
                    frequency * (outputVoltage - inputVoltage)) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            inductorCurrentRMS = inputCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }
        capacitance = (outputCurrent * dutyCycle) * 2 / (deltaCapacitorVoltage * frequency);

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
        inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
        inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }

    public static void buckBoostCalculations() {
        // Pre-global-variables
        resistance = Math.pow(outputVoltage, 2) / outputPower;
        outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        switchCurrent = inputCurrent;
        diodeCurrent = outputCurrent;
        inductorCurrent = outputCurrent + inputCurrent;
        outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);
        deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;
        String TAG = "CalculateVariables";
        Log.d(TAG, "Check variables before MODE: " +
                "Resistance = " + resistance +
                ", Output Current = " + outputCurrent +
                ", Input Current = " + inputCurrent +
                ", Inductor Current = " + inductorCurrent +
                ", Max Output Voltage = " + outputVoltageMax +
                ", Min Output Voltage = " + outputVoltageMin +
                ", Delta Capacitor Voltage = " + deltaCapacitorVoltage);

        isCCM = checkBuckBoostConductionMode();
        Log.d(TAG, isCCM + " " +
                inductanceCritical + " " + inductance + " " + dutyCycle);

        if(isCCM) {
            // CCM Mode
            dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductorCurrentMax = inductorCurrent + inductorCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inductorCurrent - inductorCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
            inductorCurrentRMS = Math.sqrt(Math.pow(inductorCurrentMax, 2) +
                    Math.pow(deltaInductorCurrent / 12, 2));
            inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) /
                    (2 * outputPower * frequency);
        } else {
            // DCM Mode
            inductorCurrentMax = inductorCurrent * (rippleInductorCurrent / 100);
            inductorCurrentMin = 0;
            deltaInductorCurrent = inductorCurrentMax;
            inductance = 2 * outputCurrent * outputVoltage / (Math.pow(deltaInductorCurrent, 2) *
                    frequency);
            dutyCycleIdeal = (outputVoltage / inputVoltage) *
                    Math.sqrt((2 * inductance * frequency) / resistance);
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
        }
        capacitance = (outputCurrent * dutyCycle) * 2 / (deltaCapacitorVoltage * frequency);

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

    private static boolean checkBuckBoostConductionMode() {
        dutyCycleIdeal = (outputVoltage) / (inputVoltage + outputVoltage);
        dutyCycle = ((outputVoltage) / (inputVoltage + outputVoltage)) / (efficiency / 100);
        inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
        inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }
}
