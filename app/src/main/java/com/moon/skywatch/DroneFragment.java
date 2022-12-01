package com.moon.skywatch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moon.skywatch.DirectAdapter;
import com.moon.skywatch.DirectVO;
import com.moon.skywatch.R;
import com.moon.skywatch.databinding.FragmentPositionBinding;

import java.util.ArrayList;


public class DroneFragment extends Fragment {
    ArrayList<DirectVO> datas;
    RecyclerView rcv;
    DirectAdapter adapter;

    private FragmentPositionBinding binding;

    ViewGroup viewGroup;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, Bundle savedInstanceState) {



        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_drone, container, false);

        rcv = viewGroup.findViewById(R.id.RecyclerView);
        rcv.setHasFixedSize(true);
        adapter = new DirectAdapter(datas);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rcv.setLayoutManager(mLayoutManager);
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setAdapter(adapter);

        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
        //aa
    }

    private void initDataset() {
        datas = new ArrayList<>();
        DirectVO m1 = new DirectVO("네이버","https://www.naver.com");
        DirectVO m2 = new DirectVO("다음","https://www.daum.net");

        datas.add(m1);
        datas.add(m2);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}