package com.bridgelabz.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bridgelabz.app.R;

/**
 * Created by bridgelabz5 on 28/5/16.
 */
public class FragmentOne extends Fragment {

    public FragmentOne(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one,container,false);
    }
}
