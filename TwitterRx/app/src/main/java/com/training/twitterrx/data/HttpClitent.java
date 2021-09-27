package com.training.twitterrx.data;

import android.util.Log;

import com.training.twitterrx.models.FakeTweetModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpClitent {

    private static final String TAG = "ss";
    private static HttpClitent INSTANCE;

    private HttpClitent(){
    }

    public static HttpClitent getINSTANCE(){
        if(INSTANCE == null){
            INSTANCE = new HttpClitent();
        }
        return INSTANCE;
    }

    public List<FakeTweetModel> makeServiceCall(String reqUrl) {

        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
            conn.disconnect();
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return parseJson(response);
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        sb.append("{").append('\n');
        sb.append("\"posts\": ");

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            sb.append('\n').append("}");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    private List<FakeTweetModel> parseJson(String jsonStr) {
        List<FakeTweetModel> tweetList = new ArrayList<>();
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                Log.d(TAG, "parseJson: got object");

                JSONArray tweets = jsonObj.getJSONArray("posts");
                Log.d(TAG, "parseJson: got array");

                for (int i = 0; i < tweets.length(); i++) {
                    JSONObject tweet = tweets.getJSONObject(i);
                    tweetList.add(new FakeTweetModel(tweet.getInt("userId"),
                            tweet.getString("title"), tweet.getString("body")));
                }
            } catch (JSONException e) {
                Log.d(TAG, "parseJson: Error parsing this message");
            }
        }
        return tweetList;
    }

}

