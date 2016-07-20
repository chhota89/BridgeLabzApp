package com.bridgelabz.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bridgelabz.app.R;
import com.bridgelabz.app.model.UserInfo;

import java.util.ArrayList;

public class HeterogenousAdapter extends RecyclerView.Adapter<HeterogenousAdapter.MyViewHolder>{

     ArrayList<Object> list;
      final int  IMAGE_TYPE=0,USER_INFO_TYPE=1;
    public  HeterogenousAdapter(ArrayList<Object> list){
        this.list=list;
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,list.size());
    }

    public void addItem(String s, int edit_position) {
        Object obj=list.get(edit_position);
        if(obj instanceof UserInfo){
            String capital=((UserInfo) obj).getLastName();
            list.remove(edit_position);
            list.add(edit_position,new UserInfo(s,capital));
        }

        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        MyViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class UserInfoViewHolder extends  MyViewHolder{
        TextView firstName,LastName;
        UserInfoViewHolder(View itemView) {
            super(itemView);
            firstName=(TextView)itemView.findViewById(R.id.firstName);
            LastName=(TextView)itemView.findViewById(R.id.lastName);
        }
    }

    private class ImageViewHolder extends  MyViewHolder{
        ImageView imageView;
        ImageViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType ==USER_INFO_TYPE){
            View viewUserInfo=inflater.inflate(R.layout.adapter_details_layout,parent,false);
            viewHolder=new UserInfoViewHolder(viewUserInfo);
        }else {
            View viewUserInfo=inflater.inflate(R.layout.adapter_image_layout,parent,false);
            viewHolder=new ImageViewHolder(viewUserInfo);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(holder.getItemViewType()== USER_INFO_TYPE){
            UserInfoViewHolder userInfoViewHolder=(UserInfoViewHolder)holder;
            UserInfo userInfo=(UserInfo)list.get(position);
            userInfoViewHolder.firstName.setText(userInfo.getFirstName());
            userInfoViewHolder.LastName.setText(userInfo.getLastName());
        }else {
            ImageViewHolder imageViewHolder=(ImageViewHolder)holder;
            String imageName=(String) list.get(position);

            ///imageViewHolder.imageView.setImageDrawable;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position) instanceof UserInfo)
            return USER_INFO_TYPE;
        else if(list.get(position) instanceof String)
            return  IMAGE_TYPE;
        else
            return -1;
    }
}
