package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by celder on 3/17/15.
 */
public class MentionsTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the client
        client = TwitterApplication.getRestClient(); // singletonClient
        populateTimeline();
    }

    // send api request to get timeline json
    // fill listview by creating tweet objects from json
    private void populateTimeline() {
        client.getMentionsTimeline(new JsonHttpResponseHandler() {
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
