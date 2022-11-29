package com.moon.skywatch.ui.number4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class number4ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public number4ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is number4 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}