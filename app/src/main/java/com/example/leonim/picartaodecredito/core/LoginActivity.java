package com.example.leonim.picartaodecredito.core;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.leonim.picartaodecredito.dbo.CreditCard;
import com.example.leonim.picartaodecredito.dbo.Invoice;
import com.example.leonim.picartaodecredito.dbo.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.*;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonim.picartaodecredito.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText cpfEditText;
    private EditText passwordEditText;
    private TextView noaccountButton;
    private View parentView;
    private User user;
    private int ccInvoiceDataReceivedCount;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        noaccountButton = (TextView) findViewById(R.id.noaccount_button);
        cpfEditText = (EditText) findViewById(R.id.cpf_editText);
        passwordEditText = (EditText) findViewById(R.id.pass_editText);
        loginButton = (Button) findViewById(R.id.login_button);
        parentView =  findViewById(R.id.activity_login);
        final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();

        final Intent i = new Intent(LoginActivity.this,MainActivity.class);

        noaccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cpfEditText.setText("12345678011");
                passwordEditText.setText("123456");
            }
        });

        cpfEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(cpfEditText.getText().toString().length()==11)
                    passwordEditText.requestFocus();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideSoftKeyboard(LoginActivity.this);

                if(!areCredentialsOk()) {
                    return;
                }
                AsyncHttpClient client = new AsyncHttpClient();
                client.setEnableRedirects(true);
                client.setTimeout(6000);
                String url = ApplicationUtilities.URL+"/Authentication";
                //String uma = "https://api.coinmarketcap.com/v1/ticker?limit=10";
                RequestParams params = new RequestParams();
                params.add("cpf",cpfEditText.getText().toString());
                params.add("password",passwordEditText.getText().toString());
                lockInterface();
                client.post(url, params, new AsyncHttpResponseHandler() {
                    String responseString = "";
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        unlockInterface();

                        try {
                            responseString = new String(responseBody,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            Snackbar.make(parentView, "Error converting the response to string", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            e.printStackTrace();
                        }

                        if(responseString.equals("1001")) {
                            Snackbar.make(parentView, "Authentication error. Try typing your CPF and password again", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            return;
                        }

                        /*try{
                            cards = gson.fromJson(responseString, new TypeToken<ArrayList<CreditCard>>(){}.getType());
                            i.putExtra("cards",cards);
                        }catch (Exception e){
                            Log.d("Error","Error trying to get cards trough Gson: "+ e.getMessage());
                        }*/
                        try{
                            loginButton.setText("Connected");
                            user = gson.fromJson(responseString, new TypeToken<User>(){}.getType());
                            Log.d("TESTE","string: "+responseString);

                            try{
                                user.getCity();
                                i.putExtra("user",user);
                                startActivity(i);
                            }catch (Exception e){
                                Log.d("invalid user", e.getMessage());
                                Snackbar.make(parentView, "Error parsing the server response. (authenticating)", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                            }


                        }catch (Exception e){
                            Log.d("catch", e.getMessage());
                        }



                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        unlockInterface();
                        try {
                            responseString = new String(responseBody,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.d("statuscode",statusCode+"");
                        Snackbar.make(parentView, "Error - The app cannot connect to server. Please check your internet connection.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                    }
                });
            }
        });

    }

    private void lockInterface(){
        loginButton.setEnabled(false);
        loginButton.setText("Connecting");
    }

    private void unlockInterface(){
        loginButton.setEnabled(true);
        loginButton.setText("Login");
    }

    private boolean areCredentialsOk(){
        boolean result = true;
        String msg = "";
        if(cpfEditText.getText().toString().length()<=10){
            msg = "Error! CPF must have 11 characters.";
            result = false;
        }

        if(passwordEditText.getText().toString().length()<=5) {
            msg = "Error! The password is too short";
            result = false;
        }

        if(!msg.equals(""))
            Snackbar.make(parentView, msg, Snackbar.LENGTH_LONG).setAction("Action", null).show();

        return result;
    }

    @Override
    protected void onStart() {
        super.onStart();
        loginButton.setText("Login");
        passwordEditText.setText("");
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }
}
