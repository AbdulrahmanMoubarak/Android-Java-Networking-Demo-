package com.training.twitterrx.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.training.twitterrx.R;
import com.training.twitterrx.models.FakeTweetModel;

import java.util.ArrayList;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {
    private List<FakeTweetModel> tweet_list = new ArrayList<FakeTweetModel>();

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TweetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
        holder.title.setText(tweet_list.get(position).getTitle());
        holder.user.setText(tweet_list.get(position).getUserId()+"");
        holder.body.setText(tweet_list.get(position).getBody());
    }

    @Override
    public int getItemCount() {
        return tweet_list.size();
    }

    public void setTweet_list(ArrayList<FakeTweetModel> tweet_list) {
        this.tweet_list = tweet_list;
        notifyDataSetChanged();
    }

    class TweetViewHolder extends RecyclerView.ViewHolder {
        TextView title, user, body;

        public TweetViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_textView);
            user = itemView.findViewById(R.id.user_textView2);
            body = itemView.findViewById(R.id.body_textView3);
        }
    }

}
