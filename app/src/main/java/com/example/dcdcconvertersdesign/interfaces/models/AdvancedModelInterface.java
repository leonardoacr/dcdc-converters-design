package com.example.dcdcconvertersdesign.interfaces.models;

import android.os.Bundle;

public interface AdvancedModelInterface {
    void retrieveDataFromConverterActivity(Bundle bundle);
    public double getInductance();
    public double getInductanceCritical();
    public double getInputCurrent();
    public double getOutputCurrent();
    public double getInductorCurrent();
    public double getSwitchCurrent();
    public double getDiodeCurrent();
    public double getOutputVoltage();
    public double getFrequency();
    public double getDeltaInductorCurrent();
    public double getDeltaCapacitorVoltage();
    public double getInductorCurrentRMS();
    public boolean getIsCCM();
    public int getFlag();
    public int getFlagReverse();
}
