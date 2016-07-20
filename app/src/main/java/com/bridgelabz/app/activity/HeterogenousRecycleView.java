package com.bridgelabz.app.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.bridgelabz.app.R;
import com.bridgelabz.app.adapter.HeterogenousAdapter;
import com.bridgelabz.app.model.UserInfo;

import java.util.ArrayList;

public class HeterogenousRecycleView extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Object> objectsArrayList;
    private HeterogenousAdapter adapter;
    private int edit_position;
    private View view;
    private boolean add=false;
    private Paint paint=new Paint();
    private AlertDialog.Builder alertDialog;
    EditText et_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heterogenous_recycle_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
        initDialog();
        recyclerView.setAdapter(adapter);
    }
    public void initViews(){
        recyclerView=(RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        objectsArrayList= new ArrayList<>();
        objectsArrayList.add(new UserInfo("India","Delhi"));
        objectsArrayList.add("Image");
        objectsArrayList.add(new UserInfo("Pakistan","karachi"));
        objectsArrayList.add(new UserInfo("Bangladesh","Dhaka"));
        objectsArrayList.add(new UserInfo("ShriLanka","Colombo"));
        objectsArrayList.add("Image");
        objectsArrayList.add(new UserInfo("Nepal","kathmandu"));
        adapter=new HeterogenousAdapter(objectsArrayList);
        adapter.notifyDataSetChanged();
        initSwipe();
    }
    public void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT){
                    adapter.removeItem(position);
                }else {
                    removeView();
                    edit_position = position;
                    alertDialog.setTitle("Edit Country");
                    if (objectsArrayList.get(position) instanceof UserInfo){
                        UserInfo userInfo= (UserInfo) objectsArrayList.get(position);
                        et_country.setText(userInfo.getFirstName());
                    }else {
                        String abc= (String) objectsArrayList.get(position);
                        et_country.setText("ESHVAR");
                    }

                    alertDialog.show();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    View itemView = viewHolder.itemView;
                    float height=(float) itemView.getBottom() - (float) itemView.getTop();
                    float width =height/3;

                    if (dX > 0){
                        paint.setColor(Color.parseColor("#388e3c"));
                        RectF background = new RectF(
                                (float)itemView.getLeft(),
                                (float)itemView.getTop(),
                                    dX,
                                (float)itemView.getBottom());
                                c.drawRect(background,paint);
                        icon = BitmapFactory.decodeResource(getResources(),R.drawable.action_search);
                        RectF icon_dest = new RectF(
                                                (float)itemView.getLeft()+width,
                                                itemView.getTop()+width,
                                                (float)itemView.getLeft()+2*width,
                                                (float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    }else {
                        paint.setColor(Color.parseColor("#d32f2f"));
                        RectF background = new RectF(
                                (float)itemView.getLeft()+dX,
                                (float)itemView.getTop(),
                                (float)itemView.getRight(),
                                (float)itemView.getBottom());
                        c.drawRect(background,paint);
                        icon =BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                        RectF icon_dest=new RectF(
                                (float)itemView.getRight()-2*width,
                                (float)itemView.getTop()+width,
                                (float)itemView.getRight() -width,
                                (float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    private void removeView(){
        if (view.getParent()!=null){
            ((ViewGroup)view.getParent()).removeView(view);
        }
    }

    public void initDialog(){
        alertDialog = new AlertDialog.Builder(this);
        view = getLayoutInflater().inflate(R.layout.dialog_layout,null);
        alertDialog.setView(view);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (add){
                    add = false;
                    adapter.addItem(et_country.getText().toString());
                    dialog.dismiss();
                }else {
                    objectsArrayList.set(edit_position,et_country.getText().toString());
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });
        et_country = (EditText) view.findViewById(R.id.etCountry);
    }

}
