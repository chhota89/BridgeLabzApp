package com.bridgelabz.app.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bridgelabz.app.R;
import com.bridgelabz.app.view.CustomButton;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG=QuizActivity.class.getSimpleName();
    Button[] buttons=new Button[5];
    CustomButton[] answer=new CustomButton[4];
    Value[] enumArray=new Value[answer.length];
    private int selectedPosition=0;
    View.OnClickListener onclicklistener;

    ObjectAnimator colorAnimator;

    enum Value{
        Full,Empty;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final LinearLayout linearLayout=(LinearLayout)findViewById(R.id.linearLayout);
        final LinearLayout answerLayout=(LinearLayout)findViewById(R.id.answer);

        for(int i=0;i<answer.length;i++)
            enumArray[i]=Value.Empty;


        onclicklistener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Button button=(Button)v;
                if(selectedPosition<answer.length){
                    //Reput the value that alredy occupied
                if(answer[selectedPosition].getText()!=null &&
                        !answer[selectedPosition].getText().equals("")){

                    Button newButton = new Button(QuizActivity.this);
                    newButton.setOnClickListener(onclicklistener);
                    newButton.setText(answer[selectedPosition].getText());
                    linearLayout.addView(newButton);
                }

                    answer[selectedPosition].setText(button.getText());
                    enumArray[selectedPosition]=Value.Full;

                    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        colorAnimator=ObjectAnimator.ofArgb(button.getBackground().mutate(),"tint",
                                getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimaryDark));
                        colorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                        colorAnimator.start();
                    }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                        colorAnimator=ObjectAnimator.ofObject(button.getBackground().mutate(),"tint",new ArgbEvaluator(),
                                getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimaryDark));
                        colorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                        colorAnimator.start();
                    }*/
                    //answer[selectedPosition].setBackgroundColor();
                    answer[selectedPosition].setText(((Button) v).getText().toString());
                    Log.i(TAG, "onClick: "+v.getId());
                    answer[selectedPosition].executeAnimation(true,getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimaryDark));
                    linearLayout.removeView(v);

                    if(selectedPosition !=0)
                        answer[selectedPosition-1].executeAnimation(false,getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimaryDark));

                    selectedPosition++;
                }
            }
        };

        View.OnClickListener answerOnClickListner=new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button=(Button)view;
                selectedPosition = button.getId() - 1;
                if(button.getText() !=null && !button.getText().toString().equals("")) {
                    Button newButton = new Button(QuizActivity.this);
                    newButton.setOnClickListener(onclicklistener);
                    newButton.setText(button.getText());
                    button.setText("");
                    button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    linearLayout.addView(newButton);
                }

            }
        };

        for(int i=0;i<5;i++){
            Button button=new Button(this);
            button.setText(""+i);
            button.setOnClickListener(onclicklistener);
            linearLayout.addView(button);
        }

        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(0, 0, 10, 0);

        for(int i=0;i<answer.length;i++){
            answer[i]=new CustomButton(this);
            //answer[i].setText(""+i);
            answer[i].setId(i+1);
            answer[i].setLayoutParams(buttonLayoutParams);
            answer[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));
            answer[i].setOnClickListener(answerOnClickListner);
            answerLayout.addView(answer[i]);
        }
    }

}
