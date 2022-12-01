package com.moon.skywatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.google.android.gms.maps.MapView;
import com.moon.skywatch.R;
import com.moon.skywatch.databinding.FragmentPositionBinding;


public class PositionFragment extends Fragment {


    ViewGroup viewGroup;
    private FragmentPositionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_position, container, false);

        MapView mapView = new MapView(getContext());

        ViewGroup mapViewContainer = (ViewGroup) viewGroup.findViewById(R.id.kakao_map);
        mapViewContainer.addView(mapView);







        return viewGroup;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

}



    