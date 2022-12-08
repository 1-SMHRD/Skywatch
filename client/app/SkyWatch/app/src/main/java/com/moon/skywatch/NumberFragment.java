package com.moon.skywatch;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class NumberFragment extends Fragment {

    static RequestQueue requestQueue;

    private Socket socket;
    private DataOutputStream outStream;
    private DataInputStream inStream;

    Button btn_date;
    TextView editTextDate;
    View view;
    Date nowDate;
    Date setDate;
    SimpleDateFormat simpleDateFormat;
    JSONArray jsonArray;
    JSONObject jsonObject;
    CarDataAdapter carDataAdapter;
    ArrayList<CarDataVO> carDataList;
    RecyclerView rcv;

    String getDate;
    String sendDate;

    String regulation_date;
    String regulation_time;
    String regulation_area;
    String car_num;
    String img_parking1;
    String img_parking2;
    String img_numPlate;

    Bitmap bmp_parking1;
    Bitmap bmp_parking2;
    Bitmap bmp_numPlate;

    byte[][] img_list;

    int check;

    CarDataVO cvo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_number, container, false);

        btn_date = view.findViewById(R.id.btn_date);
        editTextDate = view.findViewById(R.id.editTextDate);
        rcv = view.findViewById(R.id.recyclerview);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        nowDate = Calendar.getInstance().getTime();
        getDate = simpleDateFormat.format(nowDate);
        editTextDate.setText(getDate);

        carDataList = new ArrayList<>();
        carDataAdapter = new CarDataAdapter(getContext(), carDataList);
        rcv.setAdapter(carDataAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(this.getContext()));

        final Calendar date = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                view.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar updateDate = Calendar.getInstance();
                updateDate.set(year, month, dayOfMonth);

                setDate = updateDate.getTime();
                getDate = simpleDateFormat.format(setDate);
                editTextDate.setText(getDate);

            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setSpinnersShown(true);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("editTextDate Click", "ClickClickClickClickClick");
                datePickerDialog.show();
            }
        });

        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDate = editTextDate.getText().toString();
                Log.d("sendDate", sendDate);
                makeRequest(sendDate);
            }
        });


        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(view.getContext());
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    public void makeRequest(String sendDate) {
        check = 0;
        String ip = "http://220.80.88.45";
        int port = 5000;

        String url = ip + ":" + port + "/features/getinfo_android";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response", response);

                        try {
                            carDataList.clear();
                            jsonArray = new JSONArray(response);
                            Log.d("jsonArray Length", jsonArray.length() + "");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);

                                regulation_date = (String) jsonObject.get("regulation_date");
                                regulation_time = (String) jsonObject.get("regulation_time");
                                regulation_area = (String) jsonObject.get("regulation_area");
                                car_num = (String) jsonObject.get("car_num");

                                img_parking1 = (String) jsonObject.get("imgdir_parking1");
                                img_parking2 = (String) jsonObject.get("imgdir_parking2");
                                img_numPlate = (String) jsonObject.get("imgdir_numplate");

                                String[] car_data = new String[]{regulation_date, regulation_time, regulation_area, car_num};
                                String[] imgDir = new String[]{img_parking1, img_parking2, img_numPlate};

                                if (imgDir.length != 0) {
                                    connect(imgDir);
                                }

                                if (check == 1) {
                                    carDataList.add(setResult(car_data, img_list));
                                    Log.d("carDataList add", "add");
                                }

                                Thread.sleep(100);
                            }

                            Log.d("carDataList size", carDataList.size() + "");
                            carDataAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

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
                param.put("sendDate", sendDate);

                return param;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public byte[] InPutStreamToByteArray(int data_len, DataInputStream in) {
        int loop = (int)(data_len / 1024);
        Log.w("loop: ", Integer.toString(loop));
        byte[] resbytes = new byte[data_len];
        int offset = 0;

        try {
            for (int i = 0; i < loop; i++) {
                in.readFully(resbytes, offset, 1024);
                offset += 1024;
            }
            in.readFully(resbytes, offset, data_len-(loop*1024));
            Log.d("inStream", new String(resbytes));
            Log.w("resbytes len: ", Integer.toString(resbytes.length));
            Log.w("resbytes: ", new String(resbytes));
            //Log.w("get image", "get image");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resbytes;
    }

    void connect(String[] imgDir) {
        Handler mHandler = new Handler(Looper.getMainLooper());
        Log.w("connect", "connecting...");
        Log.d("imgDir len", imgDir.length + "");

        Bitmap bmp;

        Thread checkUpdate = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {
                String newip = "220.80.88.45";
                int port = 8089;

                try{
                    socket = new Socket(newip, port);
                    Log.w("서버 접속", "서버 접속 성공");
                } catch (IOException e1) {
                    Log.w("서버 접속 실패", "서버 접속 실패");
                    e1.printStackTrace();
                }

                try {
                    outStream = new DataOutputStream(socket.getOutputStream());
                    inStream = new DataInputStream(socket.getInputStream());
                    outStream.writeUTF("/image");
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼 생성 실패", "버퍼 생성 실패");
                }

                Log.w("버퍼 생성 성공", "버퍼 생성 성공");

                int img_len = 0;

                img_list = new byte[imgDir.length][];

                for (int i = 0; i < imgDir.length; i++) {
                    try {
                        Log.d("imgDir", i + " : " + imgDir[i]);
                        outStream.writeUTF(imgDir[i]);
                        outStream.flush();
                        Log.d("send img dir", "send img dir");

                        img_len = inStream.readInt();
                        Log.d("get img len", img_len + "");
                    } catch (IOException e) {
                        Log.d("send img dir error", "send img dir error");
                    }

                    img_list[i] = InPutStreamToByteArray(img_len, inStream);
                    Log.d("img receive", (i + 1) + "img receive");
                }



                try {
                    socket.close();
                } catch (IOException e) {

                }
            }
        };
        checkUpdate.start();

        try {
            checkUpdate.join();
        }catch (InterruptedException e){

        }
        check = 1;
        Log.d("Thread terminated", "Thread terminated");
    }

    public CarDataVO setResult(String[] car_data, byte[][] img_list) {

        CarDataVO vo = new CarDataVO();

        vo.setRegulationDate(car_data[0]);
        vo.setRegulationTime(car_data[1]);
        vo.setRegulationArea(car_data[2]);
        vo.setNumPlate(car_data[3]);

        for (int i = 0; i < img_list.length; i++) {
            Base64.Decoder decoder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                decoder = Base64.getDecoder();

                byte[] decodedBytes = decoder.decode(img_list[i]);
                Bitmap bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                switch (i) {
                    case 0:
                        bmp_parking1 = bmp;
                        break;
                    case 1:
                        bmp_parking2 = bmp;
                        break;
                    case 2:
                        bmp_numPlate = bmp;
                        break;
                }
            }
            Log.d("setResult", i + "");
        }

        vo.setImgParking1(bmp_parking1);
        vo.setImgParking2(bmp_parking2);
        vo.setImgNumPlate(bmp_numPlate);

        return vo;
    }


}

