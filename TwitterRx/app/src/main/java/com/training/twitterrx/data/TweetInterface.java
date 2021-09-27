package com.training.twitterrx.data;

import com.training.twitterrx.models.FakeTweetModel;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TweetInterface {

    @GET("posts")
    public Call<List<FakeTweetModel>> getTweets();

    @GET("posts")
    public Single<List<FakeTweetModel>> getTweetsRx();

}
