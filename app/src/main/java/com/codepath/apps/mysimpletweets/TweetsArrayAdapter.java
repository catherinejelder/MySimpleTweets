package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * take tweet objects, add data to views
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    private long minUidSeen = Long.MAX_VALUE;

    public long getMinUidSeen() {
        return minUidSeen;
    }

    public void setMinUidSeen(long minUidSeen) {
        this.minUidSeen = minUidSeen;
        Log.d("DEBUG", "setting maxUidSeen to: " + getMinUidSeen());
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    // TODO: check out ViewHolder pattern (for performance)

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // get tweet
        Tweet tweet = getItem(pos);
        // find or inflate template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        // find the subviews to fill with data in the template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        // populate data into the subviews
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        // populate relative timestamp
        // add timestamp
        TextView timeCaption = (TextView) convertView.findViewById(R.id.timeCaption);
        String relativeTimeSpanStr = DateUtils.getRelativeTimeSpanString(tweet.getTimestamp() / 1000, new Date().getTime() / 1000, 0).toString();
        Log.i("DEBUG", "tweet timestamp: " + tweet.getTimestamp() + ", current time: " + new Date().getTime());
        Log.i("DEBUG", "setting timeCaption text to: " + relativeTimeSpanStr);
        timeCaption.setText(relativeTimeSpanStr);
        // add photo
        ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image from recycled view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
        // return view to be inserted into the list
        return convertView;
    }

    public void addTweets(ArrayList<Tweet> tweets) {
        Log.d("DEBUG", "adding " + tweets.size() + " tweets to tweets array adapter");

        this.addAll(tweets);
        for (Tweet t: tweets) {
            long uid = t.getUid();
            if (uid < getMinUidSeen()) {
                setMinUidSeen(uid);
            }
        }
        Log.d("DEBUG", "tweets array adapter has " + this.getCount() + " entries");
    }

}
