package com.example.dcdcconvertersdesign.interfaces.controllers;

import android.content.Context;

import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;

public interface ConverterDesignInterface {
    ConverterData performCalculations();
    boolean verifyInputErrors(ConverterData data);
}
