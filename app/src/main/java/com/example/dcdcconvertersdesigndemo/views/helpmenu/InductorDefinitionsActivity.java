package com.example.dcdcconvertersdesign.views.helpmenu;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dcdcconvertersdesign.R;

public class InductorDefinitionsActivity extends AppCompatActivity {
    ImageView inductorDefinitionImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_inductor_definitions);

        inductorDefinitionImage = findViewById(R.id.inductor_image_definitions);
        inductorDefinitionImage.setImageResource(R.drawable.inductor_model);
    }
}
