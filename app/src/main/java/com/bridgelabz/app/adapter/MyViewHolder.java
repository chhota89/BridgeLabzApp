package com.bridgelabz.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.bridgelabz.app.R;
import com.google.android.exoplayer.VideoSurfaceView;

/**
 * Created by bridgeit on 15/9/16.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{
    public final FrameLayout root;
    public VideoSurfaceView surfaceView;

    public MyViewHolder(View itemView) {
        super(itemView);
        surfaceView= (VideoSurfaceView) itemView.findViewById(R.id.surface_view);
        root = (FrameLayout) itemView.findViewById(R.id.activity_exo_player);
        //shutterView = itemView.findViewById(R.id.shutter);
    }
}
