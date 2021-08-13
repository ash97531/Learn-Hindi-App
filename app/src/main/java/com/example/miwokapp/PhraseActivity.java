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

public class PhraseActivity extends AppCompatActivity {

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

        audioManager = (AudioManager) PhraseActivity.this.getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<word> words=new ArrayList<word>();

        words.add(new word("hello", "नमस्ते",R.raw.p1));
        words.add(new word("good night", "शुभ रात्री",R.raw.p2));
        words.add(new word("how are you", "आप कैसे हैं ",R.raw.p3));
        words.add(new word("I am fine", "मैं ठीक हुँ ",R.raw.p4));
        words.add(new word("my name is....", " मेरा नाम..... है ",R.raw.p5));
        words.add(new word("nice to meet you", "आपसे मिलकर खुशी हुई",R.raw.p6));
        words.add(new word("excuses me", "क्षमा कीजिए",R.raw.p7));
        words.add(new word("I am lost", "हम खो गये हैं",R.raw.p8));
        words.add(new word("do you speak english?", "क्या आप अंग्रेज़ी बोलते हैं?",R.raw.p9));
        words.add(new word("where is bathroom/ pharmacy?", "शौचघर/ फार्मेसी कहां है? ",R.raw.p10));

        wordadapter adapter = new wordadapter(this,  words, R.color.orange);
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
                    voice = MediaPlayer.create(PhraseActivity.this, (words.get(position).getAudio()));
                    voice.start();

                    ImageView playpause = (ImageView) view.findViewById(R.id.play_pause_button);
                    playpause.setImageResource(R.drawable.ic_pause_arrow);

                    voice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer voice) {
                            releaseMediaPlayer();
                            playpause.setImageResource(R.drawable.ic_play_arrow);
                            Toast.makeText(PhraseActivity.this, "Over", Toast.LENGTH_SHORT).show();
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

    /**
     * Clean up the media player by releasing its resources.
     */
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