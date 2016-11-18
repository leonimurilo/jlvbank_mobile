package com.example.leonim.picartaodecredito.core.card_section;

import android.view.View;

/**
 * Interface used to handle when any kind (Except the ViewPostings Button) of interaction happens within the creditCard viewHolder
 * @author Leoni Murilo de Lima
 */


//interface used when a credit card interface receive an interaction.
// (except in the "view Postings" button. This button uses the OnViewPostingsButtonClickListener interface)
public interface OnCardInteractionListener {

    /**
     * Method called when the lock card button is clicked in the credit card viewHolder
     */
    void onLockCardButtonClicked(int id, View icon);
}
