package com.example.dcdcconvertersdesign.interfaces.views;

public interface SnubberDesignViewInterface {
    void setSnubberImage(double flag);
    void updateUITexts();
    void updateUIValues(double capacitanceSnubber, double resistanceSnubber, double powerSnubber);
    void showResults();
    void hideResults();
    Double getTimeDelayOff();
    Double getTimeDelayFall();
    boolean isEmpty();
}
