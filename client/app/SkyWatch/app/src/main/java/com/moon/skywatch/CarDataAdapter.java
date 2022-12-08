package com.moon.skywatch;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CarDataAdapter extends RecyclerView.Adapter<CarDataViewHolder> {

    Context mContext;
    ArrayList<CarDataVO> carData;

    public CarDataAdapter(Context mContext, ArrayList<CarDataVO> carData) {
        this.mContext = mContext;
        this.carData = carData;
    }

    @NonNull
    @Override
    public CarDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CarDataViewHolder holder = new CarDataViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.carlisttemplete, parent, false)
        );

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarDataViewHolder holder, int position) {
        final int temp = position;

        holder.imgCarParking1.setImageBitmap(Bitmap.createScaledBitmap(carData.get(temp).getImgParking1(), 200, 200, false));
        holder.imgCarParking2.setImageBitmap(Bitmap.createScaledBitmap(carData.get(temp).getImgParking2(), 200, 200, false));
        holder.imgNumPlate.setImageBitmap(Bitmap.createScaledBitmap(carData.get(temp).getImgNumPlate(), 200, 200, false));

        holder.tv_date.setText(carData.get(temp).getRegulationDate());
        holder.tv_time.setText(carData.get(temp).getRegulationTime());
        holder.tv_carNum.setText(carData.get(temp).getNumPlate());
        holder.tv_area.setText(carData.get(temp).getRegulationArea());
    }

    /*@Override
    public void onBindViewHolder(@NonNull CarDataViewHolder holder, int position, @NonNull List<Object> payloads) {

    }*/

    @Override
    public int getItemCount() {
        return carData.size();
    }
}
