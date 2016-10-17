package com.bridgelabz.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;

import com.bridgelabz.app.R;
import com.bridgelabz.app.activity.ExoPlayer;
import com.bridgelabz.app.exoplayer.DashRendererBuilder;
import com.bridgelabz.app.exoplayer.DemoPlayer;
import com.bridgelabz.app.exoplayer.DemoUtil;
import com.bridgelabz.app.exoplayer.EventLogger;
import com.bridgelabz.app.exoplayer.WidevineTestMediaDrmCallback;
import com.google.android.exoplayer.VideoSurfaceView;

import java.util.List;

/**
 * Created by bridgeit on 8/9/16.
 */

public class VideoAdapter extends RecyclerView.Adapter<MyViewHolder> implements SurfaceHolder.Callback,
            DemoPlayer.Listener {
    private static final String TAG = VideoAdapter.class.getSimpleName();
    List<String> contentIdList;
        private LayoutInflater inflater;
        private DemoPlayer player;
        String userAgent;
        Context mContext;
        private MediaController mediaController;
        private long playerPosition;
        private EventLogger eventLogger;
        private boolean playerNeedsPrepare;
        private boolean autoPlay = true;
        private VideoSurfaceView surfaceView;
        private DashRendererBuilder dashRendererBuilder;
        LinearLayout linearLayout;
        List<String> contentUriList;

        public VideoAdapter(Context context, List<String> list, List<String> contentUriList,
                            DemoPlayer player,DashRendererBuilder dashRendererBuilder,LinearLayout linearLayout){
            userAgent= DemoUtil.getUserAgent(context);
            this.contentIdList =list;
            this.contentUriList=contentUriList;
            this.mContext=context;
            inflater = LayoutInflater.from(context);
            this.player=player;
            this.dashRendererBuilder=dashRendererBuilder;
            this.linearLayout=linearLayout;
            player.addListener(this);
            mediaController = new MediaController(context);
            //mediaController.setAnchorView(holder.root);
            mediaController.setMediaPlayer(player.getPlayerControl());
            mediaController.setEnabled(true);
            //prepareExoPlayer();
        }

        private void prepareExoPlayer(){
            if (player == null) {

               /* player = new DemoPlayer(new DashRendererBuilder(userAgent, contentUri.toString(), contentId,
                        new WidevineTestMediaDrmCallback(contentId)));*/
                dashRendererBuilder=new DashRendererBuilder(userAgent);
                player = new DemoPlayer(dashRendererBuilder);
                player.addListener(this);
                player.seekTo(playerPosition);
                playerNeedsPrepare = true;
                mediaController = new MediaController(mContext);
                //mediaController.setAnchorView(holder.root);
                mediaController.setMediaPlayer(player.getPlayerControl());
                mediaController.setEnabled(true);
                eventLogger = new EventLogger();
                eventLogger.startSession();
                player.addListener(eventLogger);
                player.setInfoListener(eventLogger);
                player.setInternalErrorListener(eventLogger);
                player.setPlayWhenReady(true);
            }
        }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_vedio,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if(ExoPlayer.vedioPlay && position== ExoPlayer.positionToPlay){
            try {
                ExoPlayer.vedioPlay=false;
                this.surfaceView = holder.surfaceView;
                dashRendererBuilder.setContentId(contentIdList.get(position));
                dashRendererBuilder.setUrl(contentUriList.get(position));
                dashRendererBuilder.setMediaDrmCallback(new WidevineTestMediaDrmCallback(contentIdList.get(position)));
                mediaController.setAnchorView(holder.root);
                player.seekTo(0);
                player.setSurface(holder.surfaceView.getHolder().getSurface());
                player.prepare();
            }catch (Exception exception){
                Log.e(TAG, "onBindViewHolder: ",exception );
            }
        }

        holder.root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    toggleControlsVisibility();
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentIdList.size();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (player != null) {
            player.setSurface(surfaceHolder.getSurface());
            maybeStartPlayback();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (player != null) {
            player.blockingClearSurface();
        }
    }

    @Override
    public void onStateChanged(boolean playWhenReady, int playbackState) {
        if (playbackState == com.google.android.exoplayer.ExoPlayer.STATE_ENDED) {
            showControls();
        }
    }

    @Override
    public void onError(Exception e) {
        playerNeedsPrepare = true;
        showControls();
    }

    @Override
    public void onVideoSizeChanged(int width, int height, float pixelWidthHeightRatio) {
        //shutterView.setVisibility(View.GONE);
        surfaceView.setVideoWidthHeightRatio(
                height == 0 ? 1 : (width * pixelWidthHeightRatio) / height);
    }

    private void maybeStartPlayback() {
        if (autoPlay && (player.getSurface().isValid()
                || player.getSelectedTrackIndex(DemoPlayer.TYPE_VIDEO) == DemoPlayer.DISABLED_TRACK)) {
            player.setPlayWhenReady(true);
            autoPlay = false;
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playerPosition = player.getCurrentPosition();
            player.release();
            player = null;
            eventLogger.endSession();
            eventLogger = null;
        }
    }

    private void preparePlayer(MyViewHolder holder,String contentId,String contentUri) {
        if (player == null) {

            player = new DemoPlayer(new DashRendererBuilder(userAgent, contentUri.toString(), contentId,
                    new WidevineTestMediaDrmCallback(contentId)));

            player.addListener(this);
            player.seekTo(playerPosition);
            playerNeedsPrepare = true;
            mediaController = new MediaController(mContext);
            mediaController.setAnchorView(holder.root);
            mediaController.setMediaPlayer(player.getPlayerControl());
            mediaController.setEnabled(true);
            eventLogger = new EventLogger();
            eventLogger.startSession();
            player.addListener(eventLogger);
            player.setInfoListener(eventLogger);
            player.setInternalErrorListener(eventLogger);
        }
        if (playerNeedsPrepare) {
            player.prepare();
            playerNeedsPrepare = false;
        }
        player.setSurface(holder.surfaceView.getHolder().getSurface());
        maybeStartPlayback();
    }

    private void showControls() {
        mediaController.show(0);
        //debugRootView.setVisibility(View.VISIBLE);
    }

    private void toggleControlsVisibility() {
        if (mediaController.isShowing()) {
            mediaController.hide();
            //debugRootView.setVisibility(View.GONE);
        } else {
            mediaController.show(0);
        }
    }
}
