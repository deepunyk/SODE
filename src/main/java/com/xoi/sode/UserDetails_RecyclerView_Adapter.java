package com.xoi.sode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class UserDetails_RecyclerView_Adapter extends RecyclerView.Adapter<UserDetails_RecyclerView_Adapter.ViewHolder>{

    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<String> password = new ArrayList<String>();
    private ArrayList<String> address = new ArrayList<String>();
    private ArrayList<String> city = new ArrayList<String>();

    private Context mContext;

    public UserDetails_RecyclerView_Adapter(ArrayList<String> name,ArrayList<String> password,ArrayList<String> email,ArrayList<String> address,ArrayList<String> city,Context mContext) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.city = city;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userdetails_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name_txt.setText(name.get(i));
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("com.xoi.smvitm", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("username",name.get(i)).apply();
                sharedPreferences.edit().putString("password",password.get(i)).apply();
                sharedPreferences.edit().putString("email",email.get(i)).apply();
                sharedPreferences.edit().putString("address",address.get(i)).apply();
                sharedPreferences.edit().putString("city",city.get(i)).apply();
                Intent i = new Intent(mContext, UserDetails_selected.class);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name_txt;
        ConstraintLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.username_txt);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
