package com.example.dcdcconvertersdesign.utils.simulationutils;

import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dcdcconvertersdesign.R;

import java.util.Objects;

public class SaveDialog extends DialogFragment {
    private String saveKey;
    private static final String TAG = "SaveDialog";
    public interface SaveDialogListener {
        void onFinishEditDialog(String saveKey);
    }
    private SaveDialogListener mListener;

    public SaveDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_simulation_save_graph, container);
        Objects.requireNonNull(getDialog()).setTitle("Set Limits");

        Button saveCSVButton = view.findViewById(R.id.save_CSV_button);
        saveCSVButton.setOnClickListener(v -> {

            // Print the values using Log.d
            Log.d(TAG, "Save CSV");

            // Key for what to save
            saveKey = "CSV";

            mListener.onFinishEditDialog(saveKey);
            dismiss();
        });

        Button savePNGButton = view.findViewById(R.id.save_PNG_button);
        savePNGButton.setOnClickListener(v -> {

            // Print the values using Log.d
            Log.d(TAG, "Save PNG");

            // Key for what to save
            saveKey = "PNG";

            mListener.onFinishEditDialog(saveKey);
            dismiss();
        });

        return view;
    }
    public void setListener(SaveDialogListener listener) {
        mListener = listener;
    }
}
