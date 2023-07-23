package com.example.dcdcconvertersdesigndemo.models.converters.boost;

public class BoostConverterArrays {
    private static double[] diodeCurrentArray;
    public static double[] calculateOutputCurrentArray(double[] outputVoltageArray, double resistance) {
        double[] outputCurrentArray = new double[outputVoltageArray.length];
        for (int i = 0; i < outputVoltageArray.length; i++) {
            outputCurrentArray[i] = outputVoltageArray[i] / resistance;
        }
        return outputCurrentArray;
    }

    public static double[] calculateDiodeCurrentArray(double[] inductorCurrentArray, double[] sArray) {
        diodeCurrentArray = new double[inductorCurrentArray.length];

        for (int i = 0; i < diodeCurrentArray.length; i++) {
            diodeCurrentArray[i] = inductorCurrentArray[i] * (1 - sArray[i]);
        }
        return diodeCurrentArray;
    }

    public static double[] calculateSwitchCurrentArray(double[] inductorCurrentArray, double[] sArray) {
        diodeCurrentArray = new double[inductorCurrentArray.length];
        double[] switchCurrentArray = new double[inductorCurrentArray.length];
        diodeCurrentArray = calculateDiodeCurrentArray(inductorCurrentArray, sArray);

        for (int i = 0; i < switchCurrentArray.length; i++) {
            switchCurrentArray[i] = inductorCurrentArray[i] - diodeCurrentArray[i];
        }
        return switchCurrentArray;
    }

    public static double[] calculateCapacitorCurrentArray(double[] outputVoltageArray,
                                                          double[] inductorCurrentArray,
                                                          double resistance, double[] sArray) {
        diodeCurrentArray = new double[inductorCurrentArray.length];
        double[] capacitorCurrentArray = new double[inductorCurrentArray.length];
        double[] outputCurrentArray = calculateOutputCurrentArray(outputVoltageArray, resistance);

        diodeCurrentArray = calculateDiodeCurrentArray(inductorCurrentArray, sArray);

        for (int i = 0; i < capacitorCurrentArray.length; i++) {
            capacitorCurrentArray[i] = diodeCurrentArray[i] - outputCurrentArray[i];
        }
        return capacitorCurrentArray;
    }
}
