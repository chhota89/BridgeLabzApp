package com.bridgelabz.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bridgelabz.app.R;
import com.bridgelabz.app.app.AppController;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by bridgeit on 20/7/16.
 */

public class ImageLoadingAdapter extends RecyclerView.Adapter<ImageLoadingAdapter.ImageViewHolder> {

    ArrayList<String> list;
    public static final int GLIDE=0;
    public static final int VOLLEY=1;
    public static final int PICASSO=2;
    public static final int FRESCO=3;
    int library;
    Context context;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ImageLoadingAdapter(ArrayList<String> list, int library, Context context) {
        this.list = list;
        this.library=library;
        this.context=context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(library==GLIDE || library==PICASSO) {
            View viewUserInfo = inflater.inflate(R.layout.adapter_image_layout, parent, false);
            return new ImageViewHolder(viewUserInfo);
        }else if(library==VOLLEY){
            View viewUserInfo = inflater.inflate(R.layout.adapter_volley_layout, parent, false);
            return new VolleyViewHolder(viewUserInfo);
        }
        else{
            View viewUserInfo = inflater.inflate(R.layout.adapter_fresco_layout, parent, false);
            return new FrescoViewHolder(viewUserInfo);
        }
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        switch (library){
            case PICASSO:
                AppController.getPicasso().with(context).load(list.get(position)).into(holder.imageView);
                break;
            case GLIDE:
                Glide.with(context).load(list.get(position)).thumbnail(0.5f).crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.imageView);
                break;
            case VOLLEY:
                VolleyViewHolder volleyViewHolder=(VolleyViewHolder)holder;
                volleyViewHolder.networkImageView.setImageUrl(list.get(position),imageLoader);
                break;

            case FRESCO:
                FrescoViewHolder frescoViewHolder=(FrescoViewHolder) holder;
                frescoViewHolder.frescoImage.setImageURI(Uri.parse(list.get(position)));
                break;

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return library;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public class FrescoViewHolder extends ImageViewHolder {
        public SimpleDraweeView frescoImage;
        public FrescoViewHolder(View itemView) {
            super(itemView);
            frescoImage=(SimpleDraweeView)itemView.findViewById(R.id.frescoImage);
        }
    }

    public class VolleyViewHolder extends ImageViewHolder {
        public NetworkImageView networkImageView;
        public VolleyViewHolder(View itemView) {
            super(itemView);
            networkImageView=(NetworkImageView)itemView.findViewById(R.id.networkImageView);
        }
    }
}
