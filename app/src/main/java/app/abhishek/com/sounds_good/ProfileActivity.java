package app.abhishek.com.sounds_good;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import java.io.File;
import java.util.Date;

/**
 * Created by Abhishek on 5/9/15.
 */
public class ProfileActivity extends Activity {


    protected TextView uEmail;
    protected TextView userN;
    protected ImageView prImage;
    boolean flag_photo = false;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        uEmail =(TextView)findViewById(R.id.uEmail);
        userN =(TextView)findViewById(R.id.userN);

        prImage=(ImageView)findViewById(R.id.pImage);

        final ImageButton camera = (ImageButton)findViewById(R.id.camera);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
                startActivityForResult(intent, CAPTURE_IMAGE);
                flag_photo = true;
            }
        });


        //  listen to pImage button
         prImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),1);

            }

        });

        ParseUser parseUser = ParseUser.getCurrentUser();
        {

            userN.setText(parseUser.getUsername());
            uEmail.setText(parseUser.getEmail());

        }


    }

   public void onActivityResult(int reqCode,int resCode, Intent data){
        if (resCode == RESULT_OK){
            if(reqCode ==1)
                prImage.setImageURI(data.getData());
        }

    }
    private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }
    // used this http://stackoverflow.com/questions/20934998/upload-image-in-parse-com-as-backend
    private Uri getImageUri(String path) {
        return Uri.fromFile(new File(path));
    }

    public Uri setImageUri() {

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".jpg");
            Uri imgUri = Uri.fromFile(file);
            this.imgPath = file.getAbsolutePath();
            return imgUri;
        }
        else {
            File file = new File(new ProfileActivity().getApplicationContext().getFilesDir() , "image" + new Date().getTime() + ".jpg");
            Uri imgUri = Uri.fromFile(file);
            this.imgPath = file.getAbsolutePath();
            return imgUri;
        }
    }

    public String getImagePath() {
        return imgPath;
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        switch (id) {
            case R.id.updateStatus:
                //take user to update status activity
                Intent intent = new Intent(this, UpdateStatusActivity.class);
                startActivity(intent);

                break;
            case R.id.audio:
                //allow the user to upload audio
                //ParseUser.logOut();

                //take the user to audio comment
                Intent takeUserToAudio = new Intent(this, AudioActivity.class);
                startActivity(takeUserToAudio);

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