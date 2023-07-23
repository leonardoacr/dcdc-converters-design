package com.dcdcconvertersdesigndemo.interfaces.models;

import android.os.Bundle;

public interface AdvancedModelInterface {
    void retrieveDataFromConverterActivity(Bundle bundle);
    double getInductance();
    double getInductanceCritical();
    double getInputCurrent();
    double getOutputCurrent();
    double getInductorCurrent();
    double getSwitchCurrent();
    double getDiodeCurrent();
    double getOutputVoltage();
    double getFrequency();
    double getDeltaInductorCurrent();
    double getDeltaCapacitorVoltage();
    double getInductorCurrentRMS();
    boolean getIsCCM();
    int getFlag();
    int getFlagReverse();
}
