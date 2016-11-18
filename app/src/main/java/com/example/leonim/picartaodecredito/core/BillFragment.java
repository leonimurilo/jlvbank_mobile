package com.example.leonim.picartaodecredito.core;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leonim.picartaodecredito.core.invoice_section.MyRecyclerAdapterBill;
import com.example.leonim.picartaodecredito.core.invoice_section.OnReleaseInteractionListener;
import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.Invoice;
import com.example.leonim.picartaodecredito.dbo.Release;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BillFragment extends android.support.v4.app.Fragment {

    private View myFragmentView;
    private static final String[] reportOptions = {"2343 6564 2324 6556","6555 6232 2324 0012"};
    private MyRecyclerAdapterBill listAdapter;
    private Spinner spinner;
    private RecyclerView recyclerView;

    public BillFragment() {
        // Required empty public constructor
    }

    public static BillFragment newInstance() {
        BillFragment fragment = new BillFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        //Log.d("DEBUG_LOLA,","onCreate");
    }


    @Override
    public void onPause() {
        super.onPause();
        //Log.d("DEBUG_LOLA,","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.d("DEBUG_LOLA,","onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.d("DEBUG_LOLA,","onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        //Log.d("DEBUG_LOLA,","onStart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_bill, container, false);

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.bill_recycler);
        spinner = (Spinner) myFragmentView.findViewById(R.id.choose_card_spinner);

        return myFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ArrayList<Release> releaseArrayList = new ArrayList<>();

        for(int i=0;i<6;i++){
            Release p = new Release(i,i*2000,"Buy","Outback Iguatemi",new Date(i*1000*60*60*24),12);
            releaseArrayList.add(p);
        }

        releaseArrayList.add(new Release(68688,23.90,"Buy","Apple Store",new Date(),12));

        Invoice i = new Invoice("","",new Date(2016, Calendar.AUGUST, 1),new Date(2016, Calendar.AUGUST, 1),1,false,releaseArrayList,new Date(2016, Calendar.JULY, 1),222.00);
        Invoice j = new Invoice("","",new Date(),new Date(),1,false,releaseArrayList,new Date(),288.00);
        ArrayList<Invoice> invoiceArrayList = new ArrayList<>();

        invoiceArrayList.add(i);
        invoiceArrayList.add(j);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);

        OnReleaseInteractionListener onReleaseInteractionListener = new OnReleaseInteractionListener() {
            @Override
            public void onReleaseSelected(int postingId) {
                //open detail activity
                Intent i = new Intent(getContext(),PostingDetailsActivity.class);
                i.putExtra("releaseId",postingId);
                startActivity(i);

            }
        };

        listAdapter = new MyRecyclerAdapterBill(invoiceArrayList,this.getContext(),onReleaseInteractionListener);
        recyclerView.setAdapter(listAdapter);


        ArrayAdapter<String> ccListAdapter = new ArrayAdapter<>(this.getContext(),R.layout.simple_list_item_1,reportOptions);
        spinner.setAdapter(ccListAdapter);

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    /**
     * Method that switch the listed credit card in the invoice fragment (this) when receive its identifier.
     * It switch the spinner selected item and also changes the recyclerView adapter to the one with the
     * wished data.
     *
     * @param creditCardNumber creditCard identifier
     */
    public void switchListedCreditCard(String creditCardNumber){
        try{
            Toast.makeText(getContext(),creditCardNumber,Toast.LENGTH_SHORT).show();

            spinner.setSelection(java.util.Arrays.asList(reportOptions).indexOf(creditCardNumber));
            //reset adapter to the postings related to the credit card
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
        }
    }
}
