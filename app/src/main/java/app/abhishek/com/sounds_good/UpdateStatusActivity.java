package app.abhishek.com.sounds_good;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class UpdateStatusActivity extends Activity {


    protected EditText mStatusUpdate;
    protected Button mstatusUpdateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        //initialize
        mStatusUpdate =(EditText)findViewById(R.id.updateStatusTextBox);
        mstatusUpdateBtn=(Button)findViewById(R.id.statusUpdateButton);

        //listen to status update button click
        mstatusUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get current user
                ParseUser currentUser = ParseUser.getCurrentUser();
                String currentUserUsername = currentUser.getUsername();
                //get the status user has enter, convert it to a string.
                String newStatus = mStatusUpdate.getText().toString();
                if(newStatus.isEmpty()){

                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
                    builder.setMessage("Status should not be empty");
                    builder.setTitle("Oops");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //close the dialog
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {

                    //save it to cloud or parse
                    ParseObject statusObject = new ParseObject("Status");
                    statusObject.put("newStatus", newStatus);
                    statusObject.put("user", currentUserUsername);
                    statusObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                //succesfully stored new status in parse
                                Toast.makeText(UpdateStatusActivity.this, "Success!", Toast.LENGTH_LONG).show();

                                //take user to the homepage
                                Intent takeUserHome = new Intent(UpdateStatusActivity.this, HomepageActivity.class);
                                startActivity(takeUserHome);

                            } else {
                                //there was a problem storing new status. advice user
                                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateStatusActivity.this);
                                builder.setMessage(e.getMessage());
                                builder.setTitle("Sorry!");
                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //close the dialog
                                        dialogInterface.dismiss();
                                    }
                                });
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });

                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_status, menu);
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
                //allow the user to home


                Intent takeUserToHome = new Intent(this, HomepageActivity.class);
                startActivity(takeUserToHome);

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
