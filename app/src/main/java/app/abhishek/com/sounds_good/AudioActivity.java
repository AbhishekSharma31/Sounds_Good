package app.abhishek.com.sounds_good;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by Abhishek on 5/5/15.
 */
public class AudioActivity extends Activity {

    protected Button newfile, retrieve, d_startrec, d_stoprec, d_saverec, d_cancelrec;
    protected TextView d_countdown;
    protected String outputFile = null;
    protected MediaRecorder myRecorder;
    protected LinearLayout llrec, llsave;
    protected CountDownTimer countDownTimer;
    protected Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        newfile = (Button) findViewById(R.id.bttn);
        retrieve = (Button) findViewById(R.id.button2);

        newfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_main_dialog);
                dialog.setTitle("New Audio File");

                llrec = (LinearLayout) dialog.findViewById(R.id.d_llrec);
                llsave = (LinearLayout) dialog.findViewById(R.id.d_llsave);
                d_startrec = (Button) dialog.findViewById(R.id.d_startrec);
                d_stoprec = (Button) dialog.findViewById(R.id.d_stoprec);
                d_saverec = (Button) dialog.findViewById(R.id.d_salvarec);
                d_cancelrec = (Button) dialog.findViewById(R.id.d_annullarec);
                d_countdown = (TextView) dialog.findViewById(R.id.d_tvcountdown);

                llrec.setVisibility(View.VISIBLE);
                d_startrec.setVisibility(View.VISIBLE);
                d_stoprec.setVisibility(View.GONE);
                llsave.setVisibility(View.GONE);


                outputFile = Environment.getExternalStorageDirectory().
                        getAbsolutePath() + "/rumor.mp3";

                myRecorder = new MediaRecorder();
                myRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                myRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
                myRecorder.setOutputFile(outputFile);

                countDownTimer = null;
                d_countdown.setText("20 s");

                d_startrec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d_startrec.setVisibility(View.GONE);
                        d_stoprec.setVisibility(View.VISIBLE);
                        llsave.setVisibility(View.GONE);

                        dialog.setCancelable(true);

                        countDownTimer = new CountDownTimer(20000, 1000) {

                            public void onTick(long millisUntilFinished) {
                                d_countdown.setText(millisUntilFinished / 1000 + " s");
                            }

                            public void onFinish() {
                                d_countdown.setText("Time Out!");
                                d_startrec.setVisibility(View.GONE);
                                d_stoprec.setVisibility(View.GONE);
                                llsave.setVisibility(View.VISIBLE);

                                try {
                                    myRecorder.stop();
                                    myRecorder.release();
                                    myRecorder = null;

                                } catch (IllegalStateException e) {
                                    //  it is called before start()
                                    e.printStackTrace();
                                } catch (RuntimeException e) {
                                    // no valid audio/video data has been received
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        try {
                            myRecorder.prepare();
                            myRecorder.start();
                        } catch (IllegalStateException e) {
                            // start:it is called before prepare()
                            // prepare: it is called after start() or before setOutputFormat()
                            e.printStackTrace();
                        } catch (IOException e) {
                            // prepare() fails
                            e.printStackTrace();
                        }
                    }
                });

                d_stoprec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        countDownTimer.cancel();
                        dialog.setCancelable(false);
                        d_startrec.setVisibility(View.GONE);
                        d_stoprec.setVisibility(View.GONE);
                        llsave.setVisibility(View.VISIBLE);
                        try {
                            myRecorder.stop();
                            myRecorder.release();
                            myRecorder = null;

                        } catch (IllegalStateException e) {
                            //  it is called before start()
                            e.printStackTrace();
                        } catch (RuntimeException e) {
                            // no valid audio/video data has been received
                            e.printStackTrace();
                        }
                    }
                });

                d_saverec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FileInputStream fileInputStream = null;
                        File fileObj = new File(outputFile);
                        byte[] data = new byte[(int) fileObj.length()];

                        try {
                            //convert file into array of bytes
                            fileInputStream = new FileInputStream(fileObj);
                            fileInputStream.read(data);
                            fileInputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        ParseFile parseAudioFile = new ParseFile("audiofile.mp3", data);
                        parseAudioFile.saveInBackground();
                        //get current user
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        String currentUserUsername = currentUser.getUsername();

                        ParseObject parseObject = new ParseObject("AudioClass");
                        parseObject.put("user", currentUserUsername);
                        parseObject.put("audiofile", parseAudioFile);
                        parseObject.saveInBackground(new SaveCallback() {
                            public void done(ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "Audio file saved successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(),"Error: audio file not saved", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void done(com.parse.ParseException e) {

                            }
                        });

                        dialog.dismiss();
                    }
                });

                d_cancelrec.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(dialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                dialog.show();
                dialog.getWindow().setAttributes(lp);

            }
        });

        retrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AudioFilesList.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

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






