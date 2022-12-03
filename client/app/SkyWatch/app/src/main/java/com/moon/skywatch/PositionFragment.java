package com.moon.skywatch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.moon.skywatch.databinding.FragmentPositionBinding;

public class PositionFragment extends Fragment{

    private FragmentPositionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPositionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //        MapView mapView = new MapView(getContext());
//
//        ViewGroup mapViewContainer = (ViewGroup) viewGroup.findViewById(R.id.kakao_map);
//        mapViewContainer.addView(mapView);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}