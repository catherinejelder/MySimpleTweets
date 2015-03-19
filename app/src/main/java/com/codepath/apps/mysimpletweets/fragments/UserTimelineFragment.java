package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.util.Log;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by celder on 3/19/15.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    // Creates a new fragment given an int and title
    // DemoFragment.newInstance(5, "Hello");
    public static UserTimelineFragment newInstance(String screen_name) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        userFragment.setArguments(args);
        return userFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the client
        client = TwitterApplication.getRestClient(); // singletonClient
        populateTimeline();
    }

    // send api request to get timeline json
    // fill listview by creating tweet objects from json
    private void populateTimeline() {
        //get screen name
        String screen_name = getArguments().getString("screen_name");

        client.getUserTimeline(screen_name, new JsonHttpResponseHandler() {
            // success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                //super.onSuccess(statusCode, headers, response);
                Log.d("DEBUG", "populateTimeline: json response: " + json.toString());
                // deserialize json
                // create models
                // load model data into listview
                // aTweets.addTweets(Tweet.fromJSONArray(json));
                addAll(Tweet.fromJSONArray(json));
                Log.d("DEBUG", this.toString());
            }

            // failure

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // super.onFailure(statusCode, headers, throwable, errorResponse);
                if (errorResponse != null) {
                    Log.d("DEBUG", errorResponse.toString());
                }
            }
        });
    }
}
