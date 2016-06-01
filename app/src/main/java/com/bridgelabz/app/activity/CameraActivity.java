package com.bridgelabz.app.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bridgelabz.app.R;

import java.io.File;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
    public final int CAMERA_CONSTANT=255;
    public final int GALLERY_CODE=257;
    public final int PIC_CROP=256;
    Uri mCameraSaveImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void galleryClick(View view){
        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickIntent, GALLERY_CODE);
    }

    public void camreaClick(View view){
        try {
            File file=new File(String.valueOf(getCacheDir()));
            mCameraSaveImage=Uri.fromFile(file);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,mCameraSaveImage);
            startActivityForResult(intent, CAMERA_CONSTANT);
        }catch (Exception exception){
            Toast.makeText(CameraActivity.this,"Camera is not supported on this device.",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode==CAMERA_CONSTANT){
                cropImage();
            }
            else if(requestCode==GALLERY_CODE){
                mCameraSaveImage=data.getData();
                cropImage();
            }
            else if(requestCode==PIC_CROP){
                ImageView imageView=(ImageView)findViewById(R.id.image);
                Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }
        }else {
            Toast.makeText(CameraActivity.this,"Unable to capture Image",Toast.LENGTH_LONG).show();
        }
    }

    //Crop Image
    public void cropImage(){
        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //set cropIntent  as image type.
        cropIntent.setType("image/*");
        //Check for crop intent
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(cropIntent, 0);
        if(list.size()==0){
            Toast.makeText(CameraActivity.this,"Crop intent is not present.",Toast.LENGTH_LONG).show();
            return;
        }
        //set Data path
        cropIntent.setData(mCameraSaveImage);
        //cropIntent.setData(mCameraSaveImage);
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //put media store path
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraSaveImage);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, PIC_CROP);
    }
}
