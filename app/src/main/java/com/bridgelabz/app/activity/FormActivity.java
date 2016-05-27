package com.bridgelabz.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bridgelabz.app.R;

public class FormActivity extends AppCompatActivity {

    private final int RESULT_CODE=231;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Button submit=(Button)findViewById(R.id.submit);
        if(submit!=null)
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView message=(TextView)findViewById(R.id.message);
                Intent intent=new Intent();
                intent.putExtra("MESSAGE",message.getText().toString());
                setResult(RESULT_CODE,intent);
                finish();
            }
        });
    }
}
