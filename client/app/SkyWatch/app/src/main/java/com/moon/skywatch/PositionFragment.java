package com.moon.skywatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.moon.skywatch.databinding.FragmentPositionBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PositionFragment extends Fragment implements OnMapReadyCallback{

    static RequestQueue requestQueue;
    private FragmentPositionBinding binding;
    private View view;
    private GoogleMap mMap;
    private MapView mMapView;
    ToggleButton btn_setArea;
    Button btn_sendArea;

    int numberOfMarker;
    int[] checkMarker;

    JSONObject jsonObject;
    JSONArray jsonArray;

    ArrayList<MapPointVO> mapPointList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_position, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        numberOfMarker = 0;
        checkMarker = new int[]{0, 0, 0, 0};

        mapPointList = new ArrayList<>();

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
                            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                                @Override
                                public void onMapClick(@NonNull LatLng latLng) {
                                    if (isChecked) {
                                        if (numberOfMarker < 4) {
                                            for (int i = 0; i < 4; i++) {
                                                if (checkMarker[i] == 0) {
                                                    MarkerOptions mOptions = new MarkerOptions();
                                                    jsonObject = new JSONObject();

                                                    mOptions.title((char)(i + 65) + "구역");
                                                    Double latitude = latLng.latitude;   // 위도
                                                    Double longitude = latLng.longitude;   // 경도

                                                    // 마커 간단한 설명
                                                    // mOptions.snippet(latitude.toString() + ", " + longitude.toString());
                                                    mOptions.position(new LatLng(latitude, longitude));

                                                    mMap.addMarker(mOptions);

                                                /*try {
                                                    jsonObject.put("point", (char)(i + 65));
                                                    jsonObject.put("latitude", latitude);
                                                    jsonObject.put("longitude", longitude);

                                                    jsonArray.put(jsonObject);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }*/

                                                    mapPointList.add(new MapPointVO((char)(i + 65) + "", latitude, longitude));

                                                    checkMarker[i] = 1;
                                                    i = 4;
                                                    Log.d("numberOfMarker", numberOfMarker+"");
                                                }
                                            }
                                            numberOfMarker++;
                                        } else {
                                            Toast.makeText(view.getContext(), "주차 구역 설정은 최대 4곳입니다.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(view.getContext(), "주차 구역 설정 버튼을 눌러주세요.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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

                                Iterator<MapPointVO> iterator = mapPointList.iterator();

                                while(iterator.hasNext()) {
                                    if(iterator.next().getPointName().equals(markerArea.charAt(0)+"")) {
                                        iterator.remove();
                                    }
                                }
                                Log.d("mapPointList", mapPointList + "");
                                Toast.makeText(view.getContext(), markerArea + "을 삭제하였습니다.", Toast.LENGTH_SHORT).show();
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

        btn_sendArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("numberOfMarker", numberOfMarker+"");
                if (numberOfMarker == 4) {
                    jsonArray = new JSONArray();
                    for (int i = 0; i < numberOfMarker; i++) {
                        jsonObject = new JSONObject();
                        try {
                            jsonObject.put("point", mapPointList.get(i).getPointName());
                            jsonObject.put("latitude", mapPointList.get(i).getLatitude());
                            jsonObject.put("longitude", mapPointList.get(i).getLongitude());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonArray.put(jsonObject);
                    }
                    Log.d("jsonArray", jsonArray + "");
                    makeRequest(jsonArray);
                } else {
                    Toast.makeText(view.getContext(), "주차 구역 4곳을 설정해 주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(view.getContext());
        }

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

    public void makeRequest(JSONArray jsonArray) {
        String ip = "http://220.80.88.45";
        int port = 5000;

        String url = ip + ":" + port + "/features/getArea_android";
        Log.d("send Data", jsonArray + "");

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);
                        Toast.makeText(view.getContext(), "주차 단속을 시작합니다.", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response Error", "" + error.getMessage());
            }
        }) {
            @Override //response를 UTF8로 변경해주는 소스코드
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String utf8String = new String(response.data, "UTF-8");
                    Log.d("utf8string", utf8String);
                    return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    // log error
                    return Response.error(new ParseError(e));
                } catch (Exception e) {
                    // log error
                    return Response.error(new ParseError(e));
                }
            }
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("sendArea", jsonArray + "");
                return param;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }
}