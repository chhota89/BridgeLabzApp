package com.bridgelabz.app.utility;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.bridgelabz.app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bridgeit on 17/10/16.
 */

public class FileUtility {

    public static String saveToInternalStorage(Bitmap bitmapImage,Context context){

        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        String imagePath=Environment.getExternalStorageDirectory()+"/"+System.currentTimeMillis()+".jpg";
        File mypath=new File(imagePath);

        if(!mypath.exists()) {
            try {
                mypath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mypath.mkdir();
            mypath.mkdirs();
        }

        Log.i("FileUtility", "saveToInternalStorage: "+mypath.getAbsolutePath());

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "image123.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            return  b;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        return null;

    }


}
