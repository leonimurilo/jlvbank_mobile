package com.example.leonim.picartaodecredito.core.card_section;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.CreditCard;

/**
 * Created by leonim on 14/10/2016.
 */

public class MyRecyclerViewHolderCardSelector extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

    private ImageView lockIcon;
    private Button buttonViewPostings;
    private Button buttonLock;
    private Button buttonMore;
    private TextView cardNumberTextView;
    private TextView cardTypeTextView;
    private OnViewPostingsButtonClickListener onViewPostingsClickListener;
    private OnCardInteractionListener onCardInteractionListener;
    private CreditCard card;

    public MyRecyclerViewHolderCardSelector(View view){
        super(view);
        this.buttonViewPostings = (Button) view.findViewById(R.id.button_view_postings);
        this.buttonLock = (Button) view.findViewById(R.id.button_lock_card);
        this.buttonMore = (Button) view.findViewById(R.id.button_more);
        this.cardNumberTextView = (TextView) view.findViewById(R.id.text_view_card_number);
        this.lockIcon = (ImageView) view.findViewById(R.id.lock_image);
        this.cardTypeTextView = (TextView) view.findViewById(R.id.card_type);

        Log.d("test1","viewholder ");
        view.setOnClickListener(this);
        buttonViewPostings.setOnClickListener(this);
        buttonLock.setOnClickListener(this);
        buttonMore.setOnClickListener(this);

    }

    public void setCard(CreditCard card){
        this.card = card;
        //if false, is locked
        if(!card.isStatus()){
            this.lockIcon.setVisibility(View.VISIBLE);
            this.buttonLock.setEnabled(false);
            this.buttonLock.setVisibility(View.GONE);
        }

    }

    public void setCardNumberText(String s){
        cardNumberTextView.setText(s);
    }


    public void setOnViewPostingsClickListener(OnViewPostingsButtonClickListener onViewPostingsClickListener){
        this.onViewPostingsClickListener =  onViewPostingsClickListener;
    }

    public void setOnLockCardButtonClickListener(OnCardInteractionListener onCardInteractionListener){
        this.onCardInteractionListener = onCardInteractionListener;
    }

    public void setCardTypeText(String typeText){
        cardTypeTextView.setText(typeText);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == this.buttonMore.getId()){
            onCardInteractionListener.onCardDetailsButtonClicked(card);
        }

        if (view.getId() == this.buttonViewPostings.getId()){
            onViewPostingsClickListener.onViewPostingsClicked(this.card);
        }
        if(view.getId() == this.buttonLock.getId()){
            onCardInteractionListener.onLockCardButtonClicked(card, lockIcon, buttonLock);
            this.buttonLock.setEnabled(false);
        }


    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
