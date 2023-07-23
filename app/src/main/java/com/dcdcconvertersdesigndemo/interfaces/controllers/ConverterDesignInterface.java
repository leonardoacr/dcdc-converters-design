package com.example.dcdcconvertersdesigndemo.interfaces.controllers;

import com.example.dcdcconvertersdesigndemo.utils.convertersutils.ConverterData;

public interface ConverterDesignInterface {
    ConverterData performCalculations();
    boolean verifyInputErrors(ConverterData data);
}
