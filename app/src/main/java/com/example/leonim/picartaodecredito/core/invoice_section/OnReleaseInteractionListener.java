package com.example.leonim.picartaodecredito.core.invoice_section;

/**
 * Interface used to handle a Posting viewHolder (inside the posting recyclerview) interaction
 *
 * @author Leoni Murilo de Lima
 *
 */


public interface OnReleaseInteractionListener {

    /**
     * Method that is called when a Posting is selected by a view interaction
     * @param postingId - Posting ID used to find the posting details in the database or somewhere else
     */
    void onReleaseSelected(int postingId);
}
