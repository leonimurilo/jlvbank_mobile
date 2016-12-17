package com.example.leonim.picartaodecredito.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leonim.picartaodecredito.core.card_section.MyRecyclerAdapterCardSelector;
import com.example.leonim.picartaodecredito.core.card_section.OnCardInteractionListener;
import com.example.leonim.picartaodecredito.core.card_section.OnViewPostingsButtonClickListener;
import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.CreditCard;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class ReportFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;
    private View myFragmentView;
    private RecyclerView cardSelectorRecyclerView;
    private MyRecyclerAdapterCardSelector adapterCardSelector;
    private OnCardInteractionListener onLockCardButtonClickListener;
    private MainActivity myActivity;
    protected OnViewPostingsButtonClickListener onViewPostingsButtonClickListener;

    public ReportFragment() {
        // Required empty public constructor
    }

    public static ReportFragment newInstance() {
        ReportFragment fragment = new ReportFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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

        myFragmentView = inflater.inflate(R.layout.fragment_report, container, false);

        //bloquear cartao
        onLockCardButtonClickListener = new OnCardInteractionListener() {
            @Override
            public void onCardDetailsButtonClicked(CreditCard card) {
                Intent i = new Intent(getContext(),CardDetailsActivity.class);
                i.putExtra("card",card);
                startActivity(i);
            }

            View icon;
            View button;
            void updateLockedCardUI(){
                icon.setVisibility(View.VISIBLE);
                this.button.setEnabled(false);
                this.button.setVisibility(View.GONE);
            }

            @Override
            public void onLockCardButtonClicked(CreditCard card, View icon, View button) {
                this.icon = icon;
                this.button = button;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setEnableRedirects(true);
                client.setTimeout(6000);
                String url = ApplicationUtilities.URL + ApplicationUtilities.LOCK_CARD_ROUTE;
                RequestParams params = new RequestParams();
                params.add("number",card.getNumber());
                params.add("cpf",myActivity.user.getCpf());
                params.add("password",myActivity.user.getPassword());

                client.post(url, params, new AsyncHttpResponseHandler() {
                    String responseString = "";
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        try {
                            responseString = new String(responseBody,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            Snackbar.make(myFragmentView, "Error converting the response to string: "+responseBody, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            e.printStackTrace();
                        }
                        if(responseString.equals("1000")){
                            Snackbar.make(myFragmentView, "Card locked! If you want to unlock the card please contact the bank.", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            updateLockedCardUI();
                        }else if(responseString.equals("1003")){
                            //erro ao bloquear
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Log.d("LOGSON","deu merda"+statusCode);
                        try {
                            responseString = new String(responseBody,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            Snackbar.make(myFragmentView, "Error converting the response to string: "+responseBody, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

                        }
                    }
                });


            }
        };

        //fim lock card

        onViewPostingsButtonClickListener = new OnViewPostingsButtonClickListener() {
            @Override
            public void onViewPostingsClicked(CreditCard card) {
                Log.d("espinou","Espinou main");
                try{

                    myActivity.mViewPager.setCurrentItem(0);
                    myActivity.billFragment.switchListedCreditCard(card);

                }catch(Exception e){
                    Log.d("espinou",e.getMessage());
                }
            }
        };

        return myFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myActivity = ((MainActivity)getActivity());

        cardSelectorRecyclerView = (RecyclerView) myFragmentView.findViewById(R.id.report_recycler);

        ArrayList<CreditCard> arrayList = new ArrayList<>();

        //arrayList.add(new CreditCard(10,"22224444999955555","943",new Date(),"Visa","Joao","Gold",false));
        //arrayList.add(new CreditCard(50,"23944837843843434","923",new Date(),"Master card","Joao","Silver",false));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        cardSelectorRecyclerView.setLayoutManager(layout);


        setFragmentContent();

    }

    private void setFragmentContent(){
        refreshRecyclerView();
    }

    private void refreshRecyclerView(){
        try{
            adapterCardSelector = new MyRecyclerAdapterCardSelector(myActivity.cards,this.getContext());
            adapterCardSelector.setOnViewPostingsClickListener(onViewPostingsButtonClickListener);
            adapterCardSelector.setOnLockCardButtonClickListener(onLockCardButtonClickListener);
            cardSelectorRecyclerView.setAdapter(adapterCardSelector);
        }catch (Exception e){

        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
