package com.rerx.alexey.bmstu;

/**
 * Created by alexey on 07.01.16.
 */
import android.content.Context;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by alexey on 05.01.16.
 */
public class Record {
    final String TAG = "Debug";
    PrintWriter writer;
    //всё для записи
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private AudioRecord audioRecord;
    public boolean isReading = false;
    int myBufferSize = 8192;
    byte[] myBuffer = new byte[myBufferSize];
    MainActivity main;

    Record(Context context){
        main = (MainActivity) context;
    }

    public void recordStart() {
        audioRecord.startRecording();
        int recordingState = audioRecord.getRecordingState();
    }

    public void recordStop() {
        audioRecord.stop();
    }

    public void readStart() {
        try {
            writer =  new PrintWriter(main.fileDebug);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        isReading = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (audioRecord == null)
                    return;


                int readCount = 0;
                int totalCount = 0;
                while (isReading) {
                    readCount = audioRecord.read(myBuffer, 0, myBufferSize);
                    totalCount += readCount;

                    main.runOnUiThread(new Runnable(){
                        @Override
                        public void  run() {
                            for (int i = 0; i < myBuffer.length; i++) {
                                main.txt.setText(i + 1 + " " + myBuffer[i] + "\n");

                                writer.print(i + 1 + " " + myBuffer[i] + "\n");

                            }
                        }
                    });

                } writer.close();
            }
        }).start();

    }

    public void readStop() {
        isReading = false;
    }

    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonDebug:
                    readStart();
                    break;
                case R.id.stopDebug:
                    readStop();
                    break;
            }
        }
    }
    public void readStop(View v) {
        Log.d(TAG, "read stop");
        isReading = false;
    }


}

































//    private String fileName;
//    public void recordStart(View v) {
//        try {
//            releaseRecorder();
//
//            File outFile = new File(fileName);
//            if (outFile.exists()) {
//                outFile.delete();
//            }
//
//            mediaRecorder = new MediaRecorder();
//            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//            mediaRecorder.setOutputFile(fileName);
//            mediaRecorder.prepare();
//            mediaRecorder.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void recordStop(View v) {
//        if (mediaRecorder != null) {
//            mediaRecorder.stop();
//        }
//    }
//    public void playStart(View v) {
//        try {
//            releasePlayer();
//            mediaPlayer = new MediaPlayer();
//            mediaPlayer.setDataSource(fileName);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void playStop(View v) {
//        if (mediaPlayer != null) {
//            mediaPlayer.stop();
//        }
//    }
//
//    public void releaseRecorder() {
//        if (mediaRecorder != null) {
//            mediaRecorder.release();
//            mediaRecorder = null;
//        }
//    }
//
//    public void releasePlayer() {
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }


