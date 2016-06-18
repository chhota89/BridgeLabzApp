package com.bridgelabz.app.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bridgelabz.app.R;
import com.bridgelabz.app.fragment.ButtonFragment;
import com.bridgelabz.app.fragment.FragmentDrawer;
import com.bridgelabz.app.fragment.IntentExample;
import com.bridgelabz.app.fragment.SqliteFragment;
import com.bridgelabz.app.fragment.Widgets;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i("ON CREATE", "onCreate: "+ "I am in on create method");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentDrawer mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        ViewPager viewPager=(ViewPager)findViewById(R.id.viewpager);
        setUpViewPager(viewPager);

        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer), toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(), "Item 2 Selected", Toast.LENGTH_LONG).show();
                return true;
            case R.id.item3:
                Toast.makeText(getApplicationContext(), "Item 3 Selected", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpViewPager(ViewPager viewPager) {
        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());
        viewPageAdapter.addFragment(new ButtonFragment(),"Alarm");
        viewPageAdapter.addFragment(new IntentExample(),"Intent");
        viewPageAdapter.addFragment(new Widgets(),"Widget");
        viewPageAdapter.addFragment(new SqliteFragment(),"sqlite");
        viewPager.setAdapter(viewPageAdapter);
    }

    public void onDrawerSlide(float slideOffset) {
        toggleTranslateFAB(slideOffset);
    }


    public void onDrawerItemClicked(int index) {
        Toast.makeText(MainActivity.this,"Position " +index,Toast.LENGTH_LONG).show();
    }


    private void toggleTranslateFAB(float slideOffset) {
        /*if (mFABMenu != null) {
            if (mFABMenu.isOpen()) {
                mFABMenu.close(true);
            }
            mFAB.setTranslationX(slideOffset * 200);
        }*/
    }

    class  ViewPageAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
           return mFragmentList.size();
        }

        public void addFragment(Fragment fragment,String name){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(name);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
