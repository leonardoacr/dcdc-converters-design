package com.example.dcdcconvertersdesigndemo.models;

import android.os.Bundle;

import com.example.dcdcconvertersdesigndemo.interfaces.models.InductorDesignModelInterface;

public class InductorDesignModel implements InductorDesignModelInterface {
    private double inductance;
    private double inductorCurrentRMS;
    private double deltaInductorCurrent;
    private double frequency;

    private final String[] models =
            {"EE-20/15",
                    "EE-30/07",
                    "EE-30/14",
                    "EE-42/15",
                    "EE-42/20",
                    "EE-55/21",
                    "EE-65/13",
                    "EE-65/26",
                    "EE-65/39"};

    private final double[][] cores =
            {{0.312, 0.26, 0.08112},
                    {0.6, 0.8, 0.48},
                    {1.2, 0.85, 1.02},
                    {1.81, 1.57, 2.8417},
                    {2.4, 1.57, 3.768},
                    {3.54, 2.5, 8.85},
                    {2.66, 3.7, 9.842},
                    {5.32, 3.7, 19.684},
                    {7.98, 3.7, 29.526}};

    private final double[][] conductors =
            {{10, 0.259, 0.273, 23.679, 0.052620, 0.058572},
                    {11, 0.231, 0.244, 18.778, 0.041729, 0.046738},
                    {12, 0.205, 0.218, 14.892, 0.033092, 0.037309},
                    {13, 0.183, 0.195, 11.809, 0.026243, 0.029793},
                    {14, 0.163, 0.174, 9.365, 0.020811, 0.0238},
                    {15, 0.145, 0.156, 7.427, 0.016504, 0.019021},
                    {16, 0.129, 0.139, 5.89, 0.013088, 0.015207},
                    {17, 0.115, 0.124, 4.671, 0.010379, 0.012164},
                    {18, 0.102, 0.111, 3.704, 0.008231, 0.009735},
                    {19, 0.091, 0.1, 2.937, 0.006527, 0.007794},
                    {20, 0.081, 0.089, 2.329, 0.005176, 0.006244},
                    {21, 0.072, 0.08, 1.847, 0.004105, 0.005004},
                    {22, 0.064, 0.071, 1.465, 0.003255, 0.004013},
                    {23, 0.057, 0.064, 1.162, 0.002582, 0.003221},
                    {24, 0.051, 0.057, 0.921, 0.002047, 0.002586},
                    {25, 0.045, 0.051, 0.731, 0.001624, 0.0020778},
                    {26, 0.04, 0.046, 0.579, 0.001287, 0.001671},
                    {27, 0.036, 0.041, 0.459, 0.001021, 0.001344},
                    {28, 0.032, 0.037, 0.364, 0.00081, 0.001083},
                    {29, 0.029, 0.033, 0.289, 0.000642, 0.000872},
                    {30, 0.025, 0.03, 0.229, 0.000509, 0.000704},
                    {31, 0.023, 0.027, 0.182, 0.000404, 0.000568},
                    {32, 0.02, 0.024, 0.144, 0.00032, 0.000459},
                    {33, 0.018, 0.022, 0.114, 0.000254, 0.000371},
                    {34, 0.016, 0.02, 0.091, 0.000201, 0.0003},
                    {35,0.014, 0.018, 0.072, 0.00016, 0.000243},
                    {36, 0.013, 0.016, 0.057, 0.000127, 0.000197},
                    {37, 0.011, 0.014, 0.045, 0.0001, 0.00016},
                    {38, 0.01, 0.013, 0.036, 0.00008, 0.00013},
                    {39, 0.009, 0.012, 0.028, 0.000063, 0.000106},
                    {40, 0.008, 0.01, 0.023, 0.00005, 0.000086},
                    {41, 0.007, 0.009, 0.018, 0.00004, 0.00007}};

    private double airGap;
    private double areaPercentage;
    private double turnNumber;
    private double awg;
    private double parallelConductors;
    private String coreModel;

    private ModelListener listener;

