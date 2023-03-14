package com.example.dcdcconvertersdesign.interfaces.models;

import android.os.Bundle;

public interface SnubberDesignModelInterface {
    void snubberEquations(int flag, double outputVoltage, double inputCurrent,
                          double outputCurrent, double frequency, double timeDelayFall,
                          double timeDelayOff);
    void retrieveDataFromAdvanced(Bundle bundle);
    double getInputCurrent();
    double getOutputVoltage();
    double getOutputCurrent();
    double getFrequency();
    int getFlag();
    double getCapacitanceSnubber();
    double getResistanceSnubber();
    double getPowerSnubber();
}
