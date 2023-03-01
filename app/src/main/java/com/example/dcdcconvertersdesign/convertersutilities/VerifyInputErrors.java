package com.example.dcdcconvertersdesign.convertersutilities;

import static com.example.dcdcconvertersdesign.UsualDesign.*;
import static com.example.dcdcconvertersdesign.helpers.Helpers.showToast;

import android.app.Activity;
import android.widget.Toast;

import com.example.dcdcconvertersdesign.UsualDesign;

public class VerifyInputErrors {
    public static void verifyInputCommonErrors(Activity activity) {
        if ((dutyCycle > 0.95 || dutyCycle < 0.05) && inputVoltage !=
                outputVoltage && efficiency <= 100) {
            showToast(activity, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)");
        }
        if (dutyCycle < 0.95 && dutyCycle > 0.05 && inputVoltage !=
                outputVoltage && resistance < 0) {
            showToast(activity, "This project is not possible. Resistance is negative");
        }
        if ((dutyCycle < 0.95 && dutyCycle > 0.05) && inputVoltage !=
                outputVoltage && capacitance < 0) {
            showToast(activity, "This project is not possible. Capacitance is negative");
        }
        if (dutyCycle < 0.95 && dutyCycle > 0.05 && inputVoltage !=
                outputVoltage && inductance < 0) {
            showToast(activity, "This project is not possible. Inductance is negative");
        }
        if (efficiency > 100) {
            showToast(activity, "This project is not possible. Efficiency is bigger then 100%");
        }
    }

    public static void verifyInputErrorsBuck(Activity activity) {
        if (inputVoltage < outputVoltage) {
            showToast(activity, "Error! Output bigger than Input");
            return;
        }
        if (inputVoltage == outputVoltage) {
            showToast(activity, "Error! Input and Output are equal");
        }
        if ((dutyCycle > 0.95 || dutyCycle < 0.05) &&
                inputVoltage > outputVoltage && efficiency <= 100) {
            showToast(activity, "Error! Duty Cycle is out of the security range (0.05 < D > 0.95)");
        }
        verifyInputCommonErrors(activity);
    }

    public static void verifyInputErrorsBoost(Activity activity) {
        if (inputVoltage == outputVoltage) {
            showToast(activity, "Error! Input and Output are equal");
            return;
        }
        if (inputVoltage > outputVoltage) {
            showToast(activity, "Error! Input bigger than Output");
            return;
        }
        verifyInputCommonErrors(activity);
    }
    public static void verifyInputErrorsBuckBoost(Activity activity) {
        if (inputVoltage == outputVoltage) {
            Toast.makeText(activity,
                    "Error! Input and Output are equal", Toast.LENGTH_SHORT).show();
            return;
        }
        verifyInputCommonErrors(activity);
    }
}
