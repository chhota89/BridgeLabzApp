package com.bridgelabz.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bridgelabz.app.R;
import com.bridgelabz.app.fragment.FriendsFragment;
import com.bridgelabz.app.fragment.HomeFragment;
import com.bridgelabz.app.fragment.MessagesFragment;
import com.bridgelabz.app.fragment.NavigationFragmentDrawer;

public class AndroidMaterialDesign extends AppCompatActivity implements NavigationFragmentDrawer.FragmentDrawerListener{
    private Toolbar mToolbar;
    private NavigationFragmentDrawer navigationFragmentDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_material_design);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationFragmentDrawer = (NavigationFragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.android_fragment_navigation_drawer);
        navigationFragmentDrawer.setUp(R.id.android_fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.android_fragment_navigation_drawer), mToolbar);
        navigationFragmentDrawer.setDrawerListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle action bar item clicks here.The action will
        //automatically handle clicks on the Home/Up button, so
        //long as we specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_setting){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    private void displayView(int position){
        Fragment fragment=null;
        String title=getString(R.string.app_name);
        switch (position){
            case 0:
                fragment=new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new FriendsFragment();
                title = getString(R.string.title_friend);
                break;
            case 2:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }
        if (fragment != null){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body,fragment);
            fragmentTransaction.commit();

            //set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
