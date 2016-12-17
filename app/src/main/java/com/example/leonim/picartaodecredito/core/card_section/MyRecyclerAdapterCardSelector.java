package com.example.leonim.picartaodecredito.core.card_section;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.CreditCard;

import java.util.ArrayList;

/**
 * Created by leonim on 14/10/2016.
 */

public class MyRecyclerAdapterCardSelector extends RecyclerView.Adapter<MyRecyclerViewHolderCardSelector> {

    private Context context;
    private ArrayList<CreditCard> creditCards;
    private OnViewPostingsButtonClickListener onViewPostingsClickListener;
    private OnCardInteractionListener onCardInteractionListener;

    public MyRecyclerAdapterCardSelector(ArrayList<CreditCard> creditCards, Context context)throws Exception{
        super();

        if(context==null)
            throw new Exception("Error. context is null");
        if(creditCards==null)
            throw new Exception("Error. arraylist is null");

        if(creditCards.contains(null))
            throw new Exception("Error. one of the credit cards is null");

        this.context = context;
        this.creditCards = creditCards;

    }

    public void setOnViewPostingsClickListener(OnViewPostingsButtonClickListener onViewPostingsClickListener){
        Log.d("test1","adapter ");
        this.onViewPostingsClickListener = onViewPostingsClickListener;
    }

    public void setOnLockCardButtonClickListener(OnCardInteractionListener onCardInteractionListener){
        this.onCardInteractionListener = onCardInteractionListener;
    }

    @Override
    public int getItemCount() {
        return creditCards.size();
    }

    @Override
    public MyRecyclerViewHolderCardSelector onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false);

        MyRecyclerViewHolderCardSelector viewHolder = new MyRecyclerViewHolderCardSelector(view);

        viewHolder.setOnViewPostingsClickListener(onViewPostingsClickListener);
        viewHolder.setOnLockCardButtonClickListener(onCardInteractionListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewHolderCardSelector holder, int position) {

        //get position, compare with the list size and if is the last or the first, change de icon

        CreditCard creditCard = creditCards.get(position);
        holder.setCardNumberText(creditCard.getNumber());
        holder.setCardTypeText(creditCard.getClassification());
        holder.setCard(creditCard);

    }
}


