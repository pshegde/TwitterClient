package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utilities.TwitterUtilities;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

public class ComposeTweetActivity extends ActionBarActivity {

    private ImageView ivUserProfilePic;
    private TextView tvScreenName;
    private EditText etComposeTweet;
    private TextView tvFirstName;
    private Button btTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        ivUserProfilePic = (ImageView) findViewById(R.id.ivUserProfilePic);
        tvFirstName = (TextView) findViewById(R.id.tvFirstName);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        etComposeTweet = (EditText) findViewById(R.id.etComposeText);
        btTweet = (Button) findViewById(R.id.btTweet);
        updateUserName();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose_tweet, menu);
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

    private void updateUserName() {
        TwitterUtilities.getRestClient().getUserCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User auth_user = User.fromJSON(response);
                tvScreenName.setText(auth_user.getScreenName());
                tvFirstName.setText(auth_user.getName().toString());
                Picasso.with(getBaseContext()).load(auth_user.getProfileImageUrl()).into(ivUserProfilePic);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(getBaseContext(), "No username found", Toast.LENGTH_SHORT).show();
                Log.d("DEBUG", errorResponse.toString());
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}