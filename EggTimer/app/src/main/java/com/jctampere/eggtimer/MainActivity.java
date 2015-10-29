package com.jctampere.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static com.jctampere.eggtimer.R.raw.alarm;

public class MainActivity extends AppCompatActivity {

    int started;
    int minutes, seconds;
    String sec;
    TextView timerTextView;
    SeekBar seekBar;
    CountDownTimer countDownTimer;

    Button button;

    public void updateTimer(int progress){
        minutes = (int) progress/60;
        seconds = progress - minutes * 60;

        if (seconds<10)
            sec = "0";
        else
            sec = "";
        timerTextView.setText(Integer.toString(minutes)+":"+sec+Integer.toString(seconds));
    }
    public void resetTimer(){
        countDownTimer.cancel();
        started = 0;
        button.setText("Go");
        seekBar.setEnabled(true);
       timerTextView.setText("0:30");
        seekBar.setProgress(30);
    }

    public void startTimer(View view) {

       button = (Button)findViewById(R.id.button);

        if(started == 0) {

            button.setText("stop");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    resetTimer();


                }
            }.start();
            seekBar.setEnabled(false);
            started=1;

        }else {

                resetTimer();




        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        started = 0;


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
