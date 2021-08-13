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

public class ColourActivity extends AppCompatActivity {

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

        audioManager = (AudioManager) ColourActivity.this.getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> words=new ArrayList<word>();

        words.add(new word("black", "काला",R.drawable.color_black,R.raw.black));
        words.add(new word("dusty yellow", "धूल भरा पीला",R.drawable.color_dusty_yellow,R.raw.dusty_yellow));
        words.add(new word("brown", "भूरा ",R.drawable.color_brown,R.raw.brown));
        words.add(new word("green", "हरा",R.drawable.color_green,R.raw.green));
        words.add(new word("grey", "धूसर, स्लेटी",R.drawable.color_gray,R.raw.grey));
        words.add(new word("mustard yellow", "सरसों पीली",R.drawable.color_mustard_yellow,R.raw.mustard_yellow));
        words.add(new word("red", "लाल",R.drawable.color_red,R.raw.red));
        words.add(new word("white", "सफ़ेद",R.drawable.color_white,R.raw.white));

        wordadapter adapter = new wordadapter(this,  words, R.color.teal_700);
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
                    voice = MediaPlayer.create(ColourActivity.this, (words.get(position).getAudio()));
                    voice.start();

                    ImageView playpause = (ImageView) view.findViewById(R.id.play_pause_button);
                    playpause.setImageResource(R.drawable.ic_pause_arrow);

                    voice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer voice) {
                            releaseMediaPlayer();
                            playpause.setImageResource(R.drawable.ic_play_arrow);
                            Toast.makeText(ColourActivity.this, "Over", Toast.LENGTH_SHORT).show();
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