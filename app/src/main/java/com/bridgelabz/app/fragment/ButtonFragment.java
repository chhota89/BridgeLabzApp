package com.bridgelabz.app.fragment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.activity.CameraActivity;
import com.bridgelabz.app.activity.FragmentDemo;
import com.bridgelabz.app.activity.MusicPlayer;
import com.bridgelabz.app.activity.VedioActivity;
import com.bridgelabz.app.reciver.AlaramBroadcastReciver;

/**
 * Created by bridgelabz5 on 25/5/16.
 */
public class ButtonFragment extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.button, container, false);
        Button setAlaram = (Button) view.findViewById(R.id.setAlaram);
        setAlaram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textView = (EditText) view.findViewById(R.id.time);
                try {
                    int time = 0;
                    time = Integer.parseInt(textView.getText().toString());
                    Intent intent = new Intent(v.getContext(), AlaramBroadcastReciver.class);
                    //Set Panding intent
                    PendingIntent pandingIntent = PendingIntent.getBroadcast(getActivity().getApplicationContext(), 234324243, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Activity.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME, System.currentTimeMillis() + (time * 1000), pandingIntent);
                    Toast.makeText(view.getContext(), getString(R.string.alaram_set), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException exception) {
                    Toast.makeText(view.getContext(), getString(R.string.error_enter_number), Toast.LENGTH_LONG).show();
                } catch (Exception exception) {
                    Toast.makeText(view.getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //Display Popup Menu
        final Button popupMenu=(Button)view.findViewById(R.id.popupMenu);
        popupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu menu=new PopupMenu(getActivity(),v);
                menu.getMenuInflater().inflate(R.menu.option_menu,menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item1:
                                Toast.makeText(v.getContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.item2:
                                Toast.makeText(v.getContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                                return true;
                            case R.id.item3:
                                Toast.makeText(v.getContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                menu.show();
            }
        });


        //start audio
        Button startMusic=(Button)view.findViewById(R.id.startMusic);
        startMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), MusicPlayer.class));
            }
        });


        // Vedio Player
        Button startVedio=(Button)view.findViewById(R.id.startVedio);
        startVedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), VedioActivity.class));
            }
        });

        //Fragment Demo
        Button fragmentActivity=(Button)view.findViewById(R.id.fragmentActivity);
        fragmentActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), FragmentDemo.class));
            }
        });


        //Open Camera Activity
        Button camera=(Button)view.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), CameraActivity.class));
            }
        });

        return view;
    }


}
