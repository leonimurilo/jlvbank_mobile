package com.example.leonim.picartaodecredito.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.leonim.picartaodecredito.R;


/**
 * Activity responsible for showing the posting details in general.
 */
public class PostingDetailsActivity extends AppCompatActivity {

    private TextView valueTextView;
    private TextView typeTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_details);

        valueTextView = (TextView) findViewById(R.id.release_value);
        typeTextView = (TextView) findViewById(R.id.release_type);
        descriptionTextView = (TextView) findViewById(R.id.release_description);
        dateTextView = (TextView) findViewById(R.id.release_date);


        Intent intent = getIntent();
        //check existence
        double releaseId = intent.getDoubleExtra("releaseValue",-1);
        String date = intent.getStringExtra("releaseType");
        String description = intent.getStringExtra("releaseDescription");
        String type = intent.getStringExtra("releaseDate");

        valueTextView.setText(releaseId+"");
        descriptionTextView.setText(description);
        typeTextView.setText(type);
        dateTextView.setText(date);
    }



}
