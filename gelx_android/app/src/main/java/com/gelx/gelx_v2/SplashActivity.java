package com.gelx.gelx_v2;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;


public class SplashActivity extends AppCompatActivity {

    private static final int SLEEP_TIME = 17000;//change into milliseconds
    VideoView videoView;
    boolean isScreenTapped = false;
    private static final int LENGTH_LONG = SLEEP_TIME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        videoView = findViewById(R.id.videoView);


        String path = "android.resource://"+getPackageName()+"/"+R.raw.slogoaudio;
        Uri uri = Uri.parse(path);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();
            }
        });


        RelativeLayout videoViewParent = findViewById(R.id.video_view_parent);


        final Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (!isScreenTapped) {
                        launchMeasureActivity();
                    }
                }
            }
        };
        timerThread.start();

        videoViewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isScreenTapped = true;
                launchMeasureActivity();
            }
        });
    }

    private void launchMeasureActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() { // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}

