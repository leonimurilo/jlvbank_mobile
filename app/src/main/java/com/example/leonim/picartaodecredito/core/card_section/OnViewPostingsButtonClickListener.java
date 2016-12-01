package com.example.leonim.picartaodecredito.core.card_section;

import com.example.leonim.picartaodecredito.dbo.CreditCard;

/**
 * Interface used to handle a specific button click in the credit card viewHolder representation
 * @author Leoni Murilo de Lima
 */

public interface OnViewPostingsButtonClickListener {

    /**
     * Method that is called when the specific button (ViewPostings), inside the credit card view, is clicked
     * @param card credit card
     */
    void onViewPostingsClicked(CreditCard card);
}
