package com.bridgelabz.app.utility;

import android.util.Log;

/**
 * Created by bridgeit on 19/7/16.
 */

public class NumberUtility {


    public NumberUtility(){
        super();
        int a=0;

    }

    public static int getRandom() {
        return 0 + (int) (Math.random() * ((Integer.MAX_VALUE - 0) + 1));
    }

    public static void getTime(long start,long endTime,String operation){
        Log.d("EscapeTime", "getTime: "+operation+" ........ :"+(endTime-start));
    }
}
