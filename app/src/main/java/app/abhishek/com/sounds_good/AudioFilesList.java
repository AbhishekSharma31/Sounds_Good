package app.abhishek.com.sounds_good;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;

/**
 * Created by Abhishek on 5/7/15.
 */
public class AudioFilesList extends Activity {
    ListView listView;
    AudioFileList_Adapter adapter;
    TextView empty;
    //took help from https://parse.com/tutorials/parse-query-adapter


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_files_list);

        empty = (TextView) findViewById(R.id.tv_empty);
        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(empty);
        adapter = new AudioFileList_Adapter(getApplicationContext());
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.home:

                //allow the user to go to home
                //ParseUser.logOut();

                Intent takeUserToHome = new Intent(this, HomepageActivity.class);
                startActivity(takeUserToHome);

                break;

            case R.id.audio:
                //take the user to audio
                //ParseUser.logOut();

                Intent takeUserToAudio = new Intent(this, AudioActivity.class);
                startActivity(takeUserToAudio);

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



