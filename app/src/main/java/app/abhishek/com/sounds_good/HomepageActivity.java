package app.abhishek.com.sounds_good;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class HomepageActivity extends ListActivity {

    protected List<ParseObject> mStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // show user the homepage
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> status, ParseException e) {
                    if(e == null){
                        //success

                        mStatus = status;

                        StatusAdapter adapter = new StatusAdapter(getListView().getContext(),mStatus);
                        setListAdapter(adapter);

                    }else{
                        //there was a problem. Alert User
                    }
                }
            });



        } else {
            // show the signup or login screen
            Intent takeUserToLogin = new Intent(this, LoginActivity.class);
            startActivity(takeUserToLogin);
        }
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

        switch(id){
            case R.id.updateStatus:
                //take user to update status activity
                Intent intent = new Intent(this, UpdateStatusActivity.class);
                startActivity(intent);

                break;
            case R.id.audio:
                //allow the user to upload audio


                //take the user to audio comment
                Intent takeUserToAudio = new Intent(this,AudioActivity.class);
                startActivity(takeUserToAudio);

                break;

            case R.id.profile:
                //allow the user to profile


                Intent takeUserToProfile = new Intent(this,ProfileActivity.class);
                startActivity(takeUserToProfile);

                break;

            case R.id.logoutUser:
                //logout the user
                ParseUser.logOut();

                //take the user back to the login screen
                Intent takeUserToLogin = new Intent(this,LoginActivity.class);
               takeUserToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                takeUserToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(takeUserToLogin);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        ParseObject statusObject = mStatus.get(position);
        String objectId = statusObject.getObjectId();

        Intent goToDetailView = new Intent(HomepageActivity.this, StatusDetailView.class);
        goToDetailView.putExtra("objectID",objectId);
        startActivity(goToDetailView);
    
    }
}

