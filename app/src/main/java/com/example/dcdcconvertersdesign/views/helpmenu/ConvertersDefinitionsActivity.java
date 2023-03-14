package com.example.dcdcconvertersdesign.views.helpmenu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;

import com.example.dcdcconvertersdesign.R;

public class ConvertersDefinitionsActivity extends AppCompatActivity {
    ImageView buckDefinitionImage;
    ImageView boostDefinitionImage;
    ImageView buckBoostDefinitionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_converters_definitions);
        setUIComponents();
    }

    public void setUIComponents() {
        // Image ID
        buckDefinitionImage = findViewById(R.id.buck_definition_image);
        buckDefinitionImage.setImageResource(R.drawable.buck);
        boostDefinitionImage = findViewById(R.id.boost_definition_image);
        boostDefinitionImage.setImageResource(R.drawable.boost);
        buckBoostDefinitionImage = findViewById(R.id.buck_boost_definition_image);
        buckBoostDefinitionImage.setImageResource(R.drawable.buck_boost);
    }
}
