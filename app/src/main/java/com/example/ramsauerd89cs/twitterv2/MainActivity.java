package com.example.ramsauerd89cs.twitterv2;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.Context;

import io.fabric.sdk.android.Fabric;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;




//public class MainActivity extends ActionBarActivity {
public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "hR514DwzMx1ZDYybcykdHW9lu";
    private static final String TWITTER_SECRET = "cNPoxwgmPz8PAMfBGrGT4YISqJ0DULy4tU3fOHll2VeIenr8cE";
//    private static final String TWITTER_KEY = "294011877-NrnspQvsv16S6aU5oeIDoHWssdIUEPx9HYp6BfjC";
//    private static final String TWITTER_SECRET = "JaSX0Yv41pFsID2yk9AUzEguFYa2gwMoSKDGJ659lMwnE";
    private TwitterLoginButton loginButton;
    TwitterApiClient twitterApiClient;
    // Can also use Twitter directly: Twitter.getApiClient()
    StatusesService statusesService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        twitterApiClient = TwitterCore.getInstance().getApiClient();
        // Can also use Twitter directly: Twitter.getApiClient()
        statusesService = twitterApiClient.getStatusesService();
        setContentView(R.layout.activity_main);

        final Context context = getApplicationContext();


        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            CharSequence text;
            int duration;
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                //context = getApplicationContext();
                text = "logged in";
                duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                //context = getApplicationContext();
                text = "not logged in";
                duration = Toast.LENGTH_SHORT;
                Toast.makeText(context, text, duration).show();
            }
        });

       /* statusesService.show(524971209851543553L, null, null, null, new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {*/
                //Do something with result, which provides a Tweet inside of result.data
                final LinearLayout myLayout = (LinearLayout) findViewById(R.id.tweetLayout);

                final long tweetId = 510908133917487104L;
                TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        myLayout.addView(new TweetView(MainActivity.this, result.data));
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        String text = "no results";
                        int duration = Toast.LENGTH_SHORT;
                        Toast.makeText(context, text, duration).show();
                    }
               });
           /* }

            public void failure(TwitterException exception) {
                //Do something on failure
            }
        });*/


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
