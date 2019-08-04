package com.xoi.sode;

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

public class AddUserAdmin extends AppCompatActivity {


    EditText username_txt, email_txt, password_txt, confirmPassword_txt, address_txt, city_txt;
    String username, email, password, confirmPassword, address, city, mode = "U";
    Button register_but;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_admin);

        sharedPreferences = getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        username_txt = (EditText)findViewById(R.id.username_txt);
        email_txt = (EditText)findViewById(R.id.email_txt);
        password_txt = (EditText)findViewById(R.id.password_txt);
        confirmPassword_txt = (EditText)findViewById(R.id.confirmpass_txt);
        address_txt = (EditText)findViewById(R.id.address_txt);
        city_txt = (EditText)findViewById(R.id.city_txt);
        register_but = (Button)findViewById(R.id.register_but);



        register_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = username_txt.getText().toString();
                email = email_txt.getText().toString();
                password = password_txt.getText().toString();
                confirmPassword = confirmPassword_txt.getText().toString();
                address = address_txt.getText().toString();
                city = city_txt.getText().toString();

                if(password.equals(confirmPassword)) {
                    createUser();
                }
                else{
                    Toast.makeText(AddUserAdmin.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void createUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbz7ngw3YerftG8MHMVSL5fTIbdL_BeORT1lgz-9PA0DwzMGjZAN/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Success")) {
                            Toast.makeText(AddUserAdmin.this, "New user added", Toast.LENGTH_SHORT).show();
                            Intent go = new Intent(AddUserAdmin.this, Admin_main.class);
                            startActivity(go);
                            finish();
                        }
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

                parmas.put("action", "createNewUser");
                parmas.put("Username", username);
                parmas.put("Email", email);
                parmas.put("Password", password);
                parmas.put("Address", address);
                parmas.put("City", city);
                parmas.put("Mode", "U");
                return parmas;
            }
        };
        int socketTimeOut = 50000;
        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddUserAdmin.this, Admin_main.class);
        startActivity(i);
        finish();
    }
}
