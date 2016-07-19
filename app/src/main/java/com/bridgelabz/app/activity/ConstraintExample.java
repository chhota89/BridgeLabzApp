package com.bridgelabz.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.app.R;

/**
 * Created by bridgeit on 13/6/16.
 */

public class ConstraintExample extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);

        editText= (EditText) findViewById(R.id.editText);
       /* editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s == EditorInfo.IME_ACTION_SEND){
                    Toast.makeText(ConstraintExample.this,"Send button clicked",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    Toast.makeText(ConstraintExample.this,"Send button clicked",Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });
    }
}
