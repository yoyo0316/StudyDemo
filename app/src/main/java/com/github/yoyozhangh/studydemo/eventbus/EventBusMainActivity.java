package com.github.yoyozhangh.studydemo.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.yoyozhangh.studydemo.R;


public class EventBusMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_eventbus);


        EventBus.getDefault().register(this);
        Intent intent = new Intent();
        intent.setClass(this, SecondActivity.class);
        startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(EventBean bean) {
        Log.e("====>", "getMessage: " + bean.toString());
        Log.e("====>", "getMessage threadName :" + Thread.currentThread().getName());
    }

}
