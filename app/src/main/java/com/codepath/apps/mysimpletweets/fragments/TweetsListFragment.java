package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v7.app.ActionBarActivity;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by celder on 3/15/15.
 */
public class TweetsListFragment extends Fragment {

    private TwitterClient client;

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;




    // creation lifecyle event
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timeline);

        // create data source
        tweets = new ArrayList<>();
        // construct adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity()   , tweets);
    }

    // inflation logic
    // TODO: possibly refactor! see guide
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        // find listview
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        client = TwitterApplication.getRestClient(); // singletonClient

        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                client.getOffsetTimeline(new JsonHttpResponseHandler() {

                                             public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                                                 Log.d("DEBUG", "getOffsetTimeline: json response: " + json.toString());
                                                 aTweets.addTweets(Tweet.fromJSONArray(json));
                                             }
                                             @Override
                                             public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                                                 if (errorResponse != null) {
                                                     Log.d("DEBUG", errorResponse.toString());
                                                 }
                                             }
                                         },
                        aTweets.getMinUidSeen());
            }
        });

        return v;
    }


    public void addAll(List<Tweet> tweets) {
        aTweets.addTweets(tweets);
    }
}

