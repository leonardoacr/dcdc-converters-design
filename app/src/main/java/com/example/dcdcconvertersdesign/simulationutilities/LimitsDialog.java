package com.example.dcdcconvertersdesign.simulationutilities;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dcdcconvertersdesign.R;

public class LimitsDialog extends DialogFragment {
    private static final String TAG = "LimitsDialog";
    public interface LimitsDialogListener {
        void onFinishEditDialog(double xLowerLimit, double xUpperLimit, double yLowerLimit, double yUpperLimit);
    }

    private EditText mXLowerLimitEditText;
    private EditText mXUpperLimitEditText;
    private EditText mYLowerLimitEditText;
    private EditText mYUpperLimitEditText;
    private LimitsDialogListener mListener;

    public LimitsDialog() {
        // Empty constructor required for DialogFragment
    }

    public static LimitsDialog newInstance() {
        LimitsDialog frag = new LimitsDialog();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_simulation_options, container);
        getDialog().setTitle("Set Limits");

        mXLowerLimitEditText = (EditText) view.findViewById(R.id.x_lower_limit_edit_text);
        mXUpperLimitEditText = (EditText) view.findViewById(R.id.x_upper_limit_edit_text);
        mYLowerLimitEditText = (EditText) view.findViewById(R.id.y_lower_limit_edit_text);
        mYUpperLimitEditText = (EditText) view.findViewById(R.id.y_upper_limit_edit_text);

        Button saveButton = (Button) view.findViewById(R.id.save_limits_button);
        saveButton.setOnClickListener(v -> {
            double xLowerLimit = Double.parseDouble(mXLowerLimitEditText.getText().toString());
            double xUpperLimit = Double.parseDouble(mXUpperLimitEditText.getText().toString());
            double yLowerLimit = Double.parseDouble(mYLowerLimitEditText.getText().toString());
            double yUpperLimit = Double.parseDouble(mYUpperLimitEditText.getText().toString());

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
