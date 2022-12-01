package com.moon.skywatch;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.moon.skywatch.Fragment1;
import com.moon.skywatch.Fragment2;
import com.moon.skywatch.R;

public class LiveFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.fragment_live, container, false);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DisplayMetrics display = this.getResources().getDisplayMetrics();

        int width = display.widthPixels;
        int height = display.heightPixels;

        if(width>height){
            Fragment1 fragment1 = new Fragment1();
            fragmentTransaction.replace(R.id.frag_container, fragment1);
        }else{
            Fragment2 fragment2 = new Fragment2();
            fragmentTransaction.replace(R.id.frag_container, fragment2);
        }
        fragmentTransaction.commit();

        return fv;
    }
}