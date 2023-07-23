package com.example.dcdcconvertersdesigndemo.interfaces.views;

public interface ConverterViewInterface {
    void setResources(int flag);
    void updateUIValues(double dutyCycle, double resistance, double capacitance, double inductance);
    void updateUITexts(boolean isCCM);
    void setModeWarning();
}
