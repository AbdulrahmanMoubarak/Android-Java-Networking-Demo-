package com.training.twitterrx.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.training.twitterrx.R;
import com.training.twitterrx.models.FakeTweetModel;
import com.training.twitterrx.util.Demo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TweetViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vm = ViewModelProviders.of(this).get(TweetViewModel.class);

        tryDemo(Demo.ROOM_RX);

        RecyclerView rv = findViewById(R.id.recycler);
        TweetAdapter adapter = new TweetAdapter();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        vm.liveTweets.observe(this, new Observer<List<FakeTweetModel>>() {
            @Override
            public void onChanged(List<FakeTweetModel> fakeTweetModels) {
                adapter.setTweet_list((ArrayList<FakeTweetModel>) fakeTweetModels);
            }
        });

    }

    private void tryDemo(String demo){
        switch (demo){
            case  "retrofit":
                vm.getTweetsByRetrofit();
                break;

            case "retrofitrx":
                vm.getTweetsByRetrofitRX();
                break;

            case "http":
                vm.getTweetsByNativeHttp();
                break;

            case "okhttp":
                vm.getTweetsByOkHttp();
                break;

            case "room":
                vm.insertToTweetsTable(new FakeTweetModel(155, "MyInsert", "this is the body of the tweet"));
                vm.insertToTweetsTable(new FakeTweetModel(15, "NewInsert", "this is the new body"));
                vm.retrieveFromTweetsTable();
                break;

            case "clear":
                vm.clearTweetsTable();
                break;

            default:
                vm.getTweetsByRetrofit();
        }

    }
}