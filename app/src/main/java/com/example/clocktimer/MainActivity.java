package com.example.clocktimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    SeekBar seekBar;
    Button button;
    CountDownTimer countDownTimer;
    boolean counter = true;
    boolean y;

    public void clockseekbarupdate(int progress){
        int min = (int) progress / 60;
        int second = progress - (min * 60);
        String sec= Integer.toString(second);
        if (sec =="0") {
            sec="00";
        }
        else if(second<10 ){
            textView.setText(Integer.toString(min) + ":0" + sec);
        }
        else {
            textView.setText(Integer.toString(min) + ":" + sec);
        }

    }

    public void onClick(View view){

        if(seekBar.getProgress()==0){
            Toast.makeText(this, "timer not set", Toast.LENGTH_SHORT).show();
        }
        else if(counter) {
            counter = false;
            button.setText("stop");
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000+300, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    y = true;
                    clockseekbarupdate((int) millisUntilFinished / 1000);
                    seekBar.setProgress((int) millisUntilFinished / 1000);
                    seekBar.setEnabled(false);

                }

                @Override
                public void onFinish() {
                    countDownTimer.cancel();
                    textView.setText("0:00");
                    seekBar.setProgress(0);
                    button.setText("GO!");
                    counter = true;
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mplayer.start();
                    seekBar.setEnabled(true);


                }
            }.start();
        }
            else{
                countDownTimer.cancel();
                button.setText("GO!");
                counter=true;
                seekBar.setEnabled(true);

        }

    }
    public void reset(View View){
        if(y){
        countDownTimer.cancel();}
        textView.setText("0:00");
        seekBar.setProgress(0);
        button.setText("GO!");
        counter=true;
        seekBar.setEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        textView.setText("5"+":"+"00");

        seekBar= findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(300);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                clockseekbarupdate(progress);

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