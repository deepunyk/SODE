package com.xoi.sode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class User_main extends AppCompatActivity {

    Button sevaDetails_but, signOut_but, pay_but;
    SharedPreferences sharedPreferences;
    long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        sevaDetails_but = (Button)findViewById(R.id.sevaDetails_but);
        signOut_but = (Button)findViewById(R.id.signOut);
        pay_but = (Button)findViewById(R.id.pay_but);

        sevaDetails_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_main.this,SevaDetails.class);
                startActivity(i);
                finish();
            }
        });

        pay_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User_main.this,Payment_user.class);
                startActivity(i);
                finish();
            }
        });

        signOut_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent i = new Intent(User_main.this, Log_admin.class);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
        }
        else{
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}
