package com.dcdcconvertersdesigndemo.views;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.dcdcconvertersdesigndemo.R;
import com.dcdcconvertersdesigndemo.controllers.SnubberDesignController;
import com.dcdcconvertersdesigndemo.helpers.Helpers;
import com.dcdcconvertersdesigndemo.interfaces.views.SnubberDesignViewInterface;

public class SnubberDesignView extends AppCompatActivity implements SnubberDesignViewInterface {
    private TextView capacitanceSnubberText;
    private TextView resistanceSnubberText;
    private TextView powerSnubberText;
    private TextView capacitanceSnubberTextView;
    private TextView resistanceSnubberTextView;
    private TextView powerSnubberTextView;
    private EditText timeDelayOffEditText;
    private EditText timeDelayFallEditText;
    private ImageView snubberImage;
    private ToggleButton resultsSnubberDesign;
    private RelativeLayout capacitanceSnubberLayout;
    private RelativeLayout resistanceSnubberLayout;
    private RelativeLayout powerSnubberLayout;

    private SnubberDesignController controller;
//    String TAG = "SnubberDesign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snubber_design);
        Helpers.setMinActionBar(this);
        setUIComponents();

        // Recovering data
        Bundle bundle = getIntent().getExtras();
        controller = new SnubberDesignController(this);
        controller.onCreateController(bundle);

        // Writing Results
        resultsSnubberDesign.setOnCheckedChangeListener((buttonView, btn) ->
                controller.onResultsClicked(btn));
    }

    public void setUIComponents() {
        // Text
        capacitanceSnubberText = findViewById(R.id.capacitance_snubber_text);
        resistanceSnubberText = findViewById(R.id.resistance_snubber_text);
        powerSnubberText = findViewById(R.id.power_snubber_text);

        // Get values from UI
        capacitanceSnubberTextView = findViewById(R.id.capacitance_snubber);
        resistanceSnubberTextView = findViewById(R.id.resistance_snubber);
        powerSnubberTextView = findViewById(R.id.power_snubber);
        timeDelayOffEditText = findViewById(R.id.time_delay_off);
        timeDelayFallEditText = findViewById(R.id.time_delay_fall);

        // Toggle Button
        resultsSnubberDesign = findViewById(R.id.results_snubber);

        // Image
        snubberImage = findViewById(R.id.snubber_image);

        // Layouts
        resistanceSnubberLayout = findViewById(R.id.resistance_snubber_layout);
        capacitanceSnubberLayout = findViewById(R.id.capacitance_snubber_layout);
        powerSnubberLayout = findViewById(R.id.power_snubber_layout);
    }

    public void setSnubberImage(double flag) {
        if (flag == 1) {
            snubberImage.setImageResource(R.drawable.snubber_buck);
        }
        if (flag == 2) {
            snubberImage.setImageResource(R.drawable.snubber_boost);

            // Create a new LayoutParams object to add a margin to the image layout
            int layoutMargin = 8;
            setLayoutMargin(layoutMargin);
        }
        if (flag == 3) {
            snubberImage.setImageResource(R.drawable.snubber_buck_boost);
        }
    }

    private void setLayoutMargin(int layoutMargin) {
        ViewGroup.MarginLayoutParams params =
                (ViewGroup.MarginLayoutParams) snubberImage.getLayoutParams();

        // Set the layout margins to 8dp on both the start and end sides
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, layoutMargin,
                getResources().getDisplayMetrics());
        params.setMargins(margin, 0, margin, 0);

        snubberImage.setLayoutParams(params);
    }

    @SuppressLint("SetTextI18n")
    public void updateUITexts() {
        capacitanceSnubberText.setText("Snubber Capacitor");
        resistanceSnubberText.setText("Snubber Resistor");
        powerSnubberText.setText("Power Dissipated");
    }

    public void updateUIValues(double capacitanceSnubber, double resistanceSnubber,
                                     double powerSnubber) {
        capacitanceSnubberTextView.setText(Helpers.formatValue(capacitanceSnubber, "F"));
        resistanceSnubberTextView.setText(Helpers.formatValue(resistanceSnubber, "Ω"));
        powerSnubberTextView.setText(Helpers.formatValue(powerSnubber, "W"));
    }

    public void showResults() {
        capacitanceSnubberLayout.setVisibility(View.VISIBLE);
        resistanceSnubberLayout.setVisibility(View.VISIBLE);
        powerSnubberLayout.setVisibility(View.VISIBLE);
    }

    public void hideResults() {
        capacitanceSnubberLayout.setVisibility(View.GONE);
        resistanceSnubberLayout.setVisibility(View.GONE);
        powerSnubberLayout.setVisibility(View.GONE);
    }

    public Double getTimeDelayOff() {
        return Double.parseDouble(timeDelayOffEditText.getText().toString()) * 1e-9;
    }

    public Double getTimeDelayFall() {
        return Double.parseDouble(timeDelayFallEditText.getText().toString()) * 1e-9;
    }

    public boolean isEmpty() {
        EditText[] fields = {timeDelayOffEditText, timeDelayFallEditText};
        for (EditText field : fields) {
            if (field.getText().toString().isEmpty()) {
                Helpers.showToast(this, "Error! Something is empty");
                return true;
            }
        }
        return false;
    }
}
