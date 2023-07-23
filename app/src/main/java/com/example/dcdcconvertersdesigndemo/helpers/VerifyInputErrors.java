package com.example.dcdcconvertersdesign.helpers;

import static com.example.dcdcconvertersdesign.helpers.Helpers.showToast;

import android.app.Activity;

public class VerifyInputErrors {
    public static Boolean verifyInputCommonErrors(Activity activity, double inputVoltage,
                                                  double outputVoltage, double dutyCycle,
                                                  double efficiency, double resistance,
                                                  double capacitance, double inductance) {
        if ((dutyCycle > 0.95 || dutyCycle < 0.05) && inputVoltage !=
                outputVoltage && efficiency <= 100) {
            showToast(activity, "Error! Duty Cycle is out of the security range " +
                    "(0.05 < D > 0.95)");
            return true;
        }
        if (dutyCycle < 0.95 && dutyCycle > 0.05 && inputVoltage !=
                outputVoltage && resistance < 0) {
            showToast(activity, "This project is not possible. Resistance is negative");
            return true;
        }
        if ((dutyCycle < 0.95 && dutyCycle > 0.05) && inputVoltage !=
                outputVoltage && capacitance < 0) {
            showToast(activity, "This project is not possible. Capacitance is negative");
            return true;
        }
        if (dutyCycle < 0.95 && dutyCycle > 0.05 && inputVoltage !=
                outputVoltage && inductance < 0) {
            showToast(activity, "This project is not possible. Inductance is negative");
            return true;
        }
        if (efficiency > 100) {
            showToast(activity, "This project is not possible. Efficiency is bigger " +
                    "then 100%");
            return true;
        }
        return false;
    }

    public static boolean verifyInputErrorsBuck(Activity activity, double inputVoltage,
                                                double outputVoltage, double dutyCycle,
                                                double efficiency, double resistance,
                                                double capacitance, double inductance) {
        if (inputVoltage < outputVoltage) {
            showToast(activity, "Error! Output bigger than Input");
            return true;
        }
        if (inputVoltage == outputVoltage) {
            showToast(activity, "Error! Input and Output are equal");
            return true;
        }
        if ((dutyCycle > 0.95 || dutyCycle < 0.05) && efficiency <= 100) {
            showToast(activity, "Error! Duty Cycle is out of the security range " +
                    "(0.05 < D > 0.95)");
            return true;
        }
        return verifyInputCommonErrors(activity, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance);
    }


    public static boolean verifyInputErrorsBoost(Activity activity, double inputVoltage,
                                                 double outputVoltage, double dutyCycle,
                                                 double efficiency, double resistance,
                                                 double capacitance, double inductance) {
        if (inputVoltage == outputVoltage) {
            showToast(activity, "Error! Input and Output are equal");
            return true;
        }

        if (inputVoltage > outputVoltage) {
            showToast(activity, "Error! Input bigger than Output");
            return true;
        }

        if ((dutyCycle > 0.95 || dutyCycle < 0.05) && efficiency <= 100) {
            showToast(activity, "Error! Duty Cycle is out of the security range " +
                    "(0.05 < D > 0.95)");
            return true;
        }

        return verifyInputCommonErrors(activity, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance);
    }

    public static boolean verifyInputErrorsBuckBoost(Activity activity, double inputVoltage,
                                                     double outputVoltage, double dutyCycle,
                                                     double efficiency, double resistance,
                                                     double capacitance, double inductance) {
        if (inputVoltage == outputVoltage) {
            showToast(activity, "Error! Input and Output are equal");
            return true;
        }

        if ((dutyCycle > 0.95 || dutyCycle < 0.05) &&
                inputVoltage > outputVoltage && efficiency <= 100) {
            showToast(activity, "Error! Duty Cycle is out of the security range " +
                    "(0.05 < D > 0.95)");
            return true;
        }

        return verifyInputCommonErrors(activity, inputVoltage, outputVoltage, dutyCycle, efficiency,
                resistance, capacitance, inductance);
    }
}
