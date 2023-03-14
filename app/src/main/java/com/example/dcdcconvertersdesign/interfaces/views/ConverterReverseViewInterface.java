package com.example.dcdcconvertersdesign.interfaces.views;

public interface ConverterReverseViewInterface {
    void updateDisplayValues(double outputPower, double rippleInductorCurrent, double rippleCapacitorVoltage, double dutyCycle);
    void updateDisplayTexts(boolean isCCM);
    void setResources(int flag);
    void setModeWarningReverse();
}