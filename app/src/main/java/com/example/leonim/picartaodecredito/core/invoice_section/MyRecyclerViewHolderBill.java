package com.example.leonim.picartaodecredito.core.invoice_section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.Release;

/**
 * Created by leonim on 13/10/2016.
 */

public class MyRecyclerViewHolderBill extends RecyclerView.ViewHolder implements View.OnClickListener{

    private Release release;
    private View view;
    private ImageView iconImageView;
    private TextView descriptionTextView;
    private TextView valueTextView;
    private TextView dateTextView;
    private Context context;
    private OnReleaseInteractionListener onReleaseInteractionListener;


    public MyRecyclerViewHolderBill(View view){
        super(view);
        this.view = view;
        context = view.getContext();
        iconImageView = (ImageView) view.findViewById(R.id.posting_view_icon);
        descriptionTextView = (TextView) view.findViewById(R.id.posting_view_description);
        valueTextView = (TextView) view.findViewById(R.id.posting_view_value);
        dateTextView = (TextView) view.findViewById(R.id.posting_view_date);
        release = null;
        view.setOnClickListener(this);
        iconImageView.setOnClickListener(this);
        dateTextView.setOnClickListener(this);

    }

    public void setOnReleaseInteractionListener(OnReleaseInteractionListener onReleaseInteractionListener){
        this.onReleaseInteractionListener = onReleaseInteractionListener;
    }


    public void setIcon(int drawableId){
        iconImageView.setImageResource(drawableId);
    }

    public void setDescription(String d){
        this.descriptionTextView.setText(d);
    }

    public void setDateText(String d){
        this.dateTextView.setText(d);
    }

    public void setValueTextView(String d){
        this.valueTextView.setText(d);
    }

    public void setReleaseId(Release release){
        this.release = release;
    }

    @Override
    public void onClick(View view) {

        if(this.view.getId()==view.getId()){
            onReleaseInteractionListener.onReleaseSelected(release);
        }

        if(view.getId()==iconImageView.getId()){
            Toast.makeText(context,"Icon clicked",Toast.LENGTH_SHORT).show();
        }

        if(view.getId()==dateTextView.getId()){
        }



    }
}
