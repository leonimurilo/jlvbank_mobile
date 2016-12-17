package com.example.leonim.picartaodecredito.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.CreditCard;
import com.example.leonim.picartaodecredito.dbo.Invoice;
import com.example.leonim.picartaodecredito.dbo.Release;

import java.text.SimpleDateFormat;

public class CardDetailsActivity extends AppCompatActivity {

    private TextView numberTextView;
    private TextView dateTextView;
    private TextView editionTextView;
    private TextView brandTextView;
    private TextView statusTextView;
    private TextView totalTextView;
    private TextView currentTextView;
    private TextView invoicesTextView;
    protected CreditCard creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);

        numberTextView  = (TextView) findViewById(R.id.card_number);
        dateTextView  = (TextView) findViewById(R.id.card_date);
        editionTextView  = (TextView) findViewById(R.id.card_edition);
        brandTextView  = (TextView) findViewById(R.id.card_brand);
        statusTextView  = (TextView) findViewById(R.id.card_status);
        totalTextView  = (TextView) findViewById(R.id.card_release_amount);
        currentTextView  = (TextView) findViewById(R.id.card_current_release_amount);
        invoicesTextView = (TextView) findViewById(R.id.card_invoice_amount);

        Intent intent = getIntent();
        //check existence
        if(intent.hasExtra("card")){
            Bundle b = intent.getExtras();
            creditCard = (CreditCard) b.get("card");

            numberTextView.setText(creditCard.getNumber());
            dateTextView.setText(new SimpleDateFormat("dd/MM/yy").format(creditCard.getDueDate()));
            editionTextView.setText(creditCard.getClassification());
            brandTextView.setText(creditCard.getBrand());
            statusTextView.setText(creditCard.isStatus()+"");

            //fazer logica dos counts
            int totalReleases = 0;
            for(Invoice i:creditCard.getInvoiceArrayList()){
                totalReleases += i.getReleases().size();
            }
            totalTextView.setText(totalReleases+"");
            currentTextView.setText(creditCard.getInvoiceArrayList().get(creditCard.getInvoiceArrayList().size()-1).getReleases().size()+"");
            invoicesTextView.setText((creditCard.getInvoiceArrayList().size()-1)+"");
        }
    }
}
