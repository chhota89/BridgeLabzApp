package com.bridgelabz.app.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.utility.FileUtility;
import com.bridgelabz.app.view.CustomView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class CustomActivity extends AppCompatActivity {

    ImageView imageView;
    TextRecognizer textRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CustomView customView=(CustomView)findViewById(R.id.custom);
        imageView= (ImageView) findViewById(R.id.imageView4);
        textRecognizer = new TextRecognizer.Builder(CustomActivity.this).build();
        //customView.setDrawingCacheEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customView.setDrawingCacheEnabled(true);
                customView.buildDrawingCache();
                Bitmap bitmap=customView.getDrawingCache();

                String path= FileUtility.saveToInternalStorage(bitmap,getApplicationContext());

                Bitmap abc=FileUtility.loadImageFromStorage(path);
                Log.i("CustomActivity", "onClick: Path ... "+path);

                //customView.setShapeColor(getResources().getColor(R.color.colorPrimaryDark));
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Log.i("Custom activity", "onClick: "+bitmap.getWidth()+" "+bitmap.getHeight()+" "+bitmap.getDensity());
                imageView.setImageBitmap(abc);
               /* imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();


                bitmap=imageView.getDrawingCache();*/

                detectText(abc);

                /*customView.hideViewAnimation(getApplicationContext(), new CustomView.AnimationListner() {
                    @Override
                    public void animationFinish() {
                        Toast.makeText(CustomActivity.this,"Animation finised",Toast.LENGTH_LONG).show();
                    }
                });*/
                //customView.animateStart();

            }
        });

    }


    private void detectText(Bitmap bitmap){

        if (!textRecognizer.isOperational()) {
            Toast.makeText(CustomActivity.this,"Text Recolonization is not operational",Toast.LENGTH_LONG).show();
        }else{
            Frame frame=new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> sparseArray=textRecognizer.detect(frame);

            if(sparseArray!=null && sparseArray.size()>0) {
                Log.i("CustomActivity", "onCreate: " + sparseArray.get(0).getValue());
                Toast.makeText(CustomActivity.this, sparseArray.get(0).getValue(), Toast.LENGTH_LONG).show();
            }else
                Toast.makeText(CustomActivity.this,"Nothing detected",Toast.LENGTH_LONG).show();
        }
    }

}
