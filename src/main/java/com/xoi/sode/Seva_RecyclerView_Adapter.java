package com.xoi.sode;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Seva_RecyclerView_Adapter extends RecyclerView.Adapter<Seva_RecyclerView_Adapter.ViewHolder>{

    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> price = new ArrayList<String>();
    private Context mContext;

    public Seva_RecyclerView_Adapter(ArrayList<String> name, ArrayList<String> price,Context mContext) {
        this.name = name;
        this.price = price;
        this.mContext = mContext;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sevalist_layout, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.name_txt.setText(name.get(i));
        viewHolder.price_txt.setText(price.get(i));
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name_txt, price_txt;
        LinearLayout parent_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_txt = itemView.findViewById(R.id.name_txt);
            price_txt = itemView.findViewById(R.id.price_txt);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
