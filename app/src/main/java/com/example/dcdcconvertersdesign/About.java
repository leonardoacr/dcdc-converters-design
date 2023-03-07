package com.example.dcdcconvertersdesign;

import static com.example.dcdcconvertersdesign.convertersutilities.ConvertersUtils.inductance;
import static com.example.dcdcconvertersdesign.convertersutilities.ConvertersUtils.inductanceCritical;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class About extends AppCompatActivity {

    Button email;

    public void createObjects()
    {
        // Buttons
        email = findViewById(R.id.email);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_help);
        setContentView(R.layout.activity_about);
        createObjects();

        // Email
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"dcdcconvertersproject@gmail.com"});
                startActivity(Intent.createChooser(intent, ""));
            }
        });
    }
}