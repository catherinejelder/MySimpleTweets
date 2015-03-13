package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.apache.http.Header;

import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeTweetActivity extends ActionBarActivity {
    EditText tweetText;
    String tweetStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        Log.d("DEBUG", "ComposeTweetActivity.onCreate called");
        tweetText = (EditText) findViewById(R.id.tweetText);
        tweetStr = "";
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose_tweet, menu);
        return true;
    }

    public void onTweetButtonPress(View v) {
        tweetStr = tweetText.getText().toString();
        Log.i("INFO", "tweet button pressed, text: " + tweetStr);
        // post tweet
        TwitterClient client = TwitterApplication.getRestClient(); // singletonClient
        client.postToTimeline(new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                Log.d("DEBUG", "getOffsetTimeline: json response: " + json.toString());
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.d("DEBUG", errorResponse.toString());
                }
            }
        }, tweetStr);

        // navigate back to tweet stream
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
