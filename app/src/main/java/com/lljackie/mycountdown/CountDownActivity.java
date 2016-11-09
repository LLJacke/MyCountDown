package com.lljackie.mycountdown;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CountDownActivity extends AppCompatActivity {

    int d;
    int h;
    int min;
    int s;

    TextView tv_day;
    TextView tv_hour;
    TextView tv_min;
    TextView tv_sec;

    Button bt_pause;
    Button bt_cancel;

    private boolean flag = false;             //暂停标识

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);

        tv_day = (TextView) findViewById(R.id.tv_day);
        tv_hour = (TextView) findViewById(R.id.tv_hour);
        tv_min = (TextView) findViewById(R.id.tv_min);
        tv_sec = (TextView) findViewById(R.id.tv_sec);

        bt_pause = (Button) findViewById(R.id.bt_pause);
        bt_cancel = (Button) findViewById(R.id.bt_cancel);

        Intent intent = getIntent();
        d = intent.getIntExtra("day", 0);
        h = intent.getIntExtra("hour", 0);
        min = intent.getIntExtra("minute", 0);
        s = intent.getIntExtra("second", 0);

        int sum1 = h * 60 * 60 + min * 60 + s;          //将时分秒加为一个总数

        setFormattedText(tv_day, d);
        setFormattedText(tv_hour, h);
        setFormattedText(tv_min, min);
        setFormattedText(tv_sec, s);

        final MyThread thread = new MyThread(sum1, d);
        thread.start();

        bt_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!flag) {
                    flag = true;
                    bt_pause.setText("continue");
                }
                else {
                    flag = false;
                    bt_pause.setText("pause");
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountDownActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setFormattedText(tv_day, msg.arg2);
            setFormattedText(tv_hour, msg.arg1 / 3600);
            setFormattedText(tv_min, msg.arg1 % 3600 / 60);
            setFormattedText(tv_sec, msg.arg1 % 3600 % 60);
        }
    };

    public class MyThread extends Thread {              //定义内部类，由于用匿名内部类传递数据比较麻烦，所以改用内部类
        private int num1, num2;

        MyThread(int num1, int num2) {                  //num1为时分秒换算的总秒数，num2为天数
            this.num1 = num1;
            this.num2 = num2;
        }

        @Override
        public void run() {
            while (num1 > -1 || num2 > 0) {

                try {
                    while (flag) {                      //判断是否暂停
                        sleep(1000);
                    }

                    Message msg = new Message();
                    msg.arg1 = num1;
                    msg.arg2 = num2;
                    CountDownActivity.this.handler.sendMessage(msg);

                    if (num1 == 0 && num2 == 0) break;

                    if (num1 == 0) {
                        num2--;
                        num1 = 24 * 60 * 60;
                    }
                    num1 -= 1;

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {        //点击返回键不会退出程序，程序后台运行
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo =
                pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
            startActivitySafely(startIntent);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    private void startActivitySafely(Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException | SecurityException e) {
            Toast.makeText(this, "null",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void setFormattedText(TextView bt, int num) {           //设置格式化显示数字
        if (num >= 0 && num < 10)
            bt.setText("0" + num);
        else
            bt.setText(num + "");
    }
}
