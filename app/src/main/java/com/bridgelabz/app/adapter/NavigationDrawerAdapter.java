package com.bridgelabz.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bridgelabz.app.R;
import com.bridgelabz.app.model.NavDrawerItem;

import java.util.List;

/**
 * Created by eshvar289 on 16/7/16.
 */

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyDrawerViewHolder> {
    List<NavDrawerItem> data;/* = Collections.emptyList();*/
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row,parent,false);
        return new MyDrawerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyDrawerViewHolder holder, int position) {
        NavDrawerItem current =data.get(position);
        holder.title.setText(current.getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyDrawerViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        MyDrawerViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.nav_title);
        }
    }
}
