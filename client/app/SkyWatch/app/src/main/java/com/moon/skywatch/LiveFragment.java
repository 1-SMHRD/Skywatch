package com.moon.skywatch;

import android.content.pm.ActivityInfo;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.moon.joystick1.JoystickView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Base64;

import kotlin.jvm.internal.Intrinsics;


public class LiveFragment extends Fragment {

    final String ip = "220.80.88.45";
    final int port = 8089;

    // about socket
    private Handler mHandler;
    private Socket socket;
    private DataOutputStream outStream;
    private DataInputStream inStream;

    View view;
    ImageView iv_droneView;
    Bitmap bmp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_live, container, false);
        // mHandler = new Handler(Looper.getMainLooper());
        mHandler = new Handler();

        // 화면을 LANDSCAPE(가로) 화면으로 고정하고 싶은 경우
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        // super.onCreate(savedInstanceState);
        /*JoystickView joystickView = (JoystickView)view.findViewById(R.id.joystick);
        final TextView angleValueView = (TextView)view.findViewById(R.id.value_angle);
        final TextView strengthValueView = (TextView)view.findViewById(R.id.value_strength);
        joystickView.setOnMoveListener((JoystickView.OnMoveListener)(new JoystickView.OnMoveListener() {
            public final void onMove(int angle, int strength) {
                TextView var10000 = angleValueView;
                Intrinsics.checkNotNullExpressionValue(var10000, "angleValueView");
                var10000.setText((CharSequence)("angle : " + angle));
                var10000 = strengthValueView;
                Intrinsics.checkNotNullExpressionValue(var10000, "strengthValueView");
                var10000.setText((CharSequence)("strength : " + strength));
            }
        }), 100);*/


        // drone 실시간 영상 받아오기
        if (view != null){
            connect();
        }

        return view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        view = null;
    }

    /*void connect() {
        mHandler = new Handler(Looper.getMainLooper());
        Log.w("connect", "connecting...");

        iv_droneView = view.findViewById(R.id.iv_droneView);

        Thread checkUpdate = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void run() {

                try{
                    socket = new Socket(ip, port);
                    Log.w("서버 접속", "서버 접속 성공");
                } catch (IOException e1) {
                    Log.w("서버 접속 실패", "서버 접속 실패");
                    e1.printStackTrace();
                }

                // Log.w("edit 넘어가야 할 값", "aaabbbccc");

                try {
                    outStream = new DataOutputStream(socket.getOutputStream());
                    inStream = new DataInputStream(socket.getInputStream());
                    outStream.writeUTF("/drone");
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼 생성 실패", "버퍼 생성 실패");
                }

                Log.w("버퍼 생성 성공", "버퍼 생성 성공");

                while(true) {

                    try {
                        int length = inStream.readInt();
                        Log.d("data length: ",  length + "---");

                        byte[] buffer = InPutStreamToByteArray(length, inStream);
                        Log.d("buffer", new String(buffer));

                        Base64.Decoder decoder = Base64.getDecoder();
                        byte[] decodedBytes = decoder.decode(buffer);

                        Bitmap bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                        Log.d("bmp", bmp + "");
//                        iv_droneView.setImageBitmap(bmp);
                        iv_droneView.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false));
                        Log.d("img 불러오기", "success");

                        Thread.sleep(10);

                    } catch (Exception e) {
                        Log.d("sdafas", "asdfsadf");
                        break;
                    }
                }

                try {
                    socket.close();
                    Log.d("socket close", "socket close()");
                    connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        checkUpdate.start();
    }*/


    void connect() {
        Log.w("connect", "connecting...");

        iv_droneView = view.findViewById(R.id.iv_droneView);

        Thread checkUpdate = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {

                try{
                    socket = new Socket(ip, port);
                    Log.w("서버 접속", "서버 접속 성공");
                } catch (IOException e1) {
                    Log.w("서버 접속 실패", "서버 접속 실패");
                    e1.printStackTrace();
                }

                try {
                    outStream = new DataOutputStream(socket.getOutputStream());
                    inStream = new DataInputStream(socket.getInputStream());
                    outStream.writeUTF("/drone");
                    outStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.w("버퍼 생성 실패", "버퍼 생성 실패");
                }

                /*mHandler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {

                        // Log.w("edit 넘어가야 할 값", "aaabbbccc");

                        Log.w("버퍼 생성 성공", "버퍼 생성 성공");

                        while(true) {
                            try {
                                int length = inStream.readInt();
                                Log.d("data length: ",  length + "---");

                                byte[] buffer = InPutStreamToByteArray(length, inStream);
                                Log.d("buffer", new String(buffer));

                                Base64.Decoder decoder = Base64.getDecoder();
                                byte[] decodedBytes = decoder.decode(buffer);

                                Bitmap bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                                Log.d("bmp", bmp + "");
//                        iv_droneView.setImageBitmap(bmp);
                                iv_droneView.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false));
                                Log.d("img 불러오기", "success");

                                Thread.sleep(10);

                            } catch (Exception e) {
                                Log.d("sdafas", "asdfsadf");
                                break;
                            }
                        }
                    }
                });*/

                while (true) {
                    try {
                        int length = inStream.readInt();
                        Log.d("data length: ",  length + "---");

                        byte[] buffer = InPutStreamToByteArray(length, inStream);
                        Log.d("buffer", new String(buffer));

                        Base64.Decoder decoder = Base64.getDecoder();
                        byte[] decodedBytes = decoder.decode(buffer);

                        bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                    } catch (Exception e) {
                        Log.d("sdafas", "asdfsadf");
                        break;
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("bmp", bmp + "");
                            iv_droneView.setImageBitmap(Bitmap.createScaledBitmap(bmp, bmp.getWidth(), bmp.getHeight(), false));
                            Log.d("img 불러오기", "success");
                        }
                    });

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    socket.close();
                    Log.d("socket close", "socket close()");
                    // connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        checkUpdate.start();
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resbytes;
    }

}