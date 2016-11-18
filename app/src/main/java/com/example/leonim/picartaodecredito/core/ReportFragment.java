package com.example.leonim.picartaodecredito.core;

import android.content.Context;
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
import android.widget.Toast;

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
import java.util.Date;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;


public class ReportFragment extends android.support.v4.app.Fragment {

    private OnFragmentInteractionListener mListener;
    private View myFragmentView;
    private RecyclerView cardSelectorRecyclerView;
    private MyRecyclerAdapterCardSelector adapterCardSelector;
    private OnViewPostingsButtonClickListener onViewPostingsClickListener;
    private OnCardInteractionListener onLockCardButtonClickListener;

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

        onLockCardButtonClickListener = new OnCardInteractionListener() {
            View icon;
            void showLockIcon(){
                icon.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLockCardButtonClicked(int id, View icon) {
                this.icon = icon;
                AsyncHttpClient client = new AsyncHttpClient();
                client.setEnableRedirects(true);
                client.setTimeout(6000);
                String url = "http://10.0.2.2:8080//teste_war_exploded/lock_card";
                RequestParams params = new RequestParams();
                params.add("id",id+"");

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
                        if(responseString.equals("1")){
                            Log.d("HTTPTEST",responseString);
                            showLockIcon();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        try {
                            responseString = new String(responseBody,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            Snackbar.make(myFragmentView, "Error converting the response to string: "+responseBody, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            e.printStackTrace();
                        }
                    }
                });


            }
        };

        return myFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onViewPostingsClickListener = ((MainActivity)getActivity()).getOnViewPostingsClickListener();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cardSelectorRecyclerView = (RecyclerView) myFragmentView.findViewById(R.id.report_recycler);

        ArrayList<CreditCard> arrayList = new ArrayList<>();

        arrayList.add(new CreditCard(10,"22224444999955555","943",new Date(),"Visa","Joao","Gold",false));
        arrayList.add(new CreditCard(50,"23944837843843434","923",new Date(),"Master card","Joao","Silver",false));

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        cardSelectorRecyclerView.setLayoutManager(layout);

        adapterCardSelector = new MyRecyclerAdapterCardSelector(arrayList,this.getContext());
        adapterCardSelector.setOnViewPostingsClickListener(onViewPostingsClickListener);
        adapterCardSelector.setOnLockCardButtonClickListener(onLockCardButtonClickListener);

        cardSelectorRecyclerView.setAdapter(adapterCardSelector);

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }



}
