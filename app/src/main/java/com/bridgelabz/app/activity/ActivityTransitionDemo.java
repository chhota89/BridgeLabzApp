package com.bridgelabz.app.activity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.google.android.gms.plus.PlusShare;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.File;

public class ActivityTransitionDemo extends AppCompatActivity implements Animation.AnimationListener{
    private static final int PHOTO_PICKER = 200;
    private static final int SHARE_GOOGLE_PLUS = 201;
    private static final String TAG = "ActivityTranstionDemo";
    ExitActivityTransition exitTransition;
    private SwitchButton mListenerSb;
    Animation animFadeIn, animFadeOut;

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_transition);

        animFadeIn= AnimationUtils.loadAnimation(ActivityTransitionDemo.this,R.anim.fade_in);
        animFadeOut= AnimationUtils.loadAnimation(ActivityTransitionDemo.this,R.anim.fade_out);
        animFadeOut.setAnimationListener(this);
        animFadeIn.setAnimationListener(this);

        ActivityTransition.with(getIntent()).to(findViewById(R.id.image)).start(savedInstanceState);
        mListenerSb = (SwitchButton) findViewById(R.id.sb_use_listener);
        image=(ImageView)findViewById(R.id.image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.image)).start(savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", new View.OnClickListener(){

                            @Override
                            public void onClick(View v) {
                                Toast.makeText(ActivityTransitionDemo.this,"Snack bar is pressed",Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });


        // work with listener
        mListenerSb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                    image.startAnimation(animFadeIn);
                else
                    image.startAnimation(animFadeOut);

                image.setVisibility(isChecked ? View.VISIBLE : View.INVISIBLE);
            }
        });

        Button button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File filePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "IPL" + "/" + "GujaratLions" + "/" + "Aaron Finch.png");
                Uri imageUri = Uri.fromFile(filePath);

                String fnm = "zaheer"; //  this is image file name
                String PACKAGE_NAME = getApplicationContext().getPackageName();
                Uri path = Uri.parse(PACKAGE_NAME+":drawable/"+fnm);
                int imgId = getResources().getIdentifier(imageUri.toString() , null, null);
                ImageView imageview = (ImageView) findViewById(R.id.imageView3);
                //imageview.setImageBitmap(BitmapFactory.decodeResource(getResources(),imgId));

                imageview.setImageBitmap(BitmapFactory.decodeFile(filePath.getAbsolutePath()));


                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.setType("image");
                //Target whatsapp:
                shareIntent.setPackage("com.whatsapp");
                //Add text and then Image URI
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Send whats app frete  errrrrrrrrrrre \n" + " dfuiyiuy fdfd");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                try {
                    startActivity(shareIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ActivityTransitionDemo.this,"Whatsapp have not been installed.",Toast.LENGTH_LONG).show();
                }

            }
        });


        Button googlePlusShare=(Button)findViewById(R.id.googlePlusShare);
        googlePlusShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phtopicker=new Intent(Intent.ACTION_PICK);
                phtopicker.setType("image/*");
                startActivityForResult(phtopicker,PHOTO_PICKER);
                PlusShare.Builder share = new PlusShare.Builder(ActivityTransitionDemo.this);

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PHOTO_PICKER) {
            if(resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                ContentResolver cr = this.getContentResolver();
                String mime = cr.getType(selectedImage);

                PlusShare.Builder share = new PlusShare.Builder(this);
                share.setText("hello everyone!");
                share.addStream(selectedImage);
                share.setType(mime);
                try {
                    startActivityForResult(share.getIntent(), SHARE_GOOGLE_PLUS);
                }catch (Exception exception){
                    //No activity found to handle this intent.
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
