package com.lljackie.mycountdown;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] PLANETS_FOR_DAY;
    private String[] PLANETS_FOR_HOUR;
    private String[] PLANETS_FOR_MIN;
    private String[] PLANETS_FOR_SEC;

    Button bt_1;
    Button bt_2;
    Button bt_3;
    Button bt_4;
    Button bt_5;
    Button bt_cus;

    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PLANETS_FOR_DAY = setNum(31);
        PLANETS_FOR_HOUR = setNum(24);
        PLANETS_FOR_MIN = setNum(60);
        PLANETS_FOR_SEC = setNum(60);

        bt_1 = (Button) findViewById(R.id.bt_1);
        bt_2 = (Button) findViewById(R.id.bt_2);
        bt_3 = (Button) findViewById(R.id.bt_3);
        bt_4 = (Button) findViewById(R.id.bt_4);
        bt_5 = (Button) findViewById(R.id.bt_5);
        bt_cus = (Button) findViewById(R.id.bt_cus);

        bt_1.setOnClickListener(this);
        bt_2.setOnClickListener(this);
        bt_3.setOnClickListener(this);
        bt_4.setOnClickListener(this);
        bt_5.setOnClickListener(this);
        bt_cus.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_1:
                sendtosecond(0, 0, 1, 0);
                break;
            case R.id.bt_2:
                sendtosecond(0, 0, 5, 0);
                break;
            case R.id.bt_3:
                sendtosecond(0, 0, 10, 0);
                break;
            case R.id.bt_4:
                sendtosecond(0, 0, 30, 0);
                break;
            case R.id.bt_5:
                sendtosecond(0, 1, 0, 0);
                break;
            case R.id.bt_cus: {
                LinearLayout outerView = (LinearLayout) getLayoutInflater().inflate(R.layout.wheelview_dialog, null);
                final WheelView wv_day = (WheelView) outerView.findViewById(R.id.wv_day);
                final WheelView wv_hour = (WheelView) outerView.findViewById(R.id.wv_hour);
                final WheelView wv_min = (WheelView) outerView.findViewById(R.id.wv_min);
                final WheelView wv_sec = (WheelView) outerView.findViewById(R.id.wv_sec);

                wv_day.setOffset(1);
                wv_day.setItems(Arrays.asList(PLANETS_FOR_DAY));
                wv_day.setSeletion(0);

                wv_hour.setOffset(1);
                wv_hour.setItems(Arrays.asList(PLANETS_FOR_HOUR));
                wv_hour.setSeletion(0);

                wv_min.setOffset(1);
                wv_min.setItems(Arrays.asList(PLANETS_FOR_MIN));
                wv_min.setSeletion(0);

                wv_sec.setOffset(1);
                wv_sec.setItems(Arrays.asList(PLANETS_FOR_SEC));
                wv_sec.setSeletion(0);

                new AlertDialog.Builder(this)
                        .setTitle("Customized")
                        .setView(outerView)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int day = wv_day.getSeletedIndex();
                                int hour = wv_hour.getSeletedIndex();
                                int min = wv_min.getSeletedIndex();
                                int sec = wv_sec.getSeletedIndex();

                                sendtosecond(day, hour, min, sec);
                            }
                        })
                        .show();
            }
            default:
        }

    }

    public void sendtosecond(int d, int h, int min, int s) {
        Intent intent = new Intent(MainActivity.this, CountDownActivity.class);
        intent.putExtra("day", d);
        intent.putExtra("hour", h);
        intent.putExtra("minute", min);
        intent.putExtra("second", s);
        startActivity(intent);
    }

    public String[] setNum(int n){
        String[] s = new String[n];
        for (int i = 0; i < n; i++) {
            s[i] = i + "";
        }
        return s;
    }
}
