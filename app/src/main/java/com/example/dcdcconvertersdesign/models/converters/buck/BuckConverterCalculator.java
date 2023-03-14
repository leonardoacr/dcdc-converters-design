package com.example.dcdcconvertersdesign.models.converters.buck;

import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;

public class BuckConverterCalculator {
    private static double dutyCycle;
    private static double dutyCycleIdeal;
    private static double resistance;
    private static double inductance;
    private static double inductanceCritical;
    private static double outputCurrent;
    private static double inductorCurrentMax;
    private static double inductorCurrentMin;
    private static double deltaInductorCurrent;

    public static ConverterData calculateBuckVariables(double inputVoltage, double outputVoltage, double outputPower,
                                                 double rippleInductorCurrent, double rippleCapacitorVoltage,
                                                 double frequency, double efficiency) {
        // Pre-global-variables
        resistance = Math.pow(outputVoltage, 2) / outputPower;
        outputCurrent = outputPower / outputVoltage;
        double inputCurrent = outputPower / inputVoltage;
        double inductorCurrent = outputCurrent;
        double diodeCurrent = outputCurrent - inputCurrent;
        double outputVoltageMax = outputVoltage + outputVoltage * rippleCapacitorVoltage / 200.0;
        double outputVoltageMin = outputVoltage - outputVoltage * rippleCapacitorVoltage / 200.0;
        double deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;

        boolean isCCM = checkBuckConductionMode(outputVoltage, inputVoltage, efficiency,
                rippleInductorCurrent, frequency);

        double inductorCurrentRMS;
        if (isCCM) {
            // CCM mode
            dutyCycleIdeal = outputVoltage / inputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            inductorCurrentMax = outputCurrent + outputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = outputCurrent - outputCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = (inductorCurrentMax - inductorCurrentMin);
            inductorCurrentRMS = inductorCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / inductorCurrent, 2)));

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

        double capacitance = (inputVoltage * (1 - dutyCycle) * dutyCycle) / (
                8 * inductance * deltaCapacitorVoltage * Math.pow(frequency, 2));

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, inputCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage, efficiency);

        // return the data object
        return data;
    }

    private static boolean checkBuckConductionMode(double outputVoltage, double inputVoltage,
                                                   double efficiency, double rippleInductorCurrent, double frequency) {
        dutyCycleIdeal = outputVoltage / inputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductorCurrentMax = outputCurrent + outputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = outputCurrent - outputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = (inductorCurrentMax - inductorCurrentMin);
        inductance = (inputVoltage * (1 - dutyCycle) * dutyCycle) /
                (frequency * deltaInductorCurrent);
        inductanceCritical = dutyCycleIdeal * (inputVoltage - outputVoltage) * resistance /
                (2 * frequency * outputVoltage);
        return inductanceCritical <= inductance;
    }
}
