package com.example.osama.apitask.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.osama.apitask.BR;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Multimedia extends BaseObservable implements Serializable {
    @SerializedName("url")
    @Bindable
    private final String url;

    @SerializedName("format")
    @Bindable
    private  final String format;



    public Multimedia(String url, String format){
        this.url = url;
        this.format = format;

        notifyPropertyChanged(BR.url);
        notifyPropertyChanged(BR.format);
    }

    public String getUrl() {
        return url;
    }

    public String getFormat() {
        return format;
    }
}
