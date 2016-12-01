package com.example.leonim.picartaodecredito.core;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.leonim.picartaodecredito.R;
import com.example.leonim.picartaodecredito.dbo.CreditCard;
import com.example.leonim.picartaodecredito.dbo.Invoice;
import com.example.leonim.picartaodecredito.dbo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    protected ViewPager mViewPager;
    private TabLayout tabLayout;
    protected ArrayList<CreditCard> cards;
    protected User user;
    private Intent intent;
    private Gson gson;
    private AsyncHttpClient httpClient;
    private ProgressDialog dialog;
    private int gottenCardsCount;
    private boolean dialogActive;


    protected BillFragment billFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogActive = false;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mViewPager = (ViewPager) findViewById(R.id.container);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        gottenCardsCount = 0;

        httpClient = new AsyncHttpClient();
        httpClient.setEnableRedirects(true);
        httpClient.setTimeout(6000);

        dialog = new ProgressDialog(MainActivity.this);

        gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        intent = getIntent();
        cards = new ArrayList<>();

}

    //block of code executed when all the application data are loaded
    private void dataDidLoad(){
        Log.d("App","Data Loaded");
        enableFragments();
        //refresh fragments recyclerviews
    }

    private void enableFragments(){
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        billFragment = (BillFragment) mSectionsPagerAdapter.getItem(0);
        tabLayout.setupWithViewPager(mViewPager);
        dialog.dismiss();
    }

    private void requestApplicationData(){


        dialog = ProgressDialog.show(MainActivity.this, "Getting your data...", "Please wait...",true);
        RequestParams params = new RequestParams();
        params.add("cpf",user.getCpf());
        params.add("password",user.getPassword());

        httpClient.post(ApplicationUtilities.URL + "/allCards", params, new AsyncHttpResponseHandler() {

            String responseString;

            private void loadResponseString(byte[] responseBody){
                try{
                    responseString = new String(responseBody,"UTF-8");
                }catch (UnsupportedEncodingException e){}
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                loadResponseString(responseBody);

                if(responseString.equals("1001") || responseString.equals("1002")){
                    showExitingDialog();
                }else{
                    try{
                        cards = gson.fromJson(responseString, new TypeToken<ArrayList<CreditCard>>(){}.getType());
                        try{
                            cards.size();
                            cards.get(0).getBrand();
                        }catch (Exception e){
                            Log.d("BUG", "(getting cards): "+e.getMessage());
                            showExitingDialog("Error parsing the server response (getting cards).");
                        }
                        putInvoicesInCards();

                    }catch (Exception e){
                        showExitingDialog(e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showExitingDialog("Status code:"+statusCode);
            }
        });

    }

    private void getUserData(){
        if(intent.hasExtra("user")){
            try{
                Bundle b = intent.getExtras();

                user = (User) b.get("user");



            }catch (Exception e){
                Log.d("OIOI", "erro gson "+e.getMessage());
            }
        }else{
            Log.d("uiu","sem user");
        }
    }

    private void putInvoicesInCards(){
        gottenCardsCount = 0;

        for(CreditCard creditCard:cards){
            RequestParams params = new RequestParams();
            params.add("cpf",user.getCpf());
            params.add("password",user.getPassword());
            params.add("numberCard", creditCard.getNumber());

            httpClient.post(ApplicationUtilities.URL + "/Invoices", params, new CustomAsyncHttpResponseHandler(creditCard));

        }
    }

    private void showExitingDialog(){
        dialog.dismiss();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Error loading the data from the server.");
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Go back to login",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void showExitingDialog(String customMessage){
        dialog.dismiss();
        if(!dialogActive) {
            dialogActive = true;
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Error loading the data from the server.\n" + customMessage);
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Go back to login",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialogActive = true;
                            finish();
                        }
                    });


            AlertDialog alert11 = builder1.create();

            alert11.show();
        }

    }

    private class CustomAsyncHttpResponseHandler extends AsyncHttpResponseHandler{

        CreditCard currentCreditCard;
        public CustomAsyncHttpResponseHandler(CreditCard currentCreditCard){
            this.currentCreditCard=currentCreditCard;
        }

        String responseString;

        private void loadResponseString(byte[] responseBody){
            try{
                responseString = new String(responseBody,"UTF-8");
            }catch (UnsupportedEncodingException e){}
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            showExitingDialog("Status code:"+statusCode);
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            loadResponseString(responseBody);


            Log.d("JSONTEST",responseString);
            if(responseString.equals("1001") || responseString.equals("1002")){
                currentCreditCard.setInvoiceArrayList(new ArrayList<Invoice>());

            }else{
                try{

                    currentCreditCard.setInvoiceArrayList((ArrayList<Invoice>) gson.fromJson(responseString, new TypeToken<ArrayList<Invoice>>(){}.getType()));
                    try{
                        currentCreditCard.getBrand();

                    }catch (Exception e){
                        showExitingDialog("Error parsing the server response (getting invoices).");
                    }
                }catch (Exception e){
                    Log.d("TEST",e.getMessage());
                    showExitingDialog(e.getMessage());
                }

            }
            Log.d("chubaka","real "+cards.get(gottenCardsCount).getInvoiceArrayList());

            //block after data parsing:
            gottenCardsCount++;
            if(gottenCardsCount==cards.size()){
                dataDidLoad();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        getUserData();
        requestApplicationData();

    }

    @Override
    protected void onDestroy() {
        dialog.dismiss();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            requestApplicationData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance() {
            PlaceholderFragment fragment = new PlaceholderFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            textView.setText("Section not available");
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        private Fragment one, two, three;

        @Override
        public Fragment getItem(int position) {

            if(position==0){
                if(one == null)
                    one = BillFragment.newInstance();
                return one;
            }

            if(position==1){
                if(two == null){
                    Log.d("test1","main");
                    two = ReportFragment.newInstance();
                }
                return two;
            }

            return PlaceholderFragment.newInstance();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Invoice";
                case 1:
                    return "Card Management";
                case 2:
                    return "Account";
            }
            return null;
        }
    }
}
