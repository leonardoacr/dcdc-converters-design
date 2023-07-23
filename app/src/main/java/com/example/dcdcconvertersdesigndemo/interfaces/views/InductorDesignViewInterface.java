package com.example.dcdcconvertersdesign.interfaces.views;

public interface InductorDesignViewInterface {
    void updateUITexts();
    void updateUIValues(double airGap, double areaPercentage, double turnNumber,
                        double awg, double parallelConductors, String coreModel);
    void updateUILayouts();
    void setTableButtonVisible();
    void setCoreError(String message);
    boolean isEmpty();
    Double getJmax();
    Double getBmax();
    Double getKu();
}
