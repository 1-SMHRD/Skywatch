package com.moon.moon4.ui.report5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.moon.moon4.databinding.FragmentReport5Binding;


public class report5Fragment extends Fragment {

    private FragmentReport5Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        report5ViewModel report5ViewModel =
                new ViewModelProvider(this).get(report5ViewModel.class);

        binding = FragmentReport5Binding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}