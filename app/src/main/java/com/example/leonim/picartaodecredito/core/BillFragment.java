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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.leonim.picartaodecredito.core.invoice_section.MyRecyclerAdapterBill;
import com.example.leonim.picartaodecredito.core.invoice_section.OnReleaseInteractionListener;
import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.CreditCard;
import com.example.leonim.picartaodecredito.dbo.Invoice;
import com.example.leonim.picartaodecredito.dbo.Release;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BillFragment extends android.support.v4.app.Fragment {

    private View myFragmentView;
    private MyRecyclerAdapterBill listAdapter;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private MainActivity myActivity;
    private ArrayList<String> reportOptions;
    private OnReleaseInteractionListener onReleaseInteractionListener;
    private MyRecyclerAdapterBill myRecyclerAdapterBill;

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
        Log.d("DEBUG_LOLA,","onCreate");
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("DEBUG_LOLA,","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("DEBUG_LOLA,","onStop");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DEBUG_LOLA,","onResume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("DEBUG_LOLA,","onStart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_bill, container, false);

        recyclerView = (RecyclerView) myFragmentView.findViewById(R.id.bill_recycler);
        spinner = (Spinner) myFragmentView.findViewById(R.id.choose_card_spinner);
        Log.d("DEBUG_LOLA,","onCreateView");
        return myFragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("DEBUG_LOLA,","onViewCreated");
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                refreshRecyclerView();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        reportOptions = new ArrayList<>();

        myActivity = ((MainActivity)getActivity());

        onReleaseInteractionListener = new OnReleaseInteractionListener() {
            @Override
            public void onReleaseSelected(Release r) {
                //open detail activity
                Intent i = new Intent(getContext(),PostingDetailsActivity.class);
                i.putExtra("release",r);
                startActivity(i);

            }
        };

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layout);
        setFragmentContent();
    }



    private void setFragmentContent(){
        refreshSpinnerOptions();
        refreshRecyclerView();

    }


    private void refreshRecyclerView(){
        int index = spinner.getSelectedItemPosition();

        try{
            myRecyclerAdapterBill = new MyRecyclerAdapterBill(myActivity.cards.get(index).getInvoiceArrayList(),getContext(),onReleaseInteractionListener);
            recyclerView.setAdapter(myRecyclerAdapterBill);
        }catch (Exception e){

        }

    }

    private void refreshSpinnerOptions(){
        Log.d("espinou", "dando refresh no spinner");
        reportOptions.clear();
        for(CreditCard creditCard:myActivity.cards){
            reportOptions.add(creditCard.getNumber()+"");
        }
        Log.d("espinou", "THIS PRO TRECO:"+this);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,reportOptions);
        spinner.setAdapter(spinnerAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void updateRecyclerView(){
        listAdapter.notifyDataSetChanged();
    }

    /**
     * Method that switch the listed credit card in the invoice fragment (this) when receive its identifier.
     * It switch the spinner selected item and also changes the recyclerView adapter to the one with the
     * wished data.
     *
     * @param card creditCard
     */
    public void switchListedCreditCard(CreditCard card){
        try{
            Log.d("espinou", "THIS PRO switch:"+this);
            Log.d("espinou", ""+reportOptions);
            int index = reportOptions.indexOf(card.getNumber());
            spinner.setSelection(index);
            refreshRecyclerView(index);
        }catch (Exception e){
            Log.d("LOGSON", e.getMessage());
        }
    }

    public void refreshRecyclerView(int cardIndex){
        try{
            listAdapter = new MyRecyclerAdapterBill(myActivity.cards.get(cardIndex).getInvoiceArrayList(),this.getContext(),onReleaseInteractionListener);
            recyclerView.setAdapter(listAdapter);
        }catch (Exception e){

        }

    }
}
