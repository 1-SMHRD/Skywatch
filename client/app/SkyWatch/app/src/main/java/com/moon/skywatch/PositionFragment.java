package com.moon.skywatch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moon.skywatch.databinding.FragmentPositionBinding;

public class PositionFragment extends Fragment implements OnMapReadyCallback{

    private FragmentPositionBinding binding;
    private View view;
    private GoogleMap mMap;
    private MapView mMapView;
    ToggleButton btn_setArea;
    Button btn_sendArea;

    int numberOfMarker;
    int[] checkMarker;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_position, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        numberOfMarker = 0;
        checkMarker = new int[]{0, 0, 0, 0};

        btn_setArea = view.findViewById(R.id.btn_setArea);
        btn_sendArea = view.findViewById(R.id.btn_sendArea);
        mMapView = (MapView) view.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                mMap = googleMap;

                LatLng latLng = new LatLng(35.149796202004325, 126.91992834014);
                mMap.addMarker(new MarkerOptions().position(latLng).title("스마트인재개발원"));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,19));

                btn_setArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        if (isChecked) {
                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    if (numberOfMarker < 4) {
                                        for (int i = 0; i < 4; i++) {
                                            if (checkMarker[i] == 0) {
                                                MarkerOptions mOptions = new MarkerOptions();

                                                mOptions.title((char)(i + 65) + "구역");
                                                Double latitude = latLng.latitude;   // 위도
                                                Double longitude = latLng.longitude;   // 경도

                                                // 마커 간단한 설명
                                                // mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                                                mOptions.position(new LatLng(latitude, longitude));

                                                mMap.addMarker(mOptions);
                                                checkMarker[i] = 1;
                                                i = 4;
                                                Log.d("numberOfMarker", numberOfMarker+"");
                                            }
                                        }
                                        numberOfMarker++;
                                    } else {
                                        Toast.makeText(view.getContext(), "주차 구역 설정은 최대 4곳입니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }
                });

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(@NonNull Marker marker) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        String markerArea = marker.getTitle();
                        builder.setTitle(markerArea);
                        builder.setMessage("해당 지역을 삭제 하시겠습니까?");

                        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                marker.remove();
                                int temp = (int) markerArea.charAt(0) - 65;
                                checkMarker[temp] = 0;
                                numberOfMarker--;
                            }
                        });

                        builder.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

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