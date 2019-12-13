package com.example.osama.apitask.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osama.apitask.ArticleApplication;
import com.example.osama.apitask.model.APIHandler;
import com.example.osama.apitask.model.News;
import com.example.osama.apitask.R;
import com.example.osama.apitask.Server.ApiClient;
import com.example.osama.apitask.Server.ApiInterface;
import com.example.osama.apitask.Utils.ApiKey;
import com.example.osama.apitask.Utils.ConnecUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.osama.apitask.Utils.ApiKey.API_KEY;

public class MainActivity extends AppCompatActivity {

    private TextView noDataTv;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private Type listType;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inits();

        if (ConnecUtil.isOnline(this)) {
            callApi("all", "all"); // for section - all-sections, sports, international
        } else {
            Toast.makeText(MainActivity.this, "No network connection. Loaded Offline data", Toast.LENGTH_LONG).show();
            getSavedData();
        }
    }

    private void inits() {
        listType = new TypeToken<List<News>>() {
        }.getType();
        gson = new Gson();

        progressBar =  findViewById(R.id.progressBar);
        noDataTv =  findViewById(R.id.textViewNoData);
        recyclerView =  findViewById(R.id.recycler_news);
    }

    private void callApi(String section, String period) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<APIHandler> call = apiService.getNewsDetails(section, period, API_KEY);
        Log.d("TAG", String.valueOf(API_KEY));
        call.enqueue(new Callback<APIHandler>() {
            @Override
            public void onResponse(Call<APIHandler> call, Response<APIHandler> response) {

                int statusCode = response.code();

                if (statusCode == 200) {
                    List<News> newsList = response.body().getResults();
                    saveData(newsList);
                } else {
                    showError("Server Problem. Try again!");
                }
            }

            @Override
            public void onFailure(Call<APIHandler> call, Throwable t) {
                showError(t.getMessage());
            }
        });
    }

    private void saveData(List<News> newsList) {
        //LIST DATA CONVERT TO GSON STRING
        String gsonStr = gson.toJson(newsList, listType);
        Log.d("TAG", String.valueOf(gsonStr));
        //SAVE IN SHARED-PREFERENCE
        ArticleApplication.setNewsList(this, gsonStr);

        getSavedData();
    }

    private void getSavedData() {
        //GET SAVED DATA
        String gsonList = ArticleApplication.getNewsList(this);

        if (!gsonList.equals("n/a")) {
            //CONVERT TO LIST
            List<News> newsList = gson.fromJson(gsonList, listType);

            setupRecycler(newsList);
        } else {
            showError("No saved news to display...!");
        }
    }

    private void setupRecycler(List<News> dataList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new NewsAdapter(this, dataList));

        assert dataList != null;
        if (dataList.size() > 0) {
            dataVisible();
        } else {
            showError("No News..!");
        }
    }

    private void showError(String message) {
        noDataTv.setText(message);

        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        noDataTv.setVisibility(View.VISIBLE);
    }

    private void dataVisible() {
        noDataTv.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
