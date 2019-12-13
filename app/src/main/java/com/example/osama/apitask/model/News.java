package com.example.osama.apitask.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.osama.apitask.BR;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class News extends BaseObservable implements Serializable {
    @SerializedName("title")
    @Bindable
    private final String title;

    @SerializedName("byline")
    @Bindable
    private final String byline;

    @SerializedName("published_date")
    @Bindable
    private final String published_date;

    @SerializedName("multimedia")
    @Bindable
    private final List<Multimedia> multimedia;


    public News(String byline, String title, String published_date, List<Multimedia> multimedia) {
        this.title = title;
        this.byline = byline;
        this.published_date = published_date;
        this.multimedia = multimedia;

        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.byline);
        notifyPropertyChanged(BR.published_date);
        notifyPropertyChanged(BR.multimedia);
    }

    public String getTitle() {
        return title;
    }

    public String getByline() {
        return byline;
    }

    public String getPublished_date() {
        return published_date;
    }

    public List<Multimedia> getMultimedia(){
        return multimedia;
    }
}
