package com.example.apod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RawResponseActivity extends AppCompatActivity {

    static final String EXTRA_RAW_RESPONSE = "com.example.apod.RAW_RESPONSE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw_response);

        String rawResponse = getIntent().getStringExtra(EXTRA_RAW_RESPONSE);
        TextView textViewRawResponseText = findViewById(R.id.raw_response_text);
        textViewRawResponseText.setText(rawResponse);
    }
}
