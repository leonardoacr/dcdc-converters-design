package com.example.dcdcconvertersdesign.interfaces.models;

public interface InductorDesignModelInterface {
    void inductorDesignEquations(double jMax, double bMax, double ku, double inductance,
                            double deltaInductorCurrent, double inductorCurrentRMS,
                            double frequency);
    double getInductance();
    double getInductorCurrentRMS();
    double getDeltaInductorCurrent();
    double getFrequency();
    double getAirGap();
    double getAreaPercentage();
    double getTurnNumber();
    double getAwg();
    double getParallelConductors();
    String getCoreModel();
}

