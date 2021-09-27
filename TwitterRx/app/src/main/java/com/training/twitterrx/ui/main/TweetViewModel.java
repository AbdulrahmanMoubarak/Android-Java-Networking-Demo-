package com.training.twitterrx.ui.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.training.twitterrx.data.HttpClitent;
import com.training.twitterrx.data.OkHttpCreator;
import com.training.twitterrx.data.TweetClient;
import com.training.twitterrx.models.FakeTweetModel;
import com.training.twitterrx.models.TweetsDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TweetViewModel extends AndroidViewModel {

    private static final String TAG = "Connection";
    MutableLiveData<List<FakeTweetModel>> liveTweets = new MutableLiveData<>();
    CompositeDisposable dis = new CompositeDisposable();
    private Context context = getApplication().getApplicationContext();
    TweetsDatabase tweetsDatabase;

    public TweetViewModel(Application application) {
        super(application);
        tweetsDatabase = TweetsDatabase.getInstance(context);
    }

    public void getTweetsByRetrofit() {
        TweetClient.getINSTANCE("call").getTweets().enqueue(new Callback<List<FakeTweetModel>>() {
            @Override
            public void onResponse(Call<List<FakeTweetModel>> call, Response<List<FakeTweetModel>> response) {
                liveTweets.setValue(response.body());
                Log.d(TAG, "xfilter onSuccess: successful connection ");
            }

            @Override
            public void onFailure(Call<List<FakeTweetModel>> call, Throwable t) {
                Log.d(TAG, "xfilter onFailure: connection failed");
            }
        });
    }

    public void getTweetsByRetrofitRX() {
        Single<List<FakeTweetModel>> observable = TweetClient.getINSTANCE("rx").getTweetsRx()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        dis.add(observable.subscribe(o -> liveTweets.postValue(o), e -> Log.d(TAG, "onError: " + e.getMessage())));
    }

    public void getTweetsByNativeHttp() {
        List<FakeTweetModel> tweets = new ArrayList<>();
        String urlString = TweetClient.BASE_URL + "posts/";
        Single<List<FakeTweetModel>> observable = Single.create(new SingleOnSubscribe<List<FakeTweetModel>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<FakeTweetModel>> emitter) throws Throwable {
                emitter.onSuccess(HttpClitent.getINSTANCE().makeServiceCall(urlString));
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
        dis.add(observable.subscribe(s -> liveTweets.postValue(s), throwable -> Log.d(TAG, "getTweetsByNativeHttp: " + throwable.getMessage())));
    }

    public void getTweetsByOkHttp() {
        List<FakeTweetModel> tweets = new ArrayList<>();
        String urlString = TweetClient.BASE_URL + "posts/";

        Single<List<FakeTweetModel>> observable = Single.create(new SingleOnSubscribe<List<FakeTweetModel>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<FakeTweetModel>> emitter) throws Throwable {
                emitter.onSuccess(OkHttpCreator.getInstance(urlString).getDataFromApi());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        dis.add(observable.subscribe(s -> liveTweets.postValue(s), throwable -> Log.d(TAG, "getTweetsByNativeHttp: " + throwable.getMessage())));
    }

    public void insertToTweetsTable(FakeTweetModel tweet){

        tweetsDatabase.dao().insertTweet(tweet).subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Insertion complete");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: Error inserting data");
            }
        });
    }

    public void retrieveFromTweetsTable(){
        tweetsDatabase.dao().getTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> liveTweets.postValue(o), throwable -> Log.d(TAG, "retrieveFromTweetsTable: error retrieving data"));
    }

    public void clearTweetsTable(){
        tweetsDatabase.dao().removeAllTweets().subscribeOn(Schedulers.io()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: DeletionComplete");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: Error clearing records");
            }
        });
    }

    @Override
    protected void onCleared(){
        super.onCleared();
        dis.clear();
    }
}
