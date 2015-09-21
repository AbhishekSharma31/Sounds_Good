package app.abhishek.com.sounds_good;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

/**
 * Created by Abhishek on 5/3/15.
 */
public class StatusDetailView extends Activity {

    String objectId;
    protected TextView mStatus;
    protected TextView userN;
    protected TextView timeT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_detail_view);

        mStatus = (TextView) findViewById(R.id.statusDetailView);
        userN = (TextView) findViewById(R.id.userN);
        timeT=(TextView)findViewById(R.id.timeT);
        //initialize
        //get the intent that started the activity
        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectID");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Status");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {
                if (e == null) {
                    //success we have a status

                    String userStatus = parseObject.getString("newStatus");
                    mStatus.setText(userStatus);
                    String userName = parseObject.getString("user");
                    userN.setText(userName);
                    String createdAt = parseObject.getCreatedAt().toString();

                    timeT.setText(createdAt);

                    //Date date = pObject.getCreatedAt();
                    //Date date = parseObject.getCreatedAt();
                    //timeT.setText(date);

                } else {
                    //some error
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.status_deatil_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
  /*I took a tutorial online course to gain basics of parse and Android Studio

        //https://www.udemy.com/android-app-development-with-parse-and-android-studio-ide/ */
        switch (id) {
            case R.id.updateStatus:
                //take user to update status activity
                Intent intent = new Intent(this, UpdateStatusActivity.class);
                startActivity(intent);

                break;


            case R.id.profile:
                //allow the user to profile
                //ParseUser.logOut();

                Intent takeUserToProfile = new Intent(this, ProfileActivity.class);
                startActivity(takeUserToProfile);

                break;

            case R.id.logoutUser:
                //logout the user
                ParseUser.logOut();

                //take the user back to the login screen
                Intent takeUserToLogin = new Intent(this, LoginActivity.class);
                takeUserToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                takeUserToLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(takeUserToLogin);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}


