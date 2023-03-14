package com.example.dcdcconvertersdesign.controllers;

import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBoost;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuck;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuckBoost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.controllers.ConverterDesignInterface;
import com.example.dcdcconvertersdesign.interfaces.controllers.DesignControllerInterface;
import com.example.dcdcconvertersdesign.models.UsualDesignModel;
import com.example.dcdcconvertersdesign.models.converters.boost.BoostConverterCalculator;
import com.example.dcdcconvertersdesign.models.converters.buck.BuckConverterCalculator;
import com.example.dcdcconvertersdesign.models.converters.buckboost.BuckBoostConverterCalculator;
import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;
import com.example.dcdcconvertersdesign.views.ConverterView;
import com.example.dcdcconvertersdesign.views.UsualDesignView;

public class UsualDesignController implements DesignControllerInterface {

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
        performConverterAction(flag);
    }

    public void onBoostConverterClicked() {
        flag = 2;
        performConverterAction(flag);
    }

    public void onBuckBoostConverterClicked() {
        flag = 3;
        performConverterAction(flag);
    }

    private void performConverterAction(int flag) {
        if (view.isEmpty()) {
            return;
        }

        ConverterData data = performConverterCalculations(flag);
        data.setFlag(flag);

        if (verifyInputErrors(data)) {
            return;
        }
        navigateToConverter(data);
    }

    private void navigateToConverter(ConverterData data) {
        Intent intent = new Intent(view, ConverterView.class);
        Bundle bundle = model.sendDataToConverterActivity(data);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    private ConverterData performConverterCalculations(int flag) {
        ConverterData data = null;

        switch (flag) {
            case 1:
                data = new BuckConverter(view).performCalculations();
                break;
            case 2:
                data = new BoostConverter(view).performCalculations();
                break;
            case 3:
                data = new BuckBoostConverter(view).performCalculations();
                break;
        }

        return data;
    }

    private boolean verifyInputErrors(ConverterData data) {
        switch (data.getFlag()) {
            case 1:
                return new BuckConverter(view).verifyInputErrors(data);
            case 2:
                return new BoostConverter(view).verifyInputErrors(data);
            case 3:
                return new BuckBoostConverter(view).verifyInputErrors(data);
            default:
                return false;
        }
    }

    private static class BuckConverter implements ConverterDesignInterface {
        private final Context mContext;
        public BuckConverter(Context context) {
            mContext = context;
        }

        public ConverterData performCalculations() {
            UsualDesignView view = (UsualDesignView) mContext;
            return BuckConverterCalculator.calculateBuckVariables(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getOutputPower(), view.getRippleInductorCurrent(),
                    view.getRippleCapacitorVoltage(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            UsualDesignView view = (UsualDesignView) mContext;
            return verifyInputErrorsBuck(view, data.getInputVoltage(),
                    data.getOutputVoltage(), data.getDutyCycle(), data.getEfficiency(),
                    data.getResistance(), data.getCapacitance(), data.getInductance());
        }
    }

    private static class BoostConverter implements ConverterDesignInterface {
        private final Context mContext;
        public BoostConverter(Context context) {
            mContext = context;
        }

        public ConverterData performCalculations() {
            UsualDesignView view = (UsualDesignView) mContext;
            return BoostConverterCalculator.calculateBoostVariables(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getOutputPower(), view.getRippleInductorCurrent(),
                    view.getRippleCapacitorVoltage(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            UsualDesignView view = (UsualDesignView) mContext;
            return verifyInputErrorsBoost(view, data.getInputVoltage(),
                    data.getOutputVoltage(), data.getDutyCycle(), data.getEfficiency(),
                    data.getResistance(), data.getCapacitance(), data.getInductance());
        }
    }

    private static class BuckBoostConverter implements ConverterDesignInterface {
        private final Context mContext;
        public BuckBoostConverter(Context context) {
            mContext = context;
        }

        public ConverterData performCalculations() {
            UsualDesignView view = (UsualDesignView) mContext;
            return BuckBoostConverterCalculator.calculateBuckBoostVariables(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getOutputPower(), view.getRippleInductorCurrent(),
                    view.getRippleCapacitorVoltage(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            UsualDesignView view = (UsualDesignView) mContext;
            return verifyInputErrorsBuckBoost(view, data.getInputVoltage(),
                    data.getOutputVoltage(), data.getDutyCycle(), data.getEfficiency(),
                    data.getResistance(), data.getCapacitance(), data.getInductance());
        }
    }
}
