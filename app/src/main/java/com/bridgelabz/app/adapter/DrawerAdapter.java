package com.bridgelabz.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bridgelabz.app.R;
import com.bridgelabz.app.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgeit on 11/6/16.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerViewHolder> {

    private List<UserInfo> list;
    private Context context;
    LayoutInflater inflater;

    public DrawerAdapter(List<UserInfo> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.adapter_details_layout, parent,false);
        return  new DrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        holder.firstName.setText(list.get(position).getFirstName());
        holder.lastName.setText(list.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder{
        TextView firstName,lastName;
        public DrawerViewHolder(View itemView) {
            super(itemView);
            firstName=(TextView)itemView.findViewById(R.id.firstName);
            lastName=(TextView)itemView.findViewById(R.id.lastName);
        }
    }
}
