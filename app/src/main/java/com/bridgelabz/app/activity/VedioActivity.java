package com.bridgelabz.app.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bridgelabz.app.R;
import com.kyleduo.switchbutton.SwitchButton;

public class VedioActivity extends AppCompatActivity {

    SwitchButton switchButton;
    VideoView videoView;
    MediaController mediaController;
    String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*switchButton=(SwitchButton)findViewById(R.id.sb_use_listener);
        videoView =(VideoView)findViewById(R.id.videoView1);

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    videoView.setVideoURI(Uri.parse(VideoURL));
                    videoView.start();
                }
                else{
                    Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media.mp4");
                    videoView.setVideoURI(uri);
                    videoView.start();
                }
            }
        });

        //Creating MediaController
        mediaController= new MediaController(this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        Uri uri=Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media.mp4");

        //Setting MediaController and URI, then starting the videoView
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();*/

    }

}