    public void inductorDesignEquations(double jMax, double bMax, double ku, double inductance,
                                        double deltaInductorCurrent, double inductorCurrentRMS, 
                                        double frequency) {
        double apCalculated, ap = 0, ae = 0, Aw, flagGeneral, maximumDiameter;
//        double copperDiameter;
        double percentageOfUsableArea, u0 = 0, ur = 0, p, pi = 3.1415;
        double sf = 0, st, isolatedConductorArea = 0;
        int  flag, i, i_N = 0, Nc, Nl, Cl;

        // Product of areas
        apCalculated = inductance * deltaInductorCurrent * inductorCurrentRMS * 1e4 /
                (ku * bMax * jMax); // copper winding cross-sectional area
        Nc = 2; //
        Nl = 8;
        flagGeneral = 0;

        while (flagGeneral == 0) {

            flag = 0;

            if (apCalculated <= 29.526 && apCalculated >= 0.08112) {
                for (i = Nl; i >= 0; i--){
                    if (i > 0 && flag == 0) {
                        if (cores[i - 1][Nc] <= apCalculated) {
                            ap = cores[i][Nc];
                            coreModel = models[i];
                            ae = cores[i][0];
                            i_N = i;
                            flag = 1;
                        }
                    }
                }
            }

            if (apCalculated > 29.526) {
                i_N = Nl;
                ap = cores[Nl][Nc];
                coreModel = models[Nl];
                ae = cores[Nl][0];
            }

            if (apCalculated < 0.08112) {
                i_N = 0;
                ap = cores[0][Nc];
                coreModel = models[0];
                ae = cores[0][0];
            }

            Aw = ap / ae;
            
            // Conductor Calculation to be used
            turnNumber = inductance * (inductorCurrentRMS + deltaInductorCurrent) * 1e4 / (bMax * ae);
            st = inductorCurrentRMS / jMax;
            p = 1.72e-4;
            u0 = 4 * pi * 1e-7;
            ur = 1;
            maximumDiameter = 2 * Math.sqrt(p / (pi * u0 * ur * frequency));

            // Searching in the table
            Cl = 31;
            flag = 0;

            if (maximumDiameter >= 0.007 && maximumDiameter <= 0.259) {
                for (i = 0; i <= Cl; i++){
                    if (flag == 0) {
                        if (conductors[i + 1][1] < maximumDiameter) {
//                            copperDiameter = conductors[i + 1][1];
                            awg = conductors[i + 1][0];
                            sf = conductors[i + 1][4];
                            isolatedConductorArea = conductors[i + 1][5];
                            flag = 1;
                        }
                    }
                }
            }

            if (maximumDiameter < 0.007) {
//                copperDiameter = conductors[Cl][1];
                awg = conductors[Cl][0];
                sf = conductors[Cl][4];
                isolatedConductorArea = conductors[Cl][5];
            }

            if (maximumDiameter > 0.259) {
//                copperDiameter = conductors[0][1];
                awg = conductors[0][0];
                sf = conductors[0][4];
                isolatedConductorArea = conductors[0][5];
            }

            parallelConductors = st / sf;

            // Gets the next integer greater than the calculated
            turnNumber = Math.ceil(turnNumber);
            parallelConductors = Math.ceil(parallelConductors);

            areaPercentage = parallelConductors * turnNumber * isolatedConductorArea * 100 / Aw;
            percentageOfUsableArea = 100;

            if (areaPercentage <= percentageOfUsableArea) {
                flagGeneral = 1;
            }

            if (areaPercentage > percentageOfUsableArea) {
                if (1 + i_N > Nl) {
                    handleCoreError();
                    return;
                } else {
                    apCalculated = cores[1 + i_N][Nc] - 1e-3;
                }
            }
        }
        airGap = Math.pow(turnNumber, 2) * u0 * ur * ae * 1e-4 / inductance;
    }

    public void handleCoreError() {
        if (listener != null) {
            listener.onEvent("Error! Doesn't exist core registered for this operation frequency");
        }
    }

    public void retrieveDataFromAdvanced(Bundle bundle) {
        inductance = bundle.getDouble("Inductance");
        inductorCurrentRMS = bundle.getDouble("Inductor_Current_RMS");
        deltaInductorCurrent = bundle.getDouble("DeltaIL");
        frequency = bundle.getDouble("Frequency");
    }

    public double getInductance() {
        return inductance;
    }

    public double getInductorCurrentRMS() {
        return inductorCurrentRMS;
    }

    public double getDeltaInductorCurrent() {
        return deltaInductorCurrent;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getAirGap() {
        return airGap;
    }

    public double getAreaPercentage() {
        return areaPercentage;
    }

    public double getTurnNumber() {
        return turnNumber;
    }

    public double getAwg() {
        return awg;
    }

    public double getParallelConductors() {
        return parallelConductors;
    }

    public String getCoreModel() {
        return coreModel;
    }

    public interface ModelListener {
        void onEvent(String message);
    }
}
