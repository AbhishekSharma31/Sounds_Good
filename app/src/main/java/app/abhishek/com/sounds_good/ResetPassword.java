package app.abhishek.com.sounds_good;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

/**
 * Created by Abhishek on 5/11/15.
 */


    public class ResetPassword extends Activity {
        String s = "a";
        EditText email;
        Button btn1, btn2;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_resetpassword);
            email = (EditText) findViewById(R.id.resetEmail);
            btn1 = (Button) findViewById(R.id.resetBtn);
            btn2 = (Button) findViewById(R.id.backtologin);
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    s = email.getText().toString();
                    if (s.equals("")) {
                        Toast t = Toast.makeText(getApplicationContext(), R.string.enteremail,
                                Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER, 0, 0);
                        t.show();
                    } else {
                        try {

                            ParseUser.requestPasswordResetInBackground(s, new RequestPasswordResetCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Toast t = Toast.makeText(getApplicationContext(), R.string.linksent,
                                                Toast.LENGTH_LONG);
                                        t.setGravity(Gravity.CENTER, 0, 0);
                                        t.show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
                                        builder.setMessage(e.getMessage()).setTitle(R.string.error).setPositiveButton(R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                }
                            });
                        } catch (Exception ex4) {
                            Log.i("Error : ", ex4.getMessage().toString());
                        }
                    }
                }
            });
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ResetPassword.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });
        }

    }



