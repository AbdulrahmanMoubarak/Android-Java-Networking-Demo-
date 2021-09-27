package com.training.twitterrx.models;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.training.twitterrx.data.TweetsDao;

@Database(entities = FakeTweetModel.class, version = 1)
public abstract class TweetsDatabase extends RoomDatabase {
    private static TweetsDatabase INSTANCE;
    public abstract TweetsDao dao();

    public TweetsDatabase(){

    }

    public static synchronized TweetsDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TweetsDatabase.class, "tweets_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
