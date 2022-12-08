package com.moon.skywatch;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.moon.joystick1.JoystickView;
import com.moon.skywatch.databinding.FragmentLiveBinding;

import kotlin.jvm.internal.Intrinsics;

public class LiveFragment extends Fragment {
    private FragmentLiveBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLiveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 화면을 LANDSCAPE(가로) 화면으로 고정하고 싶은 경우

        super.onCreate(savedInstanceState);
//        this.setContentView(1300000);
        JoystickView joystickView = (JoystickView)root.findViewById(R.id.joystick);
        final TextView angleValueView = (TextView)root.findViewById(R.id.value_angle);
        final TextView strengthValueView = (TextView)root.findViewById(R.id.value_strength);
        joystickView.setOnMoveListener((JoystickView.OnMoveListener)(new JoystickView.OnMoveListener() {
            public final void onMove(int angle, int strength) {
                TextView var10000 = angleValueView;
                Intrinsics.checkNotNullExpressionValue(var10000, "angleValueView");
                var10000.setText((CharSequence)("angle : " + angle));
                var10000 = strengthValueView;
                Intrinsics.checkNotNullExpressionValue(var10000, "strengthValueView");
                var10000.setText((CharSequence)("strength : " + strength));
            }
        }), 100);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}