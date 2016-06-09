package com.bridgelabz.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.activity.FormActivity;
import com.bridgelabz.app.activity.HeterogenousRecycleView;


/**
 * Created by bridgelabz5 on 25/5/16.
 */
public class IntentExample extends Fragment {
    View view;
    private final int RESULT_CODE=231;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.intent_example, container, false);
        Button activityForResult=(Button)view.findViewById(R.id.activityResult);
        activityForResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivityForResult(new Intent(getContext(), FormActivity.class),RESULT_CODE);
            }
        });

        Button explicitIntent=(Button)view.findViewById(R.id.explicitIntent);
        explicitIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    getActivity().startActivity(new Intent("com.bridgelabz.recycleviewdemo.activity.ActivityTransition"));
                }catch (Exception exception){
                    Toast.makeText(view.getContext(),getString(R.string.no_intent),Toast.LENGTH_LONG).show();
                }
            }
        });

        Button recycleView=(Button)view.findViewById(R.id.recycleView);
        recycleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HeterogenousRecycleView.class));
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 231
        if(requestCode==RESULT_CODE)
        {
            String message=data.getStringExtra("MESSAGE");
            TextView textView=(TextView)view.findViewById(R.id.result);
            textView.setText(message);
        }
    }

}
