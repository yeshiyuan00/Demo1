package com.audio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.exp.ysy.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysy on 2015/3/26.
 */
public class MediaPlayerTest extends Activity {

    //定义播放声音的mediaplayer
    MediaPlayer mPlayer;
    //定义系统的示波器
    private Visualizer mVisualizer;
    //定义系统的均衡器
    private Equalizer mEqualizer;
    //定义系统的重低音控制器
    private BassBoost mbass;
    //定义系统的预设音场控制器
    private PresetReverb mPreserReverb;
    private LinearLayout layout;

    private List<Short> reverbNames = new ArrayList<Short>();
    private List<String> reverbVals = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置控制音量声音
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        setContentView(layout);
        //创建mediaplayer对象
        mPlayer = MediaPlayer.create(this, R.raw.test);
        //初始化示波器
        setupVisualizer();
        //初始化均衡控制器
        setupEqualizer();
        //初始化重低音控制器
        setupBassBoost();
        //初始化预设音场控制器
        setupPresetReverb();
        //开始播放音乐
        mPlayer.start();
    }

    //初始化示波器
    private void setupVisualizer() {
        //创建MyVisualizerView对象，用于显示波形图
        final MyVisualizerView mVisualizerView = new MyVisualizerView(this);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (120f * getResources().getDisplayMetrics().density)));

        layout.addView(mVisualizerView);
        //以Mediaplayer的AudioSessionId创建Visualizer
        //相当于设置visualizer负责显示MediaPlayer的数据
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        //为 mVisualizer设置监听器
        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                //用waveform波形数据更新mVisualizerView组件
                mVisualizerView.updateVisualizer(waveform);

            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }
        }, Visualizer.getMaxCaptureRate() / 2, true, false);

        mVisualizer.setEnabled(true);
    }

    //初始化均衡控制器
    private void setupEqualizer() {
        //以Mediaplayer的AudioSessionId创建Equalizer
        //相当于设置Equalizer负责控制MediaPlayer
        mEqualizer = new Equalizer(0, mPlayer.getAudioSessionId());
        //启动均衡控制效果
        mEqualizer.setEnabled(true);
        TextView eqtitle = new TextView(this);
        eqtitle.setText("均衡器");
        layout.addView(eqtitle);
        //获取均衡控制器支持的最大值和最小值
        final short minEqLevel = mEqualizer.getBandLevelRange()[0];
        final short maxEqLevel = mEqualizer.getBandLevelRange()[1];
        //获取均衡控制器所支持的所有频率
        short brands = mEqualizer.getNumberOfBands();
        for (short i = 0; i < brands; i++) {
            TextView eqTextView = new TextView(this);
            //创建一个Textview，用于显示频率
            eqTextView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            eqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            //设置该均衡器控制的频率
            eqTextView.setText((mEqualizer.getCenterFreq(i) / 1000) + "Hz");
            layout.addView(eqTextView);
            //创建一个水平排列组件的LinearLayout；
            LinearLayout tmpLayout = new LinearLayout(this);
            tmpLayout.setOrientation(LinearLayout.HORIZONTAL);
            //显示均衡控制器最小值
            TextView minDbTextview = new TextView(this);
            minDbTextview.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            minDbTextview.setText(minEqLevel / 100 + "db");

            //显示均衡控制器最大值
            TextView maxDbTextview = new TextView(this);
            maxDbTextview.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            maxDbTextview.setText(maxEqLevel / 100 + "db");


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            //定义seekbar作为调整工具
            SeekBar bar = new SeekBar(this);
            bar.setLayoutParams(layoutParams);
            bar.setMax(maxEqLevel - minEqLevel);
            bar.setProgress(mEqualizer.getBandLevel(i));
            final short brand = i;

            bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //设置该频率的均衡值
                    mEqualizer.setBandLevel(brand, (short) (progress + minEqLevel));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            tmpLayout.addView(minDbTextview);
            tmpLayout.addView(bar);
            tmpLayout.addView(maxDbTextview);

            layout.addView(tmpLayout);
        }
    }

    //初始化重低音控制器
    private void setupBassBoost() {
        //以Mediaplayer的AudioSessionId创建BassBoost
        //相当于设置BassBoost负责控制MediaPlayer
        mbass = new BassBoost(0, mPlayer.getAudioSessionId());
        //设置启用重低音效果
        mbass.setEnabled(true);
        TextView bbtitle = new TextView(this);
        bbtitle.setText("重低音");
        layout.addView(bbtitle);
        //使用seekbar作为重低音调整工具
        SeekBar bar = new SeekBar(this);
        //重低音范围0·1000
        bar.setMax(1000);
        bar.setProgress(0);

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //设置重低音强度
                mbass.setStrength((short) progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        layout.addView(bar);
    }

    //初始化预设音场控制器
    private void setupPresetReverb() {
        //以Mediaplayer的AudioSessionId创建PresetReverb
        //相当于设置PresetReverbt负责控制MediaPlayer
        mPreserReverb = new PresetReverb(0, mPlayer.getAudioSessionId());
        //设置启用预设音场控制
        mPreserReverb.setEnabled(true);
        TextView prtitle = new TextView(this);
        prtitle.setText("音场");
        layout.addView(prtitle);

        //获取系统支持的所有预设音场
        for (short i = 0; i < mEqualizer.getNumberOfPresets(); i++) {
            reverbNames.add(i);
            reverbVals.add(mEqualizer.getPresetName(i));
        }
        //使用Spinner作为选择音场的工具
        Spinner sp = new Spinner(this);
        sp.setAdapter(new ArrayAdapter<String>(MediaPlayerTest.this,
                android.R.layout.simple_spinner_item, reverbVals));

        sp.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //设置音场
                mPreserReverb.setPreset(reverbNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        layout.addView(sp);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && mPlayer != null) {
            //释放所有对象
            mVisualizer.release();
            mEqualizer.release();
            mPreserReverb.release();
            mbass.release();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private static class MyVisualizerView extends View {
        //byte数组保存了波形抽样点的值
        private byte[] bytes;
        private float[] points;
        private Paint paint = new Paint();
        private Rect rect = new Rect();
        private byte type = 0;


        public MyVisualizerView(Context context) {
            super(context);
            bytes = null;
            paint.setStrokeWidth(1f);
            paint.setAntiAlias(true);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
        }

        public void updateVisualizer(byte[] waveform) {

            bytes = waveform;
            //重绘自己
            invalidate();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //触碰切换波形
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return false;
            }
            type++;
            if (type >= 3) {
                type = 0;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (bytes == null) {
                return;
            }
            //绘制白色背景
            canvas.drawColor(Color.WHITE);
            //使用rect对象记录该组件的宽度和高度
            rect.set(0, 0, getWidth(), getHeight());
            switch (type) {
                //------绘制块状的波形图------
                case 0:
                    for (int i = 0; i < bytes.length - 1; i++) {
                        float left = getWidth() * i / (bytes.length - 1);
                        //更具波形值计算矩形的高度
                        float top = rect.height() -
                                (byte) (bytes[i + 1] + 128) * rect.height() / 128;
                        float right = left + 1;
                        float bottom = rect.height();
                        canvas.drawRect(left, top, right, bottom, paint);
                    }

                    break;
                //------绘制柱状的波形图(每隔18个抽样点绘制一个矩形)------
                case 1:
                    for (int i = 0; i < bytes.length - 1; i += 18) {
                        float left = getWidth() * i / (bytes.length - 1);
                        //更具波形值计算矩形的高度
                        float top = rect.height() -
                                (byte) (bytes[i + 1] + 128) * rect.height() / 128;
                        float right = left + 6;
                        float bottom = rect.height();
                        canvas.drawRect(left, top, right, bottom, paint);
                    }
                    break;
                //------绘制曲线的波形图------
                case 2:
                    //如果point数组还未初始化
                    if (points == null || points.length < bytes.length * 4) {
                        points = new float[bytes.length * 4];
                    }

                    for (int i = 0; i < bytes.length - 1; i++) {
                        //计算第i个点的x坐标
                        points[i * 4] = rect.width() * i / (bytes.length - 1);
                        //根据bytes[i]的值计算第i个点的y坐标
                        points[i * 4 + 1] = (rect.height() / 2) +
                                ((byte) (bytes[i] + 128)) * 128 / (rect.height() / 2);
                        //计算第i+1个点的x坐标
                        points[i * 4 + 2] = rect.width() * (i + 1) / (bytes.length - 1);
                        //根据bytes[i+1]的值计算第i+1个点的y坐标
                        points[i * 4 + 3] = (rect.height() / 2) +
                                ((byte) (bytes[i + 1] + 128)) * 128 / (rect.height() / 2);
                    }
                    //绘制波形曲线
                    canvas.drawLines(points, paint);
                    break;
            }
        }
    }

}
