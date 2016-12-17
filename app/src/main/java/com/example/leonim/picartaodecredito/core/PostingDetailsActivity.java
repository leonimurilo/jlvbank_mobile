package com.example.leonim.picartaodecredito.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.CreditCard;
import com.example.leonim.picartaodecredito.dbo.Release;

import java.text.SimpleDateFormat;


/**
 * Activity responsible for showing the posting details in general.
 */
public class PostingDetailsActivity extends AppCompatActivity {

    private TextView valueTextView;
    private TextView typeTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private TextView timeTextView;
    protected Release release;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting_details);

        valueTextView = (TextView) findViewById(R.id.release_value);
        typeTextView = (TextView) findViewById(R.id.release_type);
        descriptionTextView = (TextView) findViewById(R.id.release_description);
        dateTextView = (TextView) findViewById(R.id.release_date);
        timeTextView = (TextView) findViewById(R.id.release_time);


        Intent intent = getIntent();


        if(intent.hasExtra("release")){
            Bundle b = intent.getExtras();
            release = (Release) b.get("release");

            valueTextView.setText(release.getValue()+"");
            Log.d("teste22",release.getValue()+"");
            descriptionTextView.setText(release.getDescription());
            typeTextView.setText(release.getType());
            dateTextView.setText(new SimpleDateFormat("dd/MM/yy").format(release.getDate()));
            timeTextView.setText(ApplicationUtilities.dateToResumedString(release.getDate()));
        }

    }



}
