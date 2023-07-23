package com.example.dcdcconvertersdesign.models.converters.boost;

import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;

public class BoostConverterCalculator {
    private static double dutyCycle;
    private static double dutyCycleIdeal;
    private static double inductance;
    private static double inductanceCritical;
    private static double inputCurrent;
    private static double inductorCurrentMax;
    private static double inductorCurrentMin;
    private static double deltaInductorCurrent;

    public static ConverterData calculateBoostVariables(double inputVoltage, double outputVoltage, double outputPower,
                                                  double rippleInductorCurrent, double rippleCapacitorVoltage,
                                                  double frequency, double efficiency) {
        // Pre-global-variables
        double resistance = Math.pow(outputVoltage, 2) / outputPower;
        double outputCurrent = outputPower / outputVoltage;
        inputCurrent = outputPower / inputVoltage;
        double inductorCurrent = inputCurrent;
        double outputVoltageMax = outputVoltage + outputVoltage * (rippleCapacitorVoltage / 200);
        double outputVoltageMin = outputVoltage - outputVoltage * (rippleCapacitorVoltage / 200);
        double deltaCapacitorVoltage = outputVoltageMax - outputVoltageMin;

        boolean isCCM = checkBoostConductionMode(outputVoltage, inputVoltage, efficiency,
                rippleInductorCurrent, frequency, outputPower);

        double switchCurrent;
        double diodeCurrent;
        double inductorCurrentRMS;
        if(isCCM) {
            dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
            dutyCycle = dutyCycleIdeal / (efficiency / 100);
            switchCurrent = dutyCycle * inputCurrent;
            diodeCurrent = (1 - dutyCycle) * inputCurrent;
            inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
            inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
            deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
            inductorCurrentRMS = inductorCurrent * Math.sqrt(1 + ((1 / 12.0) *
                    Math.pow(deltaInductorCurrent / inductorCurrent, 2)));
            inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
            inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) /
                    (2 * outputPower * frequency);
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
            inductorCurrentRMS = inductorCurrent * Math.sqrt((1 - dutyCycle) / 3);
        }
        double capacitance = outputCurrent * dutyCycle / (frequency * deltaCapacitorVoltage);

        // create a ConverterData object
        ConverterData data = new ConverterData();

        // set data using the setData() method
        data.setData(data, dutyCycle, dutyCycleIdeal, resistance, capacitance, inductance,
                inductanceCritical, inputCurrent, outputCurrent, inductorCurrent, switchCurrent,
                diodeCurrent, deltaInductorCurrent, deltaCapacitorVoltage, inductorCurrentRMS, isCCM,
                inputVoltage, outputVoltage, frequency, outputPower, rippleInductorCurrent,
                rippleCapacitorVoltage, efficiency);

        // return the data object
        return data;
    }

    private static boolean checkBoostConductionMode(double outputVoltage, double inputVoltage,
                                                    double efficiency, double rippleInductorCurrent,
                                                    double frequency, double outputPower) {
        dutyCycleIdeal = (outputVoltage - inputVoltage) / outputVoltage;
        dutyCycle = dutyCycleIdeal / (efficiency / 100);
        inductorCurrentMax = inputCurrent + inputCurrent * (rippleInductorCurrent / 200);
        inductorCurrentMin = inputCurrent - inputCurrent * (rippleInductorCurrent / 200);
        deltaInductorCurrent = inductorCurrentMax - inductorCurrentMin;
        inductance = (inputVoltage * dutyCycle) / (frequency * deltaInductorCurrent);
        inductanceCritical = (Math.pow(inputVoltage, 2) * dutyCycle) / (2 * outputPower * frequency);
        return inductanceCritical <= inductance;
    }
}
