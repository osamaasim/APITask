package com.example.osama.apitask.clickHandler;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.osama.apitask.Activities.DetailActivity;
import com.example.osama.apitask.model.News;

public class DetailsIntent {
    private final Context context;

    public DetailsIntent(Context context) {
        this.context = context;
    }

    public void onSaveClick(View view, News news) {
        Intent nextInt = new Intent(view.getContext(), DetailActivity.class);
        nextInt.putExtra("SELECTED_NEWS",news);
        context.startActivity(nextInt);
    }
}
