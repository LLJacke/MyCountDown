package com.lljackie.mycountdown;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_1;
    Button bt_2;
    Button bt_3;
    Button bt_4;
    Button bt_5;
    Button bt_cus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            case R.id.bt_cus:
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
}
