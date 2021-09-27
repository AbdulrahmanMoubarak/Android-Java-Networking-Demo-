package com.training.twitterrx.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tweets_table")
public class FakeTweetModel {
    @PrimaryKey(autoGenerate = true)
    private int userId;

    private String title;
    private String body;

    public FakeTweetModel() {
    }

    public FakeTweetModel(int userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
