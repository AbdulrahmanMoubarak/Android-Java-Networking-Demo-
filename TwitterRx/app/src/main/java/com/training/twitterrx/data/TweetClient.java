package com.training.twitterrx.data;

import com.training.twitterrx.models.FakeTweetModel;

import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TweetClient {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private TweetInterface tweet_interface;
    private static TweetClient INSTANCE;
    private static String type = "";

    private TweetClient(String type) {
        this.type = type;
        Retrofit retro;
        if(type.equalsIgnoreCase("call")) {
            retro = createRetrofitWithCall();
        }
        else if (type.equalsIgnoreCase("rx"))
        {
            retro = createRetrofitWithRxAdapter();
        }
        else{
            retro = createRetrofitWithCall();
        }
        tweet_interface = retro.create(TweetInterface.class);
    }

    public static TweetClient getINSTANCE(String type) {
        if (INSTANCE == null || !type.equalsIgnoreCase(TweetClient.type)) {
            INSTANCE = new TweetClient(type);
        }
        return INSTANCE;
    }

    private Retrofit createRetrofitWithCall(){
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retro;
    }

    private Retrofit createRetrofitWithRxAdapter(){
        Retrofit retro = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        return retro;
    }

    public Call<List<FakeTweetModel>> getTweets() {
        return tweet_interface.getTweets();
    }

    public Single<List<FakeTweetModel>> getTweetsRx() {
        return tweet_interface.getTweetsRx();
    }
}
