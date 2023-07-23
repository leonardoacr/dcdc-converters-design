package com.dcdcconvertersdesigndemo.interfaces.controllers;

import com.dcdcconvertersdesigndemo.utils.convertersutils.ConverterData;

public interface ConverterDesignInterface {
    ConverterData performCalculations();
    boolean verifyInputErrors(ConverterData data);
}
