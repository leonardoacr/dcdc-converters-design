package com.dcdcconvertersdesigndemo.views.helpmenu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.dcdcconvertersdesigndemo.R;

public class SnubberDefinitionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_snubber_definitions);
    }
}
