package app.abhishek.com.sounds_good;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.io.IOException;

/**
 * Created by Abhishek on 5/7/15.
 */
public class AudioFileList_Adapter extends ParseQueryAdapter<ParseObject> {



    public AudioFileList_Adapter(Context context) {

        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {


                ParseQuery query = new ParseQuery("AudioClass");
                query.orderByDescending("createdAt");
                return query;
            }
        });
    }



    @Override
    public View getItemView(final ParseObject object, View v, ViewGroup parent) {

        if (v == null) {
            v = View.inflate(getContext(), R.layout.activity_audio_files_item, null);
        }
        super.getItemView(object, v, parent);

       TextView created =(TextView)v.findViewById(R.id.date);

       //date.setText(object.getDate("createdAt"));
        //Date date = object.getCreatedAt();
        //dated.setText((CharSequence) date);


        String createdAt = object.getCreatedAt().toString();
        created.setText(createdAt);


        TextView tv_user =(TextView)v.findViewById(R.id.user);

        tv_user.setText(object.getString("user"));

        Button play = (Button) v.findViewById(R.id.play);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseFile descr = object.getParseFile("audiofile");

                if (descr != null) {
                    String audioFileURL = descr.getUrl();
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    try {
                        mediaPlayer.setDataSource(audioFileURL);
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IllegalArgumentException e1) {
                        e1.printStackTrace();
                    } catch (SecurityException e1) {
                        e1.printStackTrace();
                    } catch (IllegalStateException e1) {
                        e1.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        return v;
    }


}
