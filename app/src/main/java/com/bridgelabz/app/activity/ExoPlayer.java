package com.bridgelabz.app.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.CaptioningManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;

import com.bridgelabz.app.R;
import com.bridgelabz.app.adapter.VedeoListAdapter;
import com.bridgelabz.app.adapter.VideoAdapter;
import com.bridgelabz.app.exoplayer.DashRendererBuilder;
import com.bridgelabz.app.exoplayer.DemoPlayer;
import com.bridgelabz.app.exoplayer.DemoUtil;
import com.bridgelabz.app.exoplayer.EventLogger;
import com.bridgelabz.app.exoplayer.LoadVedioLink;
import com.bridgelabz.app.exoplayer.WidevineTestMediaDrmCallback;
import com.google.android.exoplayer.VideoSurfaceView;
import com.google.android.exoplayer.text.CaptionStyleCompat;
import com.google.android.exoplayer.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ExoPlayer extends Activity /*implements SurfaceHolder.Callback,
        DemoPlayer.Listener*/ {

    private View shutterView;
    private VideoSurfaceView surfaceView;
    FrameLayout root;
    private DemoPlayer player;
    private MediaController mediaController;
    private long playerPosition;
    private EventLogger eventLogger;
    private boolean playerNeedsPrepare;
    private boolean autoPlay = true;
    String userAgent;
    private static final String TAG = ExoPlayer.class.getSimpleName();

    public static boolean vedioPlay=true;
    public static int positionToPlay=0;
    LinearLayout linearLayout;


    String contentUri = "http://www.youtube.com/api/manifest/dash/id/3aa39fa2cc27967f/source/youtube?"
            + "as=fmp4_audio_clear,fmp4_sd_hd_clear&sparams=ip,ipbits,expire,as&ip=0.0.0.0&ipbits=0&"
            + "expire=19000000000&signature=7181C59D0252B285D593E1B61D985D5B7C98DE2A."
            + "5B445837F55A40E0F28AACAA047982E372D177E2&key=ik0";
    String contentId = "3aa39fa2cc27967f";
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private VideoAdapter adapter;
    private DashRendererBuilder dashRendererBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exo_player);

        List<String> contentIdList = new ArrayList<>();
        List<String> contentUriList = new ArrayList<>();

        contentIdList.add(contentId);
        contentIdList.add(contentId);
        contentIdList.add(contentId);
        contentIdList.add(contentId);
        contentIdList.add(contentId);
        contentIdList.add(contentId);
        contentIdList.add(contentId);
        contentIdList.add(contentId);
        contentIdList.add(contentId);

        contentUriList.add(contentUri);
        contentUriList.add(contentUri);
        contentUriList.add(contentUri);
        contentUriList.add(contentUri);
        contentUriList.add(contentUri);
        contentUriList.add(contentUri);
        contentUriList.add(contentUri);
        contentUriList.add(contentUri);
        contentUriList.add(contentUri);


        /*ListView listView=(ListView)findViewById(R.id.listView);
        VedeoListAdapter vedeoListAdapter=new VedeoListAdapter(ExoPlayer.this,contentIdList,contentUriList);
        listView.setAdapter(vedeoListAdapter);*/

        userAgent= DemoUtil.getUserAgent(ExoPlayer.this);
        prepareExoPlayer();
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        linearLayout=(LinearLayout)findViewById(R.id.activity_exo_player);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new VideoAdapter(ExoPlayer.this, contentIdList, contentUriList, player, dashRendererBuilder,linearLayout);
        recyclerView.setAdapter(adapter);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //Call your method here for next set of data

                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    Rect rect = new Rect();
                    linearLayoutManager.findViewByPosition(position).getGlobalVisibleRect(rect);

                    int lastPostion = linearLayoutManager.findLastVisibleItemPosition();
                    Rect lastRect = new Rect();
                    linearLayoutManager.findViewByPosition(lastPostion).getGlobalVisibleRect(lastRect);


                    if(rect.height()>lastRect.height()){
                        if(positionToPlay != position){
                            vedioPlay=true;
                            positionToPlay=position;
                            adapter.notifyItemChanged(position);
                        }
                    }else
                    if(positionToPlay != lastPostion){
                        vedioPlay=true;
                        positionToPlay=lastPostion;
                        adapter.notifyItemChanged(lastPostion);
                    }

                    Log.i(TAG, "onScrollStateChanged: rect .............  ......."+rect.height()+"  "+rect.width() );
                    Log.i(TAG, "onScrollStateChanged: rect .............  ......."+lastRect.height()+"  "+lastRect.width() );

                }
            }
        });
    }

    private void prepareExoPlayer(){
        if (player == null) {

               /* player = new DemoPlayer(new DashRendererBuilder(userAgent, contentUri.toString(), contentId,
                        new WidevineTestMediaDrmCallback(contentId)));*/
            dashRendererBuilder=new DashRendererBuilder(userAgent);
            player = new DemoPlayer(dashRendererBuilder);
            player.seekTo(playerPosition);
            playerNeedsPrepare = true;

            eventLogger = new EventLogger();
            eventLogger.startSession();
            player.addListener(eventLogger);
            player.setInfoListener(eventLogger);
            player.setInternalErrorListener(eventLogger);
            player.setPlayWhenReady(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        positionToPlay=0;
        vedioPlay=true;
        releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
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

        /*DemoUtil.setDefaultCookieManager();

        shutterView = findViewById(R.id.shutter);

        surfaceView = (VideoSurfaceView) findViewById(R.id.surface_view);
        surfaceView.getHolder().addCallback(this);
        root = (FrameLayout) findViewById(R.id.activity_exo_player);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    toggleControlsVisibility();
                }
                return true;
            }
        });

        mediaController = new MediaController(this);
        mediaController.setAnchorView(root);

        new LoadVedioLink(){

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                contentUri=s;
                if(contentUri!=null)
                    preparePlayer();
            }
        }.execute(contentId);*/


/*    private void toggleControlsVisibility() {
        if (mediaController.isShowing()) {
            mediaController.hide();
            //debugRootView.setVisibility(View.GONE);
        } else {
            mediaController.show(0);
        }
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
    public void onResume() {
        super.onResume();
        //preparePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
            releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    private void preparePlayer() {
        if (player == null) {
            player = new DemoPlayer(getRendererBuilder());
            player.addListener(this);
            player.seekTo(playerPosition);
            playerNeedsPrepare = true;
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
        player.setSurface(surfaceView.getHolder().getSurface());
        maybeStartPlayback();
    }


    private void showControls() {
        mediaController.show(0);
        //debugRootView.setVisibility(View.VISIBLE);
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

    private DemoPlayer.RendererBuilder getRendererBuilder() {
        String userAgent = DemoUtil.getUserAgent(this);
        return new DashRendererBuilder(userAgent, contentUri.toString(), contentId,
                new WidevineTestMediaDrmCallback(contentId));

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
        shutterView.setVisibility(View.GONE);
        surfaceView.setVideoWidthHeightRatio(
                height == 0 ? 1 : (width * pixelWidthHeightRatio) / height);
    }*/

}
