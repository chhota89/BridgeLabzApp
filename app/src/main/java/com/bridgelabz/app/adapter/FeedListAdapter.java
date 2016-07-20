package com.bridgelabz.app.adapter;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bridgelabz.app.R;
import com.bridgelabz.app.app.AppController;
import com.bridgelabz.app.model.FeedItem;

import java.util.List;

/**
 * Created by eshvar289 on 17/7/16.
 */

public class FeedListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public FeedListAdapter(Context context,List<FeedItem> feedItems){
        this.context = context;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int position) {
        return feedItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView=layoutInflater.inflate(R.layout.feed_item,null);
        }
        if (imageLoader == null){
            imageLoader=AppController.getInstance().getImageLoader();
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timeStamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView statusMsg= (TextView) convertView.findViewById(R.id.txtStatusMsg);
        TextView url = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.profilePic);
        NetworkImageView feedImageView = (NetworkImageView) convertView.findViewById(R.id.feedImage1);

        FeedItem item= feedItems.get(position);
        name.setText(item.getName());

        //converting timestamp into x ago format
        CharSequence timeAgo= DateUtils.getRelativeTimeSpanString(
                Long.parseLong(item.getTimeStamp()),
                System.currentTimeMillis(),DateUtils.SECOND_IN_MILLIS);
        timeStamp.setText(timeAgo);

        //Check for empty status message
        if (!TextUtils.isEmpty(item.getStatus())){
            statusMsg.setText(item.getStatus());
            statusMsg.setVisibility(View.VISIBLE);
        }else{
            //status is empty remove from view
            statusMsg.setVisibility(View.GONE);
        }
        //Checking for null feed url
        if (item.getUrl() != null){
            url.setText(Html.fromHtml("<a href=\""+item.getUrl()+"\">"
                +item.getUrl()+"</a>"));
            //Making url clickable
            url.setMovementMethod(LinkMovementMethod.getInstance());
            url.setVisibility(View.VISIBLE);
        }else {
            //url is null ,remove from the view
            url.setVisibility(View.GONE);
        }
        //user profile pic
        profilePic.setImageUrl(item.getProfile(),imageLoader);

        //Feed image
        if (item.getImage() != null){
            feedImageView.setImageUrl(item.getImage(),imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView.setImageUrl(item.getImage(),imageLoader);
        }else{
            feedImageView.setVisibility(View.GONE);
        }
        return convertView;
    }
}
