package com.bridgelabz.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bridgelabz.app.R;
import com.bridgelabz.app.adapter.ImageLoadingAdapter;

import java.util.ArrayList;

public class ImageLoadingLibrary extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loading_library);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button volley,glide,picasso,fresco;
        volley=(Button)findViewById(R.id.volley);
        glide=(Button)findViewById(R.id.glide);
        picasso=(Button)findViewById(R.id.picasso);
        fresco=(Button)findViewById(R.id.fresco);

        recyclerView=(RecyclerView)findViewById(R.id.imageRecycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        loadList();

        //volley Image Loade
        volley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoadingAdapter adapter=new ImageLoadingAdapter(list,ImageLoadingAdapter.VOLLEY,ImageLoadingLibrary.this);
                recyclerView.setAdapter(adapter);
            }
        });

        //glide Image Loade
        glide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoadingAdapter adapter=new ImageLoadingAdapter(list,ImageLoadingAdapter.GLIDE,ImageLoadingLibrary.this);
                recyclerView.setAdapter(adapter);
            }
        });

        //picasso Image Loade
        picasso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoadingAdapter adapter=new ImageLoadingAdapter(list,ImageLoadingAdapter.PICASSO,ImageLoadingLibrary.this);
                recyclerView.setAdapter(adapter);
            }
        });

        //Fresco Image Loade
        fresco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoadingAdapter adapter=new ImageLoadingAdapter(list,ImageLoadingAdapter.FRESCO,ImageLoadingLibrary.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    private void loadList() {
        list.add("http://api.androidhive.info/feed/img/cosmos.jpg");
        list.add("http://api.androidhive.info/feed/img/time_best.jpg");
        list.add("http://api.androidhive.info/feed/img/discovery_mos.jpg");
        list.add("http://api.androidhive.info/feed/img/nav_drawer.jpg");
        list.add("http://api.androidhive.info/feed/img/ktm_1290.jpg");
        list.add("http://api.androidhive.info/feed/img/harley_bike.jpg");
        list.add("http://api.androidhive.info/feed/img/rock.jpg");
        list.add("http://api.androidhive.info/feed/img/life_photo.jpg");
    }

}
