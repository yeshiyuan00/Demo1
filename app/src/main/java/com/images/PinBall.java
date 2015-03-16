package com.images;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ysy on 2015/3/10.
 */
public class PinBall extends Activity {

    private float preX = 0.0f, preY = 0.0f, endX, endY;

    //桌面的宽度
    private int tableWidth;
    //桌面的高度
    private int tableHeight;
    //球拍的垂直位置
    private int racketY;
    //下面定义球拍的高度和宽度
    private final int RACKET_HEIGHT = 20;
    private final int RACKET_WIDTH = 70;
    //小球的大小
    private final int BALL_SIZE = 12;
    //小球纵向的运行速度
    private int ySpeed = 20;
    Random random = new Random();
    //返回一个-0.5-0.5的比率，用于控制小球运行的方向
    private double xyRate = random.nextDouble() - 0.5;
    //小球横向运行的速度
    private int xSPeed = (int) (ySpeed * xyRate * 2);
    //ballX和ballY代表小球的坐标
    private int ballX = random.nextInt(200) + 20;
    private int ballY = random.nextInt(10) + 20;
    //racketX代表球拍的水平位置
    private int racketX = random.nextInt(200);
    //游戏是否结束的标志
    private boolean isLose = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //创建GameView组件
        final GameView gameView = new GameView(this);
        setContentView(gameView);
        //获取窗口管理
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        //获取屏幕宽和高
        tableWidth = metrics.widthPixels;
        tableHeight = metrics.heightPixels;
        racketY = tableHeight - 80;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {

                    gameView.invalidate();
                }
            }
        };

        gameView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:

                        break;
                }

                if (x >= tableWidth / 2) {
                    if (racketX < tableWidth - RACKET_WIDTH) racketX += 50;

                }
                if (x < tableWidth / 2) {
                    if (racketX > 0) racketX -= 50;
                }

                gameView.invalidate();
                return false;
            }
        });
        gameView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //获取由哪个键触发的事件
                switch (event.getKeyCode()) {
                    //控制挡板左移
                    case KeyEvent.KEYCODE_A:
                        if (racketX > 0) racketX -= 50;

                        break;
                    //控制挡板又移
                    case KeyEvent.KEYCODE_D:
                        if (racketX < tableWidth - RACKET_WIDTH) racketX += 50;
                        break;
                }
                //重绘GamaView
                gameView.invalidate();
                return true;
            }
        });

        final Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //如果小球碰到左边边框
                if (ballX <= 0 || ballX >= tableWidth - BALL_SIZE) {
                    xSPeed = -xSPeed;
                }
                //如果小球的高度超出了球拍的位置，且横向不在球拍范围内，游戏结束
                if (ballY >= racketY - BALL_SIZE && (ballX < racketX ||
                        ballX > racketX + RACKET_WIDTH)) {
                    timer.cancel();
                    //游戏结束
                    isLose = true;
                }
                //如果小球位于球拍之内，且到达球拍位置，小球反弹
                else if (ballY <= 0 || (ballY >= racketY - BALL_SIZE &&
                        ballX >= racketX && ballX <= racketX + RACKET_WIDTH)) {
                    ySpeed = -ySpeed;
                }
                //小球坐标增加
                ballY += ySpeed;
                ballX += xSPeed;
                //发送消息，通知系统重绘组件
                handler.sendEmptyMessage(0x123);

            }
        }, 0, 100);
    }

    class GameView extends View {

        Paint paint = new Paint();

        public GameView(Context context) {
            super(context);
            setFocusable(true);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            paint.setStyle(Paint.Style.FILL);
            //去锯齿
            paint.setAntiAlias(true);
            //若果游戏结束
            if (isLose) {
                paint.setColor(Color.RED);
                paint.setTextSize(40);
                canvas.drawText("游戏已经结束！", 50, 200, paint);
            }
            //游戏还未结束
            else {
                paint.setColor(Color.rgb(80, 240, 80));
                canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);

                paint.setColor(Color.rgb(80, 80, 200));
                canvas.drawRect(racketX, racketY, racketX + RACKET_WIDTH,
                        racketY + RACKET_HEIGHT, paint);
            }
        }
    }
}
