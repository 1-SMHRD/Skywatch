package com.moon.skywatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moon.skywatch.databinding.FragmentReportBinding;


public class ReportFragment extends Fragment implements OnMapReadyCallback{
    private GoogleMap gmap;
    private MapView mmapView;
    private FragmentReportBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mmapView = (MapView) root.findViewById(R.id.reportmap);
        mmapView.onCreate(savedInstanceState);
        mmapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                gmap = googleMap;

                LatLng latLng = new LatLng(35.149796202004325, 126.91992834014);
                googleMap.addMarker(new MarkerOptions().position(latLng).title("스마트인재개발원"));
                latLng = new LatLng(35.14926434318328 , 126.91984381043518 );
                gmap.addMarker(new MarkerOptions().position(latLng).title("광주동부경찰서"));

                latLng = new LatLng(35.149521034504936 , 126.91954725211693 );
                gmap.addMarker(new MarkerOptions().position(latLng).title("금남지구대"));


                gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,19));

        gmap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01021628994"));
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);

                }
                return false;
            }
        });
            }
        });




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));

    }
    @Override
    public void onResume() {
        super.onResume();
        mmapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mmapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mmapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        mmapView.onDestroy();
        super.onDestroy();
    }
}