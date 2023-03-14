package com.example.dcdcconvertersdesign.controllers;

import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBoost;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuck;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuckBoost;

import android.content.Intent;
import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.UsualDesignControllerInterface;
import com.example.dcdcconvertersdesign.models.ConverterModel;
import com.example.dcdcconvertersdesign.models.UsualDesignModel;
import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;
import com.example.dcdcconvertersdesign.views.ConverterView;
import com.example.dcdcconvertersdesign.views.UsualDesignView;

public class UsualDesignController implements UsualDesignControllerInterface {

    private final UsualDesignView view;
    private final UsualDesignModel model;
    private int flag;

    public UsualDesignController(UsualDesignView view) {
        this.view = view;
        this.model = new UsualDesignModel();
    }

    public void onExampleClicked() {
        view.updateUIComponents(24, 12, 100, 20,
                1, 50, 90);
    }

    public void onBuckConverterClicked() {
        flag = 1;
        ConverterData data = performConverterCalculations(flag);
        data.setFlag(flag);

        if (verifyInputErrors(data)) {
            return;
        }
        navigateToConverter(data);
    }

    public void onBoostConverterClicked() {
        flag = 2;
        ConverterData data = performConverterCalculations(flag);
        data.setFlag(flag);

        if (verifyInputErrors(data)) {
            return;
        }
        navigateToConverter(data);
    }

    public void onBuckBoostConverterClicked() {
        flag = 3;
        ConverterData data = performConverterCalculations(flag);
        data.setFlag(flag);

        if (verifyInputErrors(data)) {
            return;
        }
        navigateToConverter(data);
    }

    private ConverterData performConverterCalculations(int flag) {
        ConverterData data = null;

        switch (flag) {
            case 1:
                data = new BuckConverter().performCalculations(view);
                break;
            case 2:
                data = new BoostConverter().performCalculations(view);
                break;
            case 3:
                data = new BuckBoostConverter().performCalculations(view);
                break;
        }

        return data;
    }

    private boolean verifyInputErrors(ConverterData data) {
        switch (data.getFlag()) {
            case 1:
                return new BuckConverter().verifyInputErrors(data);
            case 2:
                return new BoostConverter().verifyInputErrors(data);
            case 3:
                return new BuckBoostConverter().verifyInputErrors(data);
            default:
                return false;
        }
    }

    private void navigateToConverter(ConverterData data) {
        Intent intent = new Intent(view, ConverterView.class);
        Bundle bundle = model.sendDataToConverterActivity(data);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    private interface ConverterInterface {
        ConverterData performCalculations(UsualDesignView view);
        boolean verifyInputErrors(ConverterData data);
    }

    private class BuckConverter implements ConverterInterface {
        public ConverterData performCalculations(UsualDesignView view) {
            return ConverterModel.buckCalculations(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getOutputPower(), view.getRippleInductorCurrent(),
                    view.getRippleCapacitorVoltage(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            return verifyInputErrorsBuck(view, data.getInputVoltage(),
                    data.getOutputVoltage(), data.getDutyCycle(), data.getEfficiency(),
                    data.getResistance(), data.getCapacitance(), data.getInductance());
        }
    }

    private class BoostConverter implements ConverterInterface {
        public ConverterData performCalculations(UsualDesignView view) {
            return ConverterModel.boostCalculations(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getOutputPower(), view.getRippleInductorCurrent(),
                    view.getRippleCapacitorVoltage(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            return verifyInputErrorsBoost(view, data.getInputVoltage(),
                    data.getOutputVoltage(), data.getDutyCycle(), data.getEfficiency(),
                    data.getResistance(), data.getCapacitance(), data.getInductance());
        }
    }

    private class BuckBoostConverter implements ConverterInterface {
        public ConverterData performCalculations(UsualDesignView view) {
            return ConverterModel.buckBoostCalculations(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getOutputPower(), view.getRippleInductorCurrent(),
                    view.getRippleCapacitorVoltage(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            return verifyInputErrorsBuckBoost(view, data.getInputVoltage(),
                    data.getOutputVoltage(), data.getDutyCycle(), data.getEfficiency(),
                    data.getResistance(), data.getCapacitance(), data.getInductance());
        }
    }

}
