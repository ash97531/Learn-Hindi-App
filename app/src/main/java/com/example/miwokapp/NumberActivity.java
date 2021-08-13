package com.example.miwokapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.app.FragmentManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_GAIN_TRANSIENT;
import static android.media.AudioManager.AUDIOFOCUS_REQUEST_GRANTED;

public class NumberActivity extends AppCompatActivity {

    private MediaPlayer voice;
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




        audioManager = (AudioManager) NumberActivity.this.getSystemService(Context.AUDIO_SERVICE);

        //creating an array of words
        final ArrayList <word> words=new ArrayList<word>();

        //words.add("one");
        // __ USING ___ WORD ___ CLASS __ EXPANDED ___ METHOD __ SHORTMETHOD DOWN
//        word w = new word("one", "lutti");
//        words.add(w);
        // __ USING ___ WORD ___ CLASS __ EXPANDED ___ METHOD __ SHORTMETHOD DOWN

        words.add(new word("one", "एक",R.drawable.number_one,R.raw.one));
        words.add(new word("two", "दो",R.drawable.number_two,R.raw.two));
        words.add(new word("three", "तीन",R.drawable.number_three,R.raw.three));
        words.add(new word("four", "चार",R.drawable.number_four,R.raw.four));
        words.add(new word("five", "पांच",R.drawable.number_five,R.raw.five));
        words.add(new word("six", "छः",R.drawable.number_six,R.raw.six));
        words.add(new word("seven", "सात",R.drawable.number_seven,R.raw.seven));
        words.add(new word("eight", "आठ",R.drawable.number_eight,R.raw.eight));
        words.add(new word("nine", "नौ",R.drawable.number_nine,R.raw.nine));
        words.add(new word("ten", "दस",R.drawable.number_ten,R.raw.ten));

        wordadapter adapter = new wordadapter(this,  words, R.color.teal_200);
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
                    voice = MediaPlayer.create(NumberActivity.this, (words.get(position).getAudio()));
                    voice.start();

                    // Created image view of the playpause button of the position of listview
                    ImageView playpause = (ImageView) view.findViewById(R.id.play_pause_button);
                    // setting the pause image when that view is clicked
                    playpause.setImageResource(R.drawable.ic_pause_arrow);

                    voice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer voice) {
                            releaseMediaPlayer();

                            // On completion that image will again set to play button image
                            playpause.setImageResource(R.drawable.ic_play_arrow);

                            // Here the toast message to confirm that voice is over
                            Toast.makeText(NumberActivity.this, "Over", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

            // __ USED ___ ARRAYADAPTER ___ CLASS ___
            // THIS TAKES ONLY ONE VIEW...
            // WE HAVE TO MAKE MORE VIEWS
//        ArrayAdapter <word> itemadapter = new ArrayAdapter<word>(this, R.layout.list_item, words);
//        ListView listview = (ListView) findViewById(R.id.list);
//        listview.setAdapter(itemadapter);
            // __ USED ___ ARRAYADAPTER ___ CLASS ___


        // __ CODE ___ FOR ___ GRID ___ VIEW __
//        ArrayAdapter <String> itemadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, words);
//        GridView gridview = (GridView) findViewById(R.id.list);
//        gridview.setAdapter(itemadapter);
        // __ CODE ___ FOR ___ GRID ___ VIEW __



        // __ NOT ___ EFFICIENT ___ USE ___ ARRAYADAPTER __
//        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootview);
//
//        //WHILE LOOP FOR CREATING NUMBERS IN APPLICATION
//        int index=0;
//        while(index<words.size()){
//            TextView wordview = new TextView(this);
//            wordview.setText(words.get(index));
//            wordview.setTextSize(30);
//            rootView.addView(wordview);
//            index++;
//    }
        // __ NOT ___ EFFICIENT ___ USE ___ ARRAYADAPTER __

        //__TRIED ___ ONE ___ BY ___ ONE __
//        TextView wordview = new TextView(this);
//        wordview.setText(words.get(0));
//        wordview.setTextSize(30);
//        rootView.addView(wordview);
//
//        TextView wordview2 = new TextView(this);
//        wordview2.setText(words.get(1));
//        wordview2.setTextSize(30);
//        rootView.addView(wordview2);
        //__TRIED ___ ONE ___ BY ___ ONE __

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

            //whenever release mediaplayer is called we have to release this audio focus
            //so that other application can use that audiofocus
            //or we can free the audiofocus and clean up resources
            audioManager.abandonAudioFocus(afChangeListener);
        }
    }

}