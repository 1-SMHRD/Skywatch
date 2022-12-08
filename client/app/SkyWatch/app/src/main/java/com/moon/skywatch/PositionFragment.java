package com.moon.skywatch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.content.pm.ActivityInfo;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moon.skywatch.databinding.FragmentPositionBinding;

public class PositionFragment extends Fragment implements OnMapReadyCallback{

    private FragmentPositionBinding binding;
    private View view;
    private GoogleMap mMap;
    private MapView mMapView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_position, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        mMapView = (MapView) view.findViewById(R.id.mapView2);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;

                LatLng latLng = new LatLng(35.149796202004325, 126.91992834014);
                mMap.addMarker(new MarkerOptions().position(latLng).title("스마트인재개발원"));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,19));

            }
        });


        return view;
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
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }
}