package com.training.twitterrx.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.training.twitterrx.models.FakeTweetModel;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface TweetsDao {
    @Insert
    Completable insertTweet(FakeTweetModel tweetModel);

    @Query("SELECT * FROM tweets_table")
    Observable<List<FakeTweetModel>> getTweets();

    @Query("DELETE FROM tweets_table")
    Completable removeAllTweets();
}
