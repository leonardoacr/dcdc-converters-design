package com.example.dcdcconvertersdesigndemo.interfaces.views;

public interface AdvancedViewInterface {
    void setInductorReverseNote (int flagReverse);
    void updateDisplayValues(double inductanceCritical, double inputCurrent, double outputCurrent,
                             double inductorCurrent, double switchCurrent, double diodeCurrent,
                             double deltaInductorCurrent, double deltaCapacitorVoltage);
}