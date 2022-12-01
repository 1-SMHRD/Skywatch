package com.moon.skywatch.ui.drone;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DroneViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DroneViewModel() {
        mText = new MutableLiveData<>();
    }
    public LiveData<String> getText() {
        return mText;
    }
}