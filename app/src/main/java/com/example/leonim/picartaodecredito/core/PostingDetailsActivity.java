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

    private TextView idTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_details);

        idTextView = (TextView) findViewById(R.id.releaseId);


        Intent intent = getIntent();
        int releaseId = intent.getIntExtra("releaseId",-1);

        idTextView.setText(releaseId+"");
    }



}
