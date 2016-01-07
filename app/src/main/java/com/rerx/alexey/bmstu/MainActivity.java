package com.rerx.alexey.bmstu;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    /*Константы*/
    TextView txt;
    public static final String FILE_FOLDER = "records";
    public static final String PATH = android.os.Environment
            .getExternalStorageDirectory()
            + java.io.File.separator
            + FILE_FOLDER + java.io.File.separator;
    /*------------------------------*/
    private String fileName;
    /*****************DEBUG*******************/
    String fileDebug;
    public static final String FILE_DEBUG_FOLDER = "debug";
    public static final String PATHDEBUG = android.os.Environment
            .getExternalStorageDirectory()
            + java.io.File.separator
            + FILE_DEBUG_FOLDER + java.io.File.separator;

/**************************************/

    /**************ЧТЕНИЕ***************/
    Record record = new Record(this);
    int sampleRate = 8000;
    int channelConfig = AudioFormat.CHANNEL_IN_MONO;
    int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    int minInternalBufferSize = AudioRecord.getMinBufferSize(sampleRate,
            channelConfig, audioFormat);
    int internalBufferSize = minInternalBufferSize * 4;

    AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
            sampleRate, channelConfig, audioFormat, internalBufferSize);

    short [] audioData = new short[internalBufferSize];//предположительно массив данных

    /***************************************/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txt = new TextView(this);
        Button buttonDebug = new Button(this);
        buttonDebug = (Button) findViewById(R.id.buttonDebug);
        buttonDebug.setOnClickListener(record.new OnClickListener());
        Button buttonDebugStop = new Button(this);
        buttonDebugStop = (Button) findViewById(R.id.stopDebug);
        buttonDebugStop.setOnClickListener(record.new OnClickListener());
        /* Создание директории записи */
        File dir = new File(PATH);
        dir.mkdirs();
        fileName = Environment.getExternalStorageDirectory() + "/record.3gpp";
        /*****************************/
        /*************DEBUG****************/
        File dirDebug = new File(PATHDEBUG);
        dirDebug.mkdirs();
        File f = new File(fileDebug);

        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**********************************/

        txt = (TextView) findViewById(R.id.debug);
        audioRecord.read(audioData,0,100);
        for (int i=0; i < audioData.length;i++){
            txt.setText(i+1 + " " +  audioData[i] + "\n");
        }
    }


    // sampleRate = 44100
    //оно будет вычислять частоту
//    public static int calculate(int sampleRate, short [] audioData){
//
//        int numSamples = audioData.length;
//        int numCrossing = 0;
//        for (int p = 0; p < numSamples-1; p++)
//        {
//            if ((audioData[p] > 0 && audioData[p + 1] <= 0) ||
//                    (audioData[p] < 0 && audioData[p + 1] >= 0))
//            {
//                numCrossing++;
//            }
//        }
//        float numSecondsRecorded = (float)numSamples/(float)sampleRate;
//        float numCycles = numCrossing/2;
//        float frequency = numCycles/numSecondsRecorded;
//
//        return (int)frequency;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Record record = new Record();
//        record.releasePlayer();
//        record.releaseRecorder();
    }
}