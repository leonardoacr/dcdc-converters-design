package com.example.dcdcconvertersdesigndemo.utils.convertersutils;

public class ConverterData {
    private double inputVoltage;
    private double outputVoltage;
    private double frequency;
    private double dutyCycle;
    private double dutyCycleIdeal;
    private double resistance;
    private double capacitance;
    private double inductance;
    private double inductanceCritical;
    private double inputCurrent;
    private double outputCurrent;
    private double inductorCurrent;
    private double switchCurrent;
    private double diodeCurrent;
    private double deltaInductorCurrent;
    private double rippleInductorCurrent;
    private double deltaCapacitorVoltage;
    private double rippleCapacitorVoltage;
    private double inductorCurrentRMS;
    private double outputPower;
    private double efficiency;
    private boolean isCCM;

    private int flag;

    public ConverterData() {
        // constructor
    }

    public void setData(ConverterData data, double dutyCycle, double dutyCycleIdeal,
                        double resistance, double capacitance, double inductance,
                        double inductanceCritical, double inputCurrent, double outputCurrent,
                        double inductorCurrent, double switchCurrent, double diodeCurrent,
                        double deltaInductorCurrent, double deltaCapacitorVoltage,
                        double inductorCurrentRMS, boolean isCCM, double inputVoltage,
                        double outputVoltage, double frequency, double outputPower,
                        double rippleInductorCurrent, double rippleCapacitorVoltage,
                        double efficiency) {
        data.setDutyCycle(dutyCycle);
        data.setDutyCycleIdeal(dutyCycleIdeal);
        data.setResistance(resistance);
        data.setCapacitance(capacitance);
        data.setInductance(inductance);
        data.setInductanceCritical(inductanceCritical);
        data.setInputCurrent(inputCurrent);
        data.setOutputCurrent(outputCurrent);
        data.setInductorCurrent(inductorCurrent);
        data.setSwitchCurrent(switchCurrent);
        data.setDiodeCurrent(diodeCurrent);
        data.setDeltaInductorCurrent(deltaInductorCurrent);
        data.setRippleInductorCurrent(rippleInductorCurrent);
        data.setDeltaCapacitorVoltage(deltaCapacitorVoltage);
        data.setRippleCapacitorVoltage(rippleCapacitorVoltage);
        data.setInductorCurrentRMS(inductorCurrentRMS);
        data.setIsCCM(isCCM);
        data.setInputVoltage(inputVoltage);
        data.setOutputVoltage(outputVoltage);
        data.setFrequency(frequency);
        data.setOutputPower(outputPower);
        data.setEfficiency(efficiency);
    }

    // setters
    public void setInputVoltage(double inputVoltage) { this.inputVoltage = inputVoltage; }

    public void setOutputVoltage(double outputVoltage) { this.outputVoltage = outputVoltage; }

    public void setFrequency(double frequency) { this.frequency = frequency; }

    public void setFlag(int flag) { this.flag = flag; }

    public void setDutyCycle(double dutyCycle) {
        this.dutyCycle = dutyCycle;
    }

    public void setDutyCycleIdeal(double dutyCycleIdeal) {
        this.dutyCycleIdeal = dutyCycleIdeal;
    }

    public void setResistance(double resistance) {
        this.resistance = resistance;
    }

    public void setCapacitance(double capacitance) {
        this.capacitance = capacitance;
    }

    public void setInductance(double inductance) {
        this.inductance = inductance;
    }

    public void setInductanceCritical(double inductanceCritical) {
        this.inductanceCritical = inductanceCritical;
    }

    public void setInputCurrent(double inputCurrent) {
        this.inputCurrent = inputCurrent;
    }

    public void setOutputCurrent(double outputCurrent) {
        this.outputCurrent = outputCurrent;
    }

    public void setInductorCurrent(double inductorCurrent) {
        this.inductorCurrent = inductorCurrent;
    }

    public void setSwitchCurrent(double switchCurrent) {
        this.switchCurrent = switchCurrent;
    }

    public void setDiodeCurrent(double diodeCurrent) {
        this.diodeCurrent = diodeCurrent;
    }

    public void setDeltaInductorCurrent(double deltaInductorCurrent) {
        this.deltaInductorCurrent = deltaInductorCurrent;
    }

    public void setRippleInductorCurrent(double rippleInductorCurrent) {
        this.rippleInductorCurrent = rippleInductorCurrent;
    }
    public void setDeltaCapacitorVoltage(double deltaCapacitorVoltage) {
        this.deltaCapacitorVoltage = deltaCapacitorVoltage;
    }

    public void setRippleCapacitorVoltage(double rippleCapacitorVoltage) {
        this.rippleCapacitorVoltage = rippleCapacitorVoltage;
    }

    public void setInductorCurrentRMS(double inductorCurrentRMS) {
        this.inductorCurrentRMS = inductorCurrentRMS;
    }

    public void setIsCCM(boolean isCCM) {
        this.isCCM = isCCM;
    }

    public void setOutputPower(double outputPower) { this.outputPower = outputPower; }

    public void setEfficiency(double efficiency) { this.efficiency = efficiency;}

    // getters
    public double getInputVoltage() { return inputVoltage; }

    public double getOutputVoltage() { return outputVoltage; }

    public double getFrequency() { return frequency; }

    public int getFlag() { return flag; }

    public double getDutyCycle() {
        return dutyCycle;
    }

    public double getDutyCycleIdeal() {
        return dutyCycleIdeal;
    }

    public double getResistance() {
        return resistance;
    }

    public double getCapacitance() {
        return capacitance;
    }

    public double getInductance() {
        return inductance;
    }

    public double getInductanceCritical() {
        return inductanceCritical;
    }

    public double getInputCurrent() {
        return inputCurrent;
    }

    public double getOutputCurrent() {
        return outputCurrent;
    }

    public double getInductorCurrent() {
        return inductorCurrent;
    }

    public double getSwitchCurrent() {
        return switchCurrent;
    }

    public double getDiodeCurrent() {
        return diodeCurrent;
    }

    public double getDeltaInductorCurrent() {
        return deltaInductorCurrent;
    }

    public double getRippleInductorCurrent() { return rippleInductorCurrent; }
    public double getDeltaCapacitorVoltage() {
        return deltaCapacitorVoltage;
    }

    public double getRippleCapacitorVoltage() { return rippleCapacitorVoltage; }

    public double getInductorCurrentRMS() {
        return inductorCurrentRMS;
    }

    public boolean getIsCCM() {
        return isCCM;
    }

    public double getOutputPower() { return outputPower; }

    public double getEfficiency() {
        return efficiency;
    }
}