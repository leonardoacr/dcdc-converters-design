package com.example.dcdcconvertersdesign.interfaces.views;

public interface UsualDesignViewInterface {
    // Method to update the UI components
    void updateUIComponents(double inputVoltage, double outputVoltage, double outputPower,
                            double rippleInductorCurrent, double rippleCapacitorVoltage,
                            double frequency, double efficiency);
    // Method to check if any input field is empty
    boolean isEmpty();

    // Getter methods for input fields
    Double getInputVoltage();
    Double getOutputVoltage();
    Double getOutputPower();
    Double getRippleInductorCurrent();
    Double getRippleCapacitorVoltage();
    Double getFrequency();
    Double getEfficiency();
}
