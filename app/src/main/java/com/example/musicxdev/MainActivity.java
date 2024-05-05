package com.example.musicxdev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    // musicMediaPlayer is an instance of a MediaPlayer class
    //MediaPlayer is used to play audio or video
    MediaPlayer musicMediaPlayer;
    AudioManager audioManager;
    //this is a play function
    public void play(View view){
        // start() method here runs on musicMediaPlayer to start the music.mp3, which is audio file
        musicMediaPlayer.start();
    }
    //stop function
    public void stop(View view){
        // stop() method here runs on musicMediaPlayer to stop the music.mp3, which is audio file
        musicMediaPlayer.stop();
    }
    //pause function
    public void pause(View view){
        // pause() method here runs on musicMediaPlayer to pause the music.mp3, which is audio file
        musicMediaPlayer.pause();
    }


    //The onCreate method is a crucial part of the Android activity lifecycle,
    // and it is where you perform initialization tasks( is performed before any additional code in your onCreate method.)
    // when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //The savedInstanceState parameter is a Bundle object that is used to save the state of the activity in case it gets destroyed and needs to be recreated.
        // This is often the case, for example, when the device is rotated, and the activity is recreated to adjust to the new orientation.

        //When the activity is about to be destroyed and needs to be recreated (due to a configuration change like screen rotation or when
        // the system reclaims resources), the system calls onSaveInstanceState(Bundle outState) to allow the activity to save its state.
        //The saved state is then passed to the onCreate method as the savedInstanceState parameter when the activity is recreated.
        //The super.onCreate(savedInstanceState) call is essential because it performs the standard setup for the activity(setup, refers to default behaviour
        // that the android system provides for the initialization of an activity) and restores the saved state if there is one.
        //So, in essence, it helps in restoring the state of the activity, whether it was in a paused, running, or stopped state,
        // and it ensures a smooth recreation of the activity with its previous state intact.
        super.onCreate(savedInstanceState);

        //it defines the user interface for the activity.
        setContentView(R.layout.activity_main);
        //so the line, is telling the Android system to inflate the layout defined in "activity_main.xml" and use it as the content view for the current activity.
        // This layout typically contains the UI elements (such as buttons, text views, etc.) that make up the visual representation of your app's main screen.

        //creating an object of music player and associating it with "music", which is music.mp3
        musicMediaPlayer=MediaPlayer.create(this,  R.raw.music);

        //this line is getting the audio service, it is helping us in getting the max vol and current volume .
        audioManager=(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        SeekBar seekVolBar=findViewById(R.id.seekBar);

        //getStreamsMaxVolume returns the maximum volume index for the particular stream, which is here our music.
        //For getting the max value of our android device , here we used audioManager object and its relevant methods
        int maxVol= audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVol= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        //if seekbar( for volume) goes to its end, what would be the volume
        //setMax() is setting max value you want give to your seekbar(vol bar), if you move it to its end,
        //so here we are giving "maxVol" value
        seekVolBar.setMax(maxVol);

        //for setting seekbar current value/progress to "curVol"
        seekVolBar.setProgress(curVol);

        //for changing seekbar(volume bar), setting it on current volume , changelistener is used here
        seekVolBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            //this function will work, when progress of the seekbar(volume bar) will change
            //it means whereever the seekBar(volume bar) will be left, the progress will set to that
            //point for e.g as maximum volume is 15 and if we leave seekBar on 3, current progress will set to 3.
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //setting stream volume of ,STREAM type of music, on audioManager
                // so basically this setStreamVolume is setting the Streamvolume to the value of progress
                // and the value of progress is where the seekbar is left
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress, 0);
            }

            //This method will execute, when IF user start moving seekBar forward or backward seekbar
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //This method will execute when, if user just stopped moving seekbar, leaved it at point
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar seekProgBar= findViewById(R.id.seekProgress);
        seekProgBar.setMax(musicMediaPlayer.getDuration());// setting the max of seekProgBar to total duration of musicMediaPlayer
        // which is actually playing music.mp3

        //timer in java is used to make new task, this code  automatically start seekProgBar.
        new Timer().scheduleAtFixedRate(new TimerTask() {
            //this run function will able seekProgBar to move after getting the cuurent position of musicMediaplayer, from that position
            //seekProgBar will start moving/Playing again.musicMediaPlayer( music) and seekProgBar will move/play together
            @Override
            public void run() {
                seekProgBar.setProgress(musicMediaPlayer.getCurrentPosition());
            }
        },0,900 );
        //delay 0 means when it willstart and after how many miliseconds it will repeat again

        //this code is for setting change in seekProgBar
        //Attaching setOnSeekBarChangeListener with seekProgBar
        seekProgBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //if user wants to listen music from middle, seekBar will be moved and left at middle
            //for e.g total duration is 0:15 and seekbar is moved to be left at 0:10, so the progress of seekProgBar will
            //set to 0:10, means the progress is changed to now 0:10
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //seekTo(Progress), music will start playing again from current progress
                musicMediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        

    }
}