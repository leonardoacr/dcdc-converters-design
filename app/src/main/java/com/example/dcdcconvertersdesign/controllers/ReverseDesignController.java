package com.example.dcdcconvertersdesign.controllers;

import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBoost;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuck;
import static com.example.dcdcconvertersdesign.helpers.VerifyInputErrors.verifyInputErrorsBuckBoost;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.dcdcconvertersdesign.interfaces.controllers.ConverterDesignInterface;
import com.example.dcdcconvertersdesign.interfaces.controllers.DesignControllerInterface;
import com.example.dcdcconvertersdesign.models.ConverterReverseModel;
import com.example.dcdcconvertersdesign.models.ReverseDesignModel;
import com.example.dcdcconvertersdesign.utils.convertersutils.ConverterData;
import com.example.dcdcconvertersdesign.views.ConverterReverseView;
import com.example.dcdcconvertersdesign.views.ReverseDesignView;

public class ReverseDesignController implements DesignControllerInterface {
    private final ReverseDesignView view;
    private final ReverseDesignModel model;
    private int flag;

    private final String TAG = "ReverseDesignController";

    public ReverseDesignController(ReverseDesignView view) {
        this.view = view;
        this.model = new ReverseDesignModel();
    }

    public void onExampleClicked() {
        view.updateUIComponents(24, 12, 1.44, 71.11,
                17.36, 50, 90);
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

        navigateToConverterReverse(data);
    }

    private void navigateToConverterReverse(ConverterData converterData) {
        Intent intent = new Intent(view.getApplicationContext(), ConverterReverseView.class);
        Bundle bundle = model.sendDataToConverterReverseActivity(converterData);
        intent.putExtras(bundle);
        view.startActivity(intent);
    }

    private ConverterData performConverterCalculations(int flag) {
        ConverterData data = null;

        switch (flag) {
            case 1:
                data = new ReverseDesignController.BuckConverter(view).performCalculations();
                break;
            case 2:
                data = new ReverseDesignController.BoostConverter(view).performCalculations();
                break;
            case 3:
                data = new ReverseDesignController.BuckBoostConverter(view).performCalculations();
                break;
        }

        return data;
    }

    private boolean verifyInputErrors(ConverterData data) {
        switch (data.getFlag()) {
            case 1:
                return new ReverseDesignController.BuckConverter(view).verifyInputErrors(data);
            case 2:
                return new ReverseDesignController.BoostConverter(view).verifyInputErrors(data);
            case 3:
                return new ReverseDesignController.BuckBoostConverter(view).verifyInputErrors(data);
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
            ReverseDesignView view = (ReverseDesignView) mContext;
            return ConverterReverseModel.buckCalculations(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getResistance(), view.getInductance(),
                    view.getCapacitance(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            ReverseDesignView view = (ReverseDesignView) mContext;
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
            ReverseDesignView view = (ReverseDesignView) mContext;
            return ConverterReverseModel.boostCalculations(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getResistance(), view.getInductance(),
                    view.getCapacitance(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            ReverseDesignView view = (ReverseDesignView) mContext;
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
            ReverseDesignView view = (ReverseDesignView) mContext;
            return ConverterReverseModel.buckBoostCalculations(view.getInputVoltage(),
                    view.getOutputVoltage(), view.getResistance(), view.getInductance(),
                    view.getCapacitance(), view.getFrequency(), view.getEfficiency());
        }

        public boolean verifyInputErrors(ConverterData data) {
            ReverseDesignView view = (ReverseDesignView) mContext;
            return verifyInputErrorsBuckBoost(view, data.getInputVoltage(),
                    data.getOutputVoltage(), data.getDutyCycle(), data.getEfficiency(),
                    data.getResistance(), data.getCapacitance(), data.getInductance());
        }
    }
}
