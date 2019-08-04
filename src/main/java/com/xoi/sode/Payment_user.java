package com.xoi.sode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

public class Payment_user extends AppCompatActivity {

    ProgressDialog loader;
    private ArrayList<String> name = new ArrayList<>();
    private ArrayList<String> price = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_user);
        loader = new ProgressDialog(this);
        getSevas();
    }

    private void getSevas() {
        loader.setTitle("Collecting seva details");
        loader.setMessage("Please wait...");
        loader.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loader.setCancelable(false);
        loader.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.google.com/macros/s/AKfycbxufJ2ccpC_LkcRqME7le-6n5XyQPFDV2AxU52YzyF7rz4hnLrZ/exec?action=getAllSevas",
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
            JSONArray jarray = jobj.getJSONArray("seva");

            for (int i = 0; i < jarray.length(); i++) {
                JSONObject jo = jarray.getJSONObject(i);
                String name_json = jo.getString("name");
                name.add(name_json);
                String price_json = jo.getString("price");
                price.add(price_json);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent i;
        i = new Intent(Payment_user.this, User_main.class);
        startActivity(i);
        finish();
    }
}
