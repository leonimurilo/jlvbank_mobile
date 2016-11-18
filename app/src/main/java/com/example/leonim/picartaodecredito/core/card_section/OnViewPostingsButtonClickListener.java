package com.example.leonim.picartaodecredito.core.card_section;

/**
 * Interface used to handle a specific button click in the credit card viewHolder representation
 * @author Leoni Murilo de Lima
 */

public interface OnViewPostingsButtonClickListener {

    /**
     * Method that is called when the specific button (ViewPostings), inside the credit card view, is clicked
     * @param creditCardNumber credit card number (or maybe id) used to identify the credit card
     */
    void onViewPostingsClicked(String creditCardNumber);
}
