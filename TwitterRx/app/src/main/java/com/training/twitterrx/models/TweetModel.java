package com.training.twitterrx.models;

public class TweetModel {
    private String created_at;
    private int id;
    private String id_str;
    private String text;
    private boolean favorited;
    private int favorite_count;
    private UserModel user;

    public TweetModel(
            String created_at,
            int id, String id_str,
            String text, boolean favorited,
            int favorite_count,
            UserModel user) {

        this.created_at = created_at;
        this.id = id;
        this.id_str = id_str;
        this.text = text;
        this.favorited = favorited;
        this.favorite_count = favorite_count;
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
