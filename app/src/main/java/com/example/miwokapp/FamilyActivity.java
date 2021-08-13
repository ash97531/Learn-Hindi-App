package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

public class FamilyActivity extends AppCompatActivity {

    MediaPlayer voice;
    private AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =(new AudioManager.OnAudioFocusChangeListener(){
        public void onAudioFocusChange(int focusChange){
            if (focusChange == (audioManager.AUDIOFOCUS_GAIN_TRANSIENT) || (focusChange == audioManager.AUDIOFOCUS_GAIN)){
                voice.start();
            } else if (focusChange == audioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();                      // we are releasing the audiofocus inside this releaseMediaPlayer fucntion
            } else if (focusChange == audioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                voice.pause();
                voice.seekTo(0);
            } else if (focusChange == audioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                voice.pause();
                voice.seekTo(0);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        audioManager = (AudioManager) FamilyActivity.this.getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> words=new ArrayList<word>();

        words.add(new word("father", "पिता जी",R.drawable.family_father,R.raw.father));
        words.add(new word("mother", "माता जी",R.drawable.family_mother,R.raw.mother));
        words.add(new word("son", "बेटा ",R.drawable.family_son,R.raw.son));
        words.add(new word("daughter", "बेटी",R.drawable.family_daughter,R.raw.daughter));
        words.add(new word("elder brother", "भैया, बडा भाई",R.drawable.family_older_brother,R.raw.elder_brother));
        words.add(new word("younger brother", "भाई, छोटा भाई",R.drawable.family_younger_brother,R.raw.younger_brother));
        words.add(new word("elder sister", "दीदी, बडी बहन",R.drawable.family_older_sister,R.raw.elder_sister));
        words.add(new word("younger sister", "बहन, छोटी बहन",R.drawable.family_younger_sister,R.raw.younger_sister));
        words.add(new word("grand father", "दादा जी",R.drawable.family_grandfather,R.raw.grandfather));
        words.add(new word("grand mother", "दादी जी",R.drawable.family_grandmother,R.raw.grandmother));

        wordadapter adapter = new wordadapter(this,  words,R.color.purple_200);
        ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Request audio focus for playback
                int result = audioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AUDIOFOCUS_REQUEST_GRANTED) {

                    releaseMediaPlayer();
                    voice = MediaPlayer.create(FamilyActivity.this, (words.get(position).getAudio()));
                    voice.start();

                    ImageView playpause = (ImageView) view.findViewById(R.id.play_pause_button);
                    playpause.setImageResource(R.drawable.ic_pause_arrow);

                    voice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer voice) {
                            releaseMediaPlayer();
                            playpause.setImageResource(R.drawable.ic_play_arrow);
                            Toast.makeText(FamilyActivity.this, "Over", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (voice != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            voice.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            voice = null;

            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
}