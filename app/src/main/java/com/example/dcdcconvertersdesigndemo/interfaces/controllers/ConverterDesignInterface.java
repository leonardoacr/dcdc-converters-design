package com.example.dcdcconvertersdesign.interfaces.controllers;

import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;

public interface ConverterDesignInterface {
    ConverterData performCalculations();
    boolean verifyInputErrors(ConverterData data);
}
