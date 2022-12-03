package com.moon.skywatch;

import android.content.pm.ActivityInfo;
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

//        MapView mapView = new MapView(getContext());
//
//        ViewGroup mapViewContainer = (ViewGroup) viewGroup.findViewById(R.id.kakao_map);
//        mapViewContainer.addView(mapView);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 landscape(가로) 화면으로 고정하고 싶은 경우
//        getActivity().setContentView(R.layout.fragment_live);
        return viewGroup;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
