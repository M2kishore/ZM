package com.example.zm;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_setter);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.music);
        final TextView timer_text = (TextView) findViewById(R.id.text);
        final Button start_button = (Button) findViewById(R.id.button_start);
        final Button stop_button = (Button) findViewById(R.id.button_stop);
        final ProgressBar timer_bar = (ProgressBar) findViewById(R.id.progressbar);
        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(25);
        numberPicker.setMaxValue(45);


        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int value = numberPicker.getValue();
                int millisecond = value * 60000;
                timer_bar.setMax(millisecond);

                numberPicker.setVisibility(View.INVISIBLE);
                start_button.setVisibility(View.INVISIBLE);
                stop_button.setVisibility(View.VISIBLE);
                timer_text.setVisibility(View.VISIBLE);
                timer_bar.setVisibility(View.VISIBLE);

                final CountDownTimer timer = new CountDownTimer(millisecond,1000) {

                    public void onTick(long l) {
                        //start music
                        mediaPlayer.start();
                        mediaPlayer.setLooping(true);
                        int minute;
                        int sec;
                        minute = (int) l / 60000;
                        sec = (int) (l - (minute * 60000)) / 1000;
                        timer_bar.setProgress((int) l);
                        if(sec>=10){
                            timer_text.setText(minute + ":" + sec);

                        }
                        else {
                            timer_text.setText(minute + ":0" + sec);
                        }
                    }
                    @Override
                    public void onFinish() {
                        //stop music
                        mediaPlayer.pause();
                        timer_text.setVisibility(View.INVISIBLE);
                        start_button.setVisibility(View.VISIBLE);
                        numberPicker.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "You have completed!", Toast.LENGTH_LONG).show();


                    }
                }.start();
                stop_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timer.cancel();
                        mediaPlayer.pause();
                        timer_text.setVisibility(View.INVISIBLE);
                        timer_bar.setVisibility(View.INVISIBLE);
                        stop_button.setVisibility(View.INVISIBLE);
                        start_button.setVisibility(View.VISIBLE);
                        numberPicker.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }
}