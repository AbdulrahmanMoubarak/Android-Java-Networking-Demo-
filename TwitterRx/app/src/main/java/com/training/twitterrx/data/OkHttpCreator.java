package com.training.twitterrx.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.training.twitterrx.models.FakeTweetModel;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpCreator {
    private static final String TAG = "OkHttp";
    private static OkHttpCreator INSTANCE;
    private final OkHttpClient client = new OkHttpClient();
    private final Request request ;

    private OkHttpCreator(String url){
        request = new Request.Builder()
                .url(url)
                .build();
    }

    public static OkHttpCreator getInstance(String url){
        if(INSTANCE == null){
            INSTANCE = new OkHttpCreator(url);
        }
        return INSTANCE;
    }

    public List<FakeTweetModel> getDataFromApi(){
        List<FakeTweetModel>tweets = new ArrayList<>();
        Response res = null;
        try {
            res = client.newCall(request).execute();
            Log.d(TAG, "getDataFromApi: connection succeeded");
        } catch (IOException e) {
            Log.d(TAG, "getDataFromApi: Connection failed");
            e.printStackTrace();
        }
        if(res != null)
            tweets = parseJson(res);
        return tweets;
    }

    private List<FakeTweetModel> parseJson(Response response){
        List<FakeTweetModel> tweets = new ArrayList<>();
        Moshi moshi = new Moshi.Builder().build();
        Type listMyData = Types.newParameterizedType(List.class, FakeTweetModel.class);
        JsonAdapter<List<FakeTweetModel>> adapter = moshi.adapter(listMyData);
        try {
            tweets = adapter.fromJson(response.body().string());
            Log.d(TAG, "parseJson: parsing succeeded");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "parseJson: Error parsing");
            Log.d(TAG, "parseJson: " + response.message());
        }
        return tweets;
    }
}
