package com.dcdcconvertersdesigndemo.models.converters.buck;

public class BuckConverterSimulator {
    private static double[] outputVoltageArray;
    private static double[] inductorCurrentArray;
    private static double[] timeArray;
    private static double[] sArray;
    public static void solveDiffEquations(double outputVoltage, double inputVoltage, double dutyCycle,
                                     double inductance, double capacitance, double resistance,
                                     double frequency, double timeStep, int numStep) {

        // Set the initial conditions
        double inductanceCurrentRealTime0 = 0;

        // Initialize the solution arrays
        timeArray = new double[numStep + 1];
        sArray = new double[numStep + 1];
        inductorCurrentArray = new double[numStep + 1];
        outputVoltageArray = new double[numStep + 1];
        double[] Yi = new double[numStep + 1];
        double[] Yv = new double[numStep + 1];
        Yi[0] = inductanceCurrentRealTime0;
        Yv[0] = outputVoltage;
        inductorCurrentArray[0] = inductanceCurrentRealTime0;
        outputVoltageArray[0] = outputVoltage;

        // Implement the Euler method
        for (int i = 0; i < numStep; i++) {
            // Extract the variables
            double t = i * timeStep;
            double inductanceCurrentRealTime = Yi[i];
            double outputVoltageRealTime = Yv[i];

            // Calculate s
            double s = ((t % (1.0 / frequency)) < (dutyCycle / frequency)) ? 1 : 0;

            // Calculate the derivatives
            double didt = -outputVoltageRealTime / inductance * (1 - s) -
                    (outputVoltageRealTime - inputVoltage) / inductance * s;
            double dvdt = -(-inductanceCurrentRealTime * resistance + outputVoltageRealTime) /
                    (resistance * capacitance) * (1 - s) -
                    (-inductanceCurrentRealTime * resistance + outputVoltageRealTime)
                            / (resistance * capacitance) * s;

            // Update the solution using Euler method
            Yi[i + 1] = Yi[i] + timeStep * didt;
            Yv[i + 1] = Yv[i] + timeStep * dvdt;
            inductorCurrentArray[i+1] = Yi[i+1];
            outputVoltageArray[i+1] = Yv[i+1];
            timeArray[i+1] = t;
            sArray[i] = s;
        }
    }

    public static double[] getOutputVoltageArray() {
        return outputVoltageArray;
    }

    public static double[] getInductorCurrentArray() {
        return inductorCurrentArray;
    }

    public static double[] getTimeArray() {
        return timeArray;
    }

    public static double[] getSArray() {
        return sArray;
    }
}
