package com.moon.moon4.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.moon.moon4.R;
import com.moon.moon4.databinding.FragmentConnection6Binding;

import net.daum.mf.map.api.MapView;

public class GalleryFragment extends Fragment {
    ViewGroup viewGroup;
    private FragmentConnection6Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_slideshow, container, false);


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