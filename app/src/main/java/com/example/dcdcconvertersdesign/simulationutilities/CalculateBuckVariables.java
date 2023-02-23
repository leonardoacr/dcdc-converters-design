package com.example.dcdcconvertersdesign.simulationutilities;

import static com.example.dcdcconvertersdesign.Simulation.diodeCurrentArray;
import static com.example.dcdcconvertersdesign.Simulation.outputCurrentArray;

public class CalculateBuckVariables {
    public static double[] calculateOutputCurrentArray(double[] outputVoltageArray, double resistance) {
        double[] outputCurrentArray = new double[outputVoltageArray.length];
        for (int i = 0; i < outputVoltageArray.length; i++) {
            outputCurrentArray[i] = outputVoltageArray[i] / resistance;
        }
        return outputCurrentArray;
    }

    public static double[] calculateInputCurrentArray(double[] inductorCurrentArray, double[] sArray) {
        double[] inputCurrentArray = new double[inductorCurrentArray.length];
        for (int i = 0; i < inductorCurrentArray.length; i++) {
            inputCurrentArray[i] = inductorCurrentArray[i] * sArray[i];
        }
        return inputCurrentArray;
    }

    public static double[] calculateDiodeCurrentArray(double[] inductorCurrentArray, double[] sArray) {
        double[] diodeCurrentArray = new double[inductorCurrentArray.length];
        double[] inputCurrentArray = calculateInputCurrentArray(inductorCurrentArray, sArray);

        for (int i = 0; i < diodeCurrentArray.length; i++) {
            diodeCurrentArray[i] = inductorCurrentArray[i] - inputCurrentArray[i];
        }
        return diodeCurrentArray;
    }

    public static double[] calculateCapacitorCurrentArray(double[] outputVoltageArray,
                                                          double[] inductorCurrentArray,
                                                          double resistance) {
        double[] capacitorCurrentArray = new double[inductorCurrentArray.length];
        double[] outputCurrentArray = calculateOutputCurrentArray(outputVoltageArray, resistance);

        for (int i = 0; i < capacitorCurrentArray.length; i++) {
            capacitorCurrentArray[i] = inductorCurrentArray[i] - outputCurrentArray[i];
        }
        return capacitorCurrentArray;
    }
}
