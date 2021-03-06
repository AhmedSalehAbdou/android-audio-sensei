package com.rygelouv.androidaudiosensei;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rygelouv.audiosensei.recorder.AudioRecordInfo;
import com.rygelouv.audiosensei.recorder.AudioSensei;

import java.util.UUID;

/**
 Copyright 2017 Rygelouv.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **/
public class MainActivity extends AppCompatActivity
{
    private Button play, stop, record, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Audio Recording");
        play = findViewById(R.id.play);
        stop = findViewById(R.id.stop);
        record = findViewById(R.id.record);
        cancel = findViewById(R.id.cancel);
        stop.setEnabled(false);
        play.setEnabled(false);
        cancel.setEnabled(false);

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AudioSensei.Recorder()
                        .with(MainActivity.this)
                        .name(UUID.randomUUID().toString())
                        .to(AudioRecordInfo.AudioPath.APP_PUBLIC_MUSIC)
                        .start();

                record.setEnabled(false);
                stop.setEnabled(true);
                cancel.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioSensei.getInstance().stopRecording();
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                cancel.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(AudioSensei.getInstance().getLastRecordedOutputFile());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // make something
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AudioSensei.getInstance().cancelRecording();
                record.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(true);
                cancel.setEnabled(false);
            }
        });
    }

    //Handling callback
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        AudioSensei.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
