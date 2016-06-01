package com.bridgelabz.app.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by bridgelabz5 on 25/5/16.
 */
public class AlaramBroadcastReciver extends BroadcastReceiver {
    private String TAG="AlaramBroadcastReciver";
    public AlaramBroadcastReciver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //MediaPlayer mp;
        //mp=MediaPlayer.create(context, android.R.raw.sample_audio);
        Toast.makeText(context,"Alaram is started",Toast.LENGTH_LONG).show();
        Log.i(TAG, "onReceive: +This is alaram");
    }
}
