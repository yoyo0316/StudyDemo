package com.github.yoyozhangh.studydemo.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.yoyozhangh.studydemo.R;


public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_eventbus);

        findViewById(R.id.getMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventBus.getDefault().post(new EventBean("zhanghe","123"));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("====>", "theadName :" + Thread.currentThread().getName());
                        EventBus.getDefault().post(new EventBean("zhanghe", "123"));
                    }
                }).start();
            }
        });

    }
}
