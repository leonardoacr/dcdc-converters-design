package com.example.dcdcconvertersdesigndemo.utils.simulationutils;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dcdcconvertersdesigndemo.R;

import java.util.Objects;

public class LimitsDialog extends DialogFragment {
    private static final String TAG = "LimitsDialog";
    public interface LimitsDialogListener {
        void onFinishEditDialog(Double xLowerLimit, Double xUpperLimit, Double yLowerLimit, Double yUpperLimit);
    }

    private EditText mXLowerLimitEditText;
    private EditText mXUpperLimitEditText;
    private EditText mYLowerLimitEditText;
    private EditText mYUpperLimitEditText;
    private LimitsDialogListener mListener;

    public LimitsDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_simulation_options, container);
        Objects.requireNonNull(getDialog()).setTitle("Set Limits");

        mXLowerLimitEditText = view.findViewById(R.id.x_lower_limit_edit_text);
        mXUpperLimitEditText = view.findViewById(R.id.x_upper_limit_edit_text);
        mYLowerLimitEditText = view.findViewById(R.id.y_lower_limit_edit_text);
        mYUpperLimitEditText = view.findViewById(R.id.y_upper_limit_edit_text);

        Button saveButton = view.findViewById(R.id.save_limits_button);
        saveButton.setOnClickListener(v -> {
            Double xLowerLimit = null;
            Double xUpperLimit = null;
            Double yLowerLimit = null;
            Double yUpperLimit = null;

            String xLowerLimitText = mXLowerLimitEditText.getText().toString().trim();
            if (!xLowerLimitText.isEmpty()) {
                xLowerLimit = Double.parseDouble(xLowerLimitText);
            }

            String xUpperLimitText = mXUpperLimitEditText.getText().toString().trim();
            if (!xUpperLimitText.isEmpty()) {
                xUpperLimit = Double.parseDouble(xUpperLimitText);
            }

            String yLowerLimitText = mYLowerLimitEditText.getText().toString().trim();
            if (!yLowerLimitText.isEmpty()) {
                yLowerLimit = Double.parseDouble(yLowerLimitText);
            }

            String yUpperLimitText = mYUpperLimitEditText.getText().toString().trim();
            if (!yUpperLimitText.isEmpty()) {
                yUpperLimit = Double.parseDouble(yUpperLimitText);
            }

            // Print the values using Log.d
            Log.d(TAG, "X Lower Limit: " + xLowerLimit);
            Log.d(TAG, "X Upper Limit: " + xUpperLimit);
            Log.d(TAG, "Y Lower Limit: " + yLowerLimit);
            Log.d(TAG, "Y Upper Limit: " + yUpperLimit);

            mListener.onFinishEditDialog(xLowerLimit, xUpperLimit, yLowerLimit, yUpperLimit);
            dismiss();
        });

        return view;
    }


    public void setListener(LimitsDialogListener listener) {
        mListener = listener;
    }
}
