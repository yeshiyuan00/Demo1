package com.io;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.exp.ysy.demo.R;

import java.util.Locale;

/**
 * Created by ysy on 2015/3/17.
 */
public class Speech extends Activity {

    TextToSpeech tts;
    EditText editText;
    Button speech;
    Button record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_speech);

        editText = (EditText) findViewById(R.id.text);
        speech = (Button) findViewById(R.id.bn_speech);
        record = (Button) findViewById(R.id.bn_record);
        //初始化TextToSpeech对象
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //如果装载TTS引擎成功
                if (status == TextToSpeech.SUCCESS) {
                    //设置使用美式英语朗读
                    int result = tts.setLanguage(Locale.US);
                    //如果不支持所设置的语言
                    if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
                            && result != TextToSpeech.LANG_AVAILABLE) {
                        Toast.makeText(Speech.this, "TTS暂不支持这种语言的朗读", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行朗读
                tts.speak(editText.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tts.synthesizeToFile(editText.getText().toString(), null, "/mnt/sdcard/sound.wav");
                Toast.makeText(Speech.this, "声音记录成功", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        //关闭TextToSpeech对象
        if (tts != null) {
            tts.shutdown();
        }
        super.onDestroy();

    }
}
