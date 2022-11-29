package com.moon.moon4.ui.report5;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class report5ViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public report5ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is report5 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}