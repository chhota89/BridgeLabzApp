package com.bridgelabz.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bridgelabz.app.R;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.rey.material.widget.CompoundButton;
import com.rey.material.widget.RadioButton;

public class MaterialDesign extends AppCompatActivity {

    RadioButton button1,button2;

    /*private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);

        //setupWindowAnimations();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button1=(RadioButton)findViewById(R.id.radioButton7);
        button2=(RadioButton)findViewById(R.id.radioButton8);

        CompoundButton.OnCheckedChangeListener mCheckChangeListener = new  CompoundButton.OnCheckedChangeListener(){

           @Override
           public void onCheckedChanged(android.widget.CompoundButton buttonView, boolean isChecked) {
               if(isChecked){
                   if(buttonView == button1)
                       button2.setChecked(false);
                   else
                       button1.setChecked(false);
               }
           }
       };

        button1.setOnCheckedChangeListener(mCheckChangeListener);
        button2.setOnCheckedChangeListener(mCheckChangeListener);

        final com.rey.material.widget.EditText et_helper_error = (com.rey.material.widget.EditText)findViewById(R.id.password);

        et_helper_error.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
                    et_helper_error.setError("Password is incorrect.");

                return false;
            }

        });


        final EditText email = (EditText)findViewById(R.id.email);

        email.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP)
                    email.setError("email is incorrect.");

                return false;
            }

        });

        ImageView imageView=(ImageView)findViewById(R.id.imageView2);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MaterialDesign.this, ActivityTransitionDemo.class);
                ActivityTransitionLauncher.with(MaterialDesign.this).from(v).launch(intent);
            }
        });
    }

}
