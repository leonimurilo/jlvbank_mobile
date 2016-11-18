package com.example.leonim.picartaodecredito.core;

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
import android.widget.Button;
import com.loopj.android.http.*;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leonim.picartaodecredito.R;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText cpfEditText;
    private EditText passwordEditText;
    private View parentView;
    private AsyncHttpResponseHandler responseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        cpfEditText = (EditText) findViewById(R.id.cpf_editText);
        passwordEditText = (EditText) findViewById(R.id.pass_editText);
        loginButton = (Button) findViewById(R.id.login_button);
        parentView =  findViewById(R.id.activity_login);

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

        responseHandler = new AsyncHttpResponseHandler() {
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

                Intent i = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);

                Snackbar.make(parentView, "The sent cpf was: "+responseString, Snackbar.LENGTH_SHORT).setAction("Action", null).show();

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

                Snackbar.make(parentView, "Error - The app cannot connect to server. Please check your internet connection.", Snackbar.LENGTH_LONG).setAction("Action", null).show();

            }
        };


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!areCredentialsOk()) {
                    return;
                }
                AsyncHttpClient client = new AsyncHttpClient();
                client.setEnableRedirects(true);
                client.setTimeout(6000);
                String url = "http://10.0.2.2:8080//teste_war_exploded/teste";
                //String uma = "https://api.coinmarketcap.com/v1/ticker?limit=10";
                RequestParams params = new RequestParams();
                params.add("cpf",cpfEditText.getText().toString());
                lockInterface();
                client.post(url, params, responseHandler);
            }
        });

    }

    private void lockInterface(){
        loginButton.setEnabled(false);
        loginButton.setText("Connecting");
    }

    private void unlockInterface(){
        loginButton.setEnabled(true);
        loginButton.setText("Connected");
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
}
