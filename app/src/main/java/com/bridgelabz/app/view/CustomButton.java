package com.bridgelabz.app.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

/**
 * Created by bridgeit on 30/9/16.
 */

public class CustomButton extends Button {

    private  Paint mBackgroundColorPaint,mBackgroundBorder;
    private Paint textPaint;
    String mText;
    private float mTextSize;
    private float animationValue;
    boolean start=true;

    private static final String TAG=CustomButton.class.getSimpleName();


    public CustomButton(Context context) {
        super(context);

        mText = "";
        mTextSize=50.0f;
        animationValue=0;

        //Background paint
        mBackgroundColorPaint = new Paint();
        mBackgroundColorPaint.setAntiAlias(true);
        mBackgroundColorPaint.setStyle(Paint.Style.FILL);
        mBackgroundColorPaint.setColor(getResources().getColor(android.R.color.darker_gray));

        mBackgroundBorder = new Paint();
        mBackgroundBorder.setAntiAlias(true);
        mBackgroundBorder.setStyle(Paint.Style.FILL);
        mBackgroundBorder.setColor(getResources().getColor(android.R.color.darker_gray));

        //paint for text
        textPaint = new Paint();
        textPaint.setColor(getResources().getColor(android.R.color.black));
        textPaint.setAntiAlias(true);
        textPaint.setStrokeWidth(7);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeJoin(Paint.Join.ROUND);
        textPaint.setStrokeCap(Paint.Cap.ROUND);
        textPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Log.i(TAG, "onDraw: "+canvas.getWidth()+" "+getHeight());
        canvas.drawPaint(mBackgroundColorPaint);
        canvas.drawText(mText,canvas.getWidth()/2-mTextSize/2,canvas.getHeight()/2 , textPaint);
        //Log.i(TAG, "Right ------>"+canvas.getWidth()*animationValue);
        if(start)
            canvas.drawRect(0,0,canvas.getWidth()*animationValue,getWidth()/8,mBackgroundBorder);
        else
            canvas.drawRect(canvas.getWidth()*animationValue,0,canvas.getWidth(),canvas.getWidth()/8,mBackgroundBorder);
    }

    public void setText(String text){
        this.mText=text;
        invalidate();
    }

    public void setBackgroundColor(int color){
        this.mBackgroundColorPaint.setColor(color);
        invalidate();
    }

    public String getText(){
        return mText;
    }

    public void executeAnimation(boolean start,int startColor,int endColor){
        // Create a new value animator that will use the range 0 to 1
        ValueAnimator animator;

        this.start=start;

        animator= ValueAnimator.ofFloat(0, 1);

        // It will take 5000ms for the animator to go from 0 to 1
        animator.setDuration(2000);

        // Callback that executes on animation steps.
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animationValue=((Float) (animation.getAnimatedValue())).floatValue();

                if(animationValue<=1.0 && animationValue>0.0) {
                    Log.i(TAG, "onAnimationUpdate: .... " + animationValue);
                    invalidate();
                }
            }
        });

        /*animator.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                Log.i(TAG, "onAnimationEnd: I ended.......................");
            }
        });*/

        animator.start();

        if(start){

            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), startColor, endColor);

            colorAnimation.setDuration(2000);

            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    int newColor = (int) animator.getAnimatedValue();
                    mBackgroundColorPaint.setColor(newColor);
                    //viewToAnimate.setBackgroundColor(newColor);
                }
            });
            colorAnimation.start();
        }

    }
}
