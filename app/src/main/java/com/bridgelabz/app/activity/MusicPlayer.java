package com.bridgelabz.app.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;

import com.bridgelabz.app.R;

public class MusicPlayer extends AppCompatActivity {
    MediaPlayer mPlayer2;
    int media_length;
    SeekBar mSeekBar;
    Handler seekHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSeekBar=(SeekBar)findViewById(R.id.seekBar);

        //Add Seekbar Listener
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(mPlayer2!=null) {
                    mPlayer2.seekTo(seekBar.getProgress());
                    mPlayer2.start();
                }
                else
                    playSound();

            }
        });
        seekHandler = new Handler();

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
        mSeekBar.setProgress(mPlayer2.getCurrentPosition());
        seekHandler.postDelayed(run, 1000);
    }

    //This method is trigger when uplay button is pressed
    public void playAudio(View view){
        if(mPlayer2!=null)
        {
            mPlayer2.release();
            mPlayer2=null;
        }
        playSound();
    }

    //Play audio
    public void playSound(){
        mPlayer2= MediaPlayer.create(this, R.raw.sample_audio);
        mPlayer2.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mSeekBar.setMax(mPlayer2.getDuration());
                seekUpdation();
            }
        });
        mPlayer2.start();
    }

    //Pause audio when user press pause button
    public void puaseAudio(View view){
        if(mPlayer2!=null) {
            mPlayer2.pause();
            media_length=mPlayer2.getCurrentPosition();
        }
    }

    //Resume audio when user press resume button
    public void resumeAudio(View view){
        if(mPlayer2!=null)
            mPlayer2.seekTo(media_length);
        mPlayer2.start();
    }

    //stop audio when user press stop button
    public void stopAudio(View view){
        if(mPlayer2!=null)
            mPlayer2.stop();
    }

}
