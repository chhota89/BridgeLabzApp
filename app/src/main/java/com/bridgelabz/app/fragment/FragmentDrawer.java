package com.bridgelabz.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bridgelabz.app.R;
import com.bridgelabz.app.activity.MainActivity;
import com.bridgelabz.app.adapter.DrawerAdapter;
import com.bridgelabz.app.model.UserInfo;

import java.util.ArrayList;

/**
 * Created by bridgeit on 11/6/16.
 */

public class FragmentDrawer extends Fragment {

    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private RecyclerView mRecyclerDrawer;
    private DrawerAdapter mAdapter;
    private ActionBarDrawerToggle mDrawerBarToggle;
    DrawerLayout mDrawerLayout;
    View mContainer;

    public FragmentDrawer(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sharedPreferences.getBoolean(KEY_USER_LEARNED_DRAWER, false);
        mFromSavedInstanceState = savedInstanceState != null ? true : false;
        // Select either the default item (0) or the last selected item.
        //selectItem(0);
    }

    /*private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nevigation_drawer,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerDrawer = (RecyclerView) view.findViewById(R.id.drawerList);

        mAdapter = new DrawerAdapter(getData(),getActivity());
        mRecyclerDrawer.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerDrawer.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), mRecyclerDrawer, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                ((MainActivity) getActivity()).onDrawerItemClicked(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        mRecyclerDrawer.setAdapter(mAdapter);
    }

    public ArrayList<UserInfo> getData(){
        ArrayList<UserInfo> list=new ArrayList<>();
        list.add(new UserInfo("Mohmad","Chhota"));
        list.add(new UserInfo("Mohmad","Chhota"));
        list.add(new UserInfo("Mohmad","Chhota"));
        list.add(new UserInfo("Mohmad","Chhota"));

        return list;
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar){
        mContainer = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerBarToggle= new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(KEY_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                ((MainActivity) getActivity()).onDrawerSlide(slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerBarToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerBarToggle.syncState();
                if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
                    mDrawerLayout.openDrawer(mContainer);
                }
            }
        });
    }



    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
