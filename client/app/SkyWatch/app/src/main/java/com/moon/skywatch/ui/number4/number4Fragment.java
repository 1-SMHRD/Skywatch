package com.moon.skywatch.ui.number4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.moon.skywatch.databinding.FragmentNumber4Binding;


public class number4Fragment extends Fragment {

    private FragmentNumber4Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        number4ViewModel number4ViewModel =
                new ViewModelProvider(this).get(number4ViewModel.class);

        binding = FragmentNumber4Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNumber4;
        number4ViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}