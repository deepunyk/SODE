package com.xoi.sode;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserDetails extends AppCompatActivity {

    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> password = new ArrayList<>();
    private ArrayList<String> email = new ArrayList<>();
    private ArrayList<String> address = new ArrayList<>();
    private ArrayList<String> city = new ArrayList<>();
    ListView userDetails;
    ProgressDialog loader;
    ArrayAdapter<String> arrayAdapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        userDetails = (ListView)findViewById(R.id.userdetails_list);
        loader = new ProgressDialog(this);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        userDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sharedPreferences.edit().putString("username",name.get(position)).apply();
                sharedPreferences.edit().putString("password",password.get(position)).apply();
                sharedPreferences.edit().putString("email",email.get(position)).apply();
                sharedPreferences.edit().putString("address",address.get(position)).apply();
                sharedPreferences.edit().putString("city",city.get(position)).apply();
                Intent i = new Intent(UserDetails.this, UserDetails_selected.class);
                startActivity(i);
                finish();
            }
        });

        getUserDetails();
    }

    private void getUserDetails() {
        loader.setTitle("Collecting user details");
        loader.setMessage("Please wait...");
        loader.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loader.setCancelable(false);
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbz7ngw3YerftG8MHMVSL5fTIbdL_BeORT1lgz-9PA0DwzMGjZAN/exec?action=getAllUserDetails",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }

    private void parseItems(String jsonResposnce) {
        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("users");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                String name_json = jo.getString("name");
                name.add(name_json);
                String password_json = jo.getString("password");
                password.add(password_json);
                String email_json = jo.getString("email");
                email.add(email_json);
                String address_json = jo.getString("address");
                address.add(address_json);
                String city_json = jo.getString("city");
                city.add(city_json);
            }
            setListItem();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setListItem(){
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);
        userDetails.setAdapter(arrayAdapter);
        loader.dismiss();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UserDetails.this, Admin_main.class);
        startActivity(i);
        finish();
    }
}
