package com.example.dcdcconvertersdesign.views;

import static com.example.dcdcconvertersdesign.helpers.Helpers.showToast;
import static com.example.dcdcconvertersdesign.helpers.Helpers.stringFormat;

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

import com.example.dcdcconvertersdesign.R;
import com.example.dcdcconvertersdesign.controllers.SnubberDesignController;
import com.example.dcdcconvertersdesign.helpers.Helpers;
import com.example.dcdcconvertersdesign.models.SnubberDesignModel;

public class SnubberDesignActivity extends AppCompatActivity {
    TextView capacitanceSnubberTextView, resistanceSnubberTextView, powerSnubberTextView,
            capacitanceSnubberText, resistanceSnubberText, powerSnubberText;
    EditText timeDelayOffEditText, timeDelayFallEditText;
    ImageView snubberImage;
    ToggleButton resultsSnubberDesign;
    RelativeLayout capacitanceSnubberLayout, resistanceSnubberLayout, powerSnubberLayout;
    String TAG = "SnubberDesign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snubber_design);
        Helpers.setMinActionBar(this);
        setUIComponents();

        // Recovering data
        Bundle bundle = getIntent().getExtras();
        SnubberDesignModel model = new SnubberDesignModel();
        SnubberDesignController controller = new SnubberDesignController(this, model);
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
    public void updateDisplayTexts() {
        capacitanceSnubberText.setText("Snubber Capacitor");
        capacitanceSnubberLayout.setVisibility(View.VISIBLE);
        resistanceSnubberText.setText("Snubber Resistor");
        resistanceSnubberLayout.setVisibility(View.VISIBLE);
        powerSnubberText.setText("Power Dissipated");
        powerSnubberLayout.setVisibility(View.VISIBLE);
    }

    public void updateDisplayValues(double capacitanceSnubber, double resistanceSnubber,
                                     double powerSnubber) {
        capacitanceSnubberTextView.setText(Helpers.formatValue(capacitanceSnubber, "F"));
        resistanceSnubberTextView.setText(Helpers.formatValue(resistanceSnubber, "Ω"));
        powerSnubberTextView.setText(Helpers.formatValue(powerSnubber, "W"));
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
