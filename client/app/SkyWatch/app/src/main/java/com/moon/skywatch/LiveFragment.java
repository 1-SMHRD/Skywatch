package com.moon.skywatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moon.skywatch.databinding.FragmentLiveBinding;

public class LiveFragment extends Fragment {
    ViewGroup viewGroup;
    private FragmentLiveBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_live, container, false);










        return viewGroup;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}