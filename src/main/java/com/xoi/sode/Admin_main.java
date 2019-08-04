package com.xoi.sode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

public class Admin_main extends AppCompatActivity {

    Button addUser,signOut, userDetails, sevaDetails;
    SharedPreferences sharedPreferences;
    long backPressedTime;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.car0, R.drawable.car1, R.drawable.car2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        sharedPreferences = this.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);

        addUser = (Button)findViewById(R.id.addUser_but);
        userDetails = (Button)findViewById(R.id.userDetails_but);
        sevaDetails = (Button)findViewById(R.id.sevaDetails_but);
        signOut = (Button)findViewById(R.id.signOut_but);
        carouselView = (CarouselView)findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Admin_main.this, AddUserAdmin.class);
                startActivity(i);
                finish();
            }
        });

        userDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Admin_main.this, UserDetails.class);
                startActivity(i);
                finish();
            }
        });

        sevaDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Admin_main.this, SevaDetails.class);
                startActivity(i);
                finish();
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().apply();
                Intent i = new Intent(Admin_main.this, Log_admin.class);
                startActivity(i);
                finish();
            }
        });
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

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
