package com.bridgelabz.app.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.bridgelabz.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bridgeit on 25/8/16.
 */

public class CustomView extends View {

    private static final  int PRECISION=30;
    private static final int SIZE_PRECISION=10;

    private String[] shapeValues = { "square", "circle" };
    private int currentShapeIndex = 0;

    private static final String TAG = "CustomView";
    private final Paint mBackgroundColorPaint = new Paint();
    private int mStrokeWidth=50;

    private int shapeColor;
    private boolean displayShapeName,animationStart=false;
    int size=10;
    float aCoordinates[] = {0f, 0f};

    Path drawPath;
    Paint drawPaint;
    PathMeasure pathMeasure;

    List<Point> pointList,originalData;
    StringBuilder pathBuilder;

    Bitmap bitmap;

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet arrts){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(arrts, R.styleable.CustomView, 0, 0);
        // Extract custom attributes into member variables
        try {
            shapeColor = a.getColor(R.styleable.CustomView_shapeColor, Color.BLACK);
            displayShapeName = a.getBoolean(R.styleable.CustomView_displayShapeName, false);
        } finally {
            // TypedArray objects are shared and must be recycled.
            a.recycle();
        }

        mBackgroundColorPaint.setAntiAlias(true);
        mBackgroundColorPaint.setStyle(Paint.Style.FILL);
        //mBackgroundColorPaint.setStrokeWidth(mStrokeWidth);
        mBackgroundColorPaint.setColor(shapeColor);

        drawPath = new Path();

        drawPaint = new Paint();

        drawPaint.setColor(shapeColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(22);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.BEVEL);
        drawPaint.setStrokeCap(Paint.Cap.SQUARE);
        drawPaint.setTextSize(200.0f);
        //pathMeasure=new PathMeasure(drawPath,false);

        pointList=new ArrayList<>();
        originalData=new ArrayList<>();

        pathBuilder=new StringBuilder();

        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.hand);
    }

    private void loadData(){

        originalData.add(new Point(395,385));
        originalData.add(new Point(410,430));
        originalData.add(new Point(415,385));
        originalData.add(new Point(395,385));
    }


    public void setShapeColor(int shapeColor) {
        this.shapeColor = shapeColor;
        //init(null);
        mBackgroundColorPaint.setColor(this.shapeColor);
        invalidate();
        //requestLayout();
    }

    public void setDisplayShapeName(boolean displayShapeName) {
        this.displayShapeName = displayShapeName;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: Canvas height and width "+canvas.getHeight()+" "+canvas.getWidth());

        /*switch (currentShapeIndex){
            case 0: canvas.drawRect(10, 10, 360 , 360, mBackgroundColorPaint);
                break;
            case 1: canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,200,mBackgroundColorPaint);
                break;
        }

        //canvas.drawRect(size, size, 360+size , 360+size, mBackgroundColorPaint);
        //size+=10;*/

        //canvas.drawText("A B C D e f 1 2 3",50,360,drawPaint);

        //canvas.drawBitmap(bitmap, new Matrix(), new Paint());
        canvas.drawPath(drawPath, drawPaint);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec)/2;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec)/2;


        Log.d(TAG, "onMeasure: widthMode: "+widthMode+" widthSize: "+widthSize+" heightMode:"+heightMode+" heightSize:"+heightSize);

        if(widthMode==MeasureSpec.EXACTLY)
            Log.i(TAG, "onMeasure: Measure spec exactly");
        else if(widthMode ==MeasureSpec.AT_MOST)
            Log.i(TAG, "onMeasure: Measure spec AT_MOST");

        int shapeWidth=400,shapeHeight=400,textYOffset=10;
        boolean displayShapeName=false;

        // Defines the extra padding for the shape name text
        int textPadding = 10;
        int contentWidth = shapeWidth;

        // Resolve the width based on our minimum and the measure spec
        int minw = contentWidth + getPaddingLeft() + getPaddingRight();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 0);

        Log.i(TAG, "onMeasure: Minw "+minw+" w "+w);

        // Ask for a height that would let the view get as big as it can
        int minh = shapeHeight + getPaddingBottom() + getPaddingTop();
        if (displayShapeName) {
            minh += textYOffset + textPadding;
        }
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        // Calling this method determines the measured width and height
        // Retrieve with getMeasuredWidth or getMeasuredHeight methods later

        Log.i(TAG, "onMeasure: w and h"+w+" "+h);
        setMeasuredDimension(w, h);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.d(TAG, "onTouchEvent() called with: event = [" + event + "]");
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                //pointList.add(new Point(Math.round(event.getX()),Math.round(event.getY())));
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                pointList.add(new Point(Math.round(event.getX()),Math.round(event.getY())));
                break;
            case MotionEvent.ACTION_UP:
                //pathMeasure=new PathMeasure(drawPath,false);
                for(int i=1;i<pointList.size();i+=5){
                    //pathMeasure.getPosTan(i, aCoordinates, null);
                    pathBuilder=pathBuilder.append("X:"+pointList.get(i).x+" Y:"+pointList.get(i).y+" -> ");
                }
                Log.i(TAG, "onDraw: ..... "+pathBuilder);
                //pathBuilder.delete(0,pathBuilder.length());
                break;
            default:
                return false;
        }
        invalidate();

        return true;
    }

    public interface AnimationListner{
        void animationFinish();
    }

    public void hideViewAnimation(Context context, final AnimationListner animationListner){
        /*final ObjectAnimator progressBarAnimator = ObjectAnimator.ofFloat(this, "animateProgress", start, end);
        progressBarAnimator.setDuration(1500);
        //		progressBarAnimator.setInterpolator(new AnticipateOvershootInterpolator(2f, 1.5f));
        progressBarAnimator.setInterpolator(new LinearInterpolator());

        progressBarAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationCancel(final Animator animation) {
            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                animationListner.animationFinish();
            }

            @Override
            public void onAnimationRepeat(final Animator animation) {
            }

            @Override
            public void onAnimationStart(final Animator animation) {

            }
        });

        progressBarAnimator.start();*/

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        this.startAnimation(animation);
    }


    public void animateStart(){

        drawPath.reset();
        pointList.clear();
        invalidate();

        /*if(animationStart)
            animationStart=false;
        else
            animationStart=true;



        new Thread(new Runnable() {

            @Override
            public void run() {
                while(animationStart) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "run: ...........");
                    postInvalidate();
                }
            }
        }).start();*/



/*
         Runnable animator = new Runnable() {
            @Override
            public void run() {

                while (true) {
                    Log.i(TAG, "run: is executing .......");

                }

            }
        };
        animator.run();*/

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private boolean comparePointWithPrecision(int inputX,int inputY,int originalX,int originalY){
        if(inputX > originalX-PRECISION && inputX< originalX+PRECISION
                && inputY > originalY-PRECISION && inputY< originalY+PRECISION)
            return true;

        return false;
    }

    private boolean checkInput(List<Point> inputList,List<Point> originalData){

        if(inputList.size() < originalData.size()-SIZE_PRECISION || inputList.size()>originalData.size()+SIZE_PRECISION)
            return false;

        for(int i=0;i<inputList.size() && i<originalData.size() ;i+=20){
            if(!comparePointWithPrecision(inputList.get(i).x,inputList.get(i).y,originalData.get(i).x,originalData.get(i).y))
                return false;
        }

        return true;
    }
}
