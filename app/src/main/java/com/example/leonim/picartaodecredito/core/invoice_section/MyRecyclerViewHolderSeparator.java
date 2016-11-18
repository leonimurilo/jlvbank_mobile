package com.example.leonim.picartaodecredito.core.invoice_section;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.leonim.picartaodecredito.R;

/**
 * Created by leonim on 21/10/2016.
 */


public class MyRecyclerViewHolderSeparator extends RecyclerView.ViewHolder{

    private TextView startDateText, endDateText;

    public MyRecyclerViewHolderSeparator(View itemView) {
        super(itemView);
        startDateText = (TextView) itemView.findViewById(R.id.invoice_start_text);
        endDateText = (TextView) itemView.findViewById(R.id.invoice_end_text);

    }

    public void setStartDateText(String text){
        startDateText.setText(text);
    }

    public void setEndDateText(String text){
        endDateText.setText(text);
    }
}
