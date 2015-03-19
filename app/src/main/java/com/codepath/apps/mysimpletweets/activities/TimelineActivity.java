package com.codepath.apps.mysimpletweets.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.TweetsListFragment;
import com.codepath.apps.mysimpletweets.models.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends ActionBarActivity {

    private TweetsListFragment fragmentTweetsList;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // get viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // find the pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);
    }


    public void onProfileView(MenuItem mi) {
        // launch profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }


    // TODO: move this logic?
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("DEBUG", "TimelineActivity.onOptionsItemSelected called");
        int id = item.getItemId();

        /**
         *         if (id == R.id.action_settings) {
         // navigate back to searchactivity
         // creating an intent
         Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
         // launch the new activity
         startActivity(i);

         return true;
         }
         */
        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    // return order of fragments in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = {"Home", "Mentions",};

        // adapter get the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        // the order and creation of fragments within pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        // return tab title
        @Override
        public CharSequence getPageTitle(int pos) {
            return tabTitles[pos];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
