package com.xoi.sode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Log_admin extends AppCompatActivity {


    EditText username_txt, password_txt;
    Button login_but;
    String username_str, password_str;
    ProgressDialog loading;
    String username, password, email, mode;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_admin);

        username_txt = (EditText)findViewById(R.id.username_txt);
        password_txt = (EditText)findViewById(R.id.password_txt);
        login_but = (Button)findViewById(R.id.login_but);


        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("Login")){
            if(sharedPreferences.getString("Login","").equals("A")){
                Intent i = new Intent(Log_admin.this,Admin_main.class);
                startActivity(i);
                finish();
            }
            else{
                Intent i = new Intent(Log_admin.this,User_main.class);
                startActivity(i);
                finish();
            }
        }
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_str = username_txt.getText().toString();
                password_str = password_txt.getText().toString();
                getUserDetails();

            }
        });
    }

    private void  getUserDetails() {

        loading = ProgressDialog.show(this,"Verifying user credentials","Please wait");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbz7ngw3YerftG8MHMVSL5fTIbdL_BeORT1lgz-9PA0DwzMGjZAN/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("No user found")){
                            Toast.makeText(Log_admin.this, "No user found", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            username = response.substring(0, response.indexOf(","));
                            response = response.substring(response.indexOf(",") + 1, response.length());
                            password = response.substring(0, response.indexOf(","));
                            response = response.substring(response.indexOf(",") + 1, response.length());
                            email = response.substring(0, response.indexOf(","));
                            response = response.substring(response.indexOf(",") + 1, response.length());
                            mode = response.substring(0, response.indexOf(","));
                            sharedPreferences.edit().putString("Username",username).apply();
                            sharedPreferences.edit().putString("Password",password).apply();
                            sharedPreferences.edit().putString("Email",email).apply();
                            sharedPreferences.edit().putString("Mode",mode).apply();
                            if(password_str.equals(password)){
                                if(mode.equals("A")){
                                    sharedPreferences.edit().putString("Login","A").apply();
                                    Intent i = new Intent(Log_admin.this,Admin_main.class);
                                    startActivity(i);
                                    finish();
                                }
                                else{
                                    sharedPreferences.edit().putString("Login","U").apply();
                                    Intent i = new Intent(Log_admin.this,User_main.class);
                                    startActivity(i);
                                    finish();
                                }
                            }
                            else{
                                Toast.makeText(Log_admin.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                parmas.put("action","getUserDetails");
                parmas.put("username",username_str);

                return parmas;
            }
        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
