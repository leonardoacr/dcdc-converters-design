package com.example.dcdcconvertersdesign.interfaces.views;

public interface ReverseDesignViewInterface {
    void updateUIComponents(double inputVoltage, double outputVoltage,
                            double resistance, double inductance, double capacitance,
                            double frequency, double efficiency);
    boolean isEmpty();
    Double getInputVoltage();
    Double getOutputVoltage();
    Double getResistance();
    Double getInductance();
    Double getCapacitance();
    Double getFrequency();
    Double getEfficiency();
}
