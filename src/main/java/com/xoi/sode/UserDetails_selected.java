package com.xoi.sode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserDetails_selected extends AppCompatActivity {

    TextView username, password, email, address, city;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_selected);

        username = (TextView)findViewById(R.id.username_txt);
        password = (TextView)findViewById(R.id.password_txt);
        email = (TextView)findViewById(R.id.email_txt);
        address = (TextView)findViewById(R.id.address_txt);
        city = (TextView)findViewById(R.id.city_txt);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        username.setText(sharedPreferences.getString("username",""));
        password.setText(sharedPreferences.getString("password",""));
        email.setText(sharedPreferences.getString("email",""));
        address.setText(sharedPreferences.getString("address",""));
        city.setText(sharedPreferences.getString("city",""));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(UserDetails_selected.this, UserDetails.class);
        startActivity(i);
        finish();
    }
}
