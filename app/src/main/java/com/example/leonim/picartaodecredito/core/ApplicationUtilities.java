package com.example.leonim.picartaodecredito.core;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;

import java.util.Date;

/**
 * Created by leonim on 20/10/2016.
 */

public class ApplicationUtilities {

    public static String URL = "http://10.0.2.2:8080/jlvbank_war_exploded";
    public static String LOCK_CARD_ROUTE = "/BlockCard";
    public static String ALL_CARDS_ROUTE = "/allCards";
    public static String INVOICES_ROUTE = "/Invoices";


    public static String dateToResumedString(Date date){
        long ms = date.getTime();
        String resumeString = null;

        int days = Days.daysBetween(new DateTime(date),new DateTime(new Date())).getDays();

        resumeString = days+" days ago";
        return resumeString;
    }

}
