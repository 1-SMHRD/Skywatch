package com.moon.skywatch.ui.connection6;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class connection6ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public connection6ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is connection6 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

