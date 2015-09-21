package app.abhishek.com.sounds_good;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;


public class RegisterActivity extends Activity {

    protected EditText mUsername;
    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected EditText mLike;
    protected EditText mHate;
    protected Button mRegisterButton;
   // protected ImageView prImage;
    protected  ByteArrayOutputStream bytearray;
    Bitmap bmp;
    Intent i;
    Uri BmpFileName = null;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        // Enable Local Datastore.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername =(EditText)findViewById(R.id.usernameRegisterEditText);
        mUserEmail =(EditText)findViewById(R.id.emailRegisterEditText);
        mUserPassword =(EditText)findViewById(R.id.passwordRegisterEditText);
        mLike =(EditText)findViewById(R.id.like);
        mHate =(EditText)findViewById(R.id.hate);
        mRegisterButton=(Button)findViewById(R.id.registerButton);

       // prImage=(ImageView)findViewById(R.id.pImage);
        //
        /*listen to pImage button
        prImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),1);

            }

        });*/

        /*FileInputStream fileInputStream = null;
        File fileObj = new File(pI);
        byte[] data = new byte[(int) fileObj.length()];

        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(fileObj);
            fileInputStream.read(data);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       //Bitmap bm = BitmapFactory.decodeResource(getResources(),R.id.pImage);
       //ByteArrayOutputStream stream = new ByteArrayOutputStream();
       //bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //I was planning to allow user to login with image upload. but it did not work. I have
        // not removed these comment code, I want to keep them for my future reference.
        //prImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        //byte[] data = stream.toByteArray();
       // byte[] data = prImage.toByteArray();
        //ParseFile image = new ParseFile(String.valueOf(prImage), data);
        //image.saveInBackground();


        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //toast
                //get the username,password and email and convert them to string
                String username = mUsername.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();
                String email = mUserEmail.getText().toString().trim();
                String like = mHate.getText().toString().trim();
                String hate = mLike.getText().toString().trim();
                //byte[] data = mHate.getBytes();

                //ParseFile picture = new ParseFile("picture.mp3", data);
                //picture.saveInBackground();
                //I was planning to allow user to login with image upload. but it did not work. I have
                // not removed these comment code, I want to keep them for my future reference.
                //byte[] data = like.getBytes();
                //ParseFile file = new ParseFile("likes", data);
                //byte[] datas = hate.getBytes();
                //ParseFile files = new ParseFile("hates", datas);

                /*
                 Drawable drawable = res.getDrawable(R.drawable.myimage);
                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] data = stream.toByteArray();

                ParseFile file = new ParseFile("resume.txt", data);
                 file.saveInBackground();

                */

                ParseUser user = new ParseUser();
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
               // user.put("photo",picture);
               // user.put("likes",file);
                //user.put("hates",files);


                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            //user signed up successfully
                            Toast.makeText(RegisterActivity.this, "Success welcome", Toast.LENGTH_LONG).show();
                            //take user homepage
                            Intent takeUserHome = new Intent(RegisterActivity.this, HomepageActivity.class);
                            startActivity(takeUserHome);

                        } else {
                            //there was an error
                        }
                    }
                });
            }
        });


    }

        /*  public void onActivityResult(int reqCode,int resCode, Intent data){
        if (resCode == RESULT_OK){
            if(reqCode ==1)
                prImage.setImageURI(data.getData());
        }

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
