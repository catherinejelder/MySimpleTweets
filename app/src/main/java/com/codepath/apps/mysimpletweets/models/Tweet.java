package com.codepath.apps.mysimpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by celder on 3/10/15.
 *
 *
 */

// parse JSON, store data, encapsulate state logic or display logic
public class Tweet {
    // list attributes
    private String body;
    private long uid; // unique id for tweet (database id)
    private User user;
    private String createdAt;

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    // deserialize json, turn into POJO
    public static Tweet fromJSON(JSONObject jsonObj) {
        Tweet tweet = new Tweet();
        // extract json data
        try {
            tweet.body = jsonObj.getString("text");
            tweet.uid = jsonObj.getLong("id");
            tweet.createdAt = jsonObj.getString("created at");
            tweet.user = User.fromJSON(jsonObj.getJSONObject("user"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        // iterate json array and create tweets
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
}
