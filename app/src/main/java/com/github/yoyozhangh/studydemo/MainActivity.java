package com.github.yoyozhangh.studydemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.yoyozhangh.studydemo.serial.SerialMainActivity;
import com.github.yoyozhangh.studydemo.supportlibrary.DesignActivity;
import com.github.yoyozhangh.studydemo.supportlibrary.OpearteDbActivity;
import com.github.yoyozhangh.studydemo.thread.ThreadMainActivity;

/**
 * Created by yoyozhangh on 2018/12/21.
 */

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        findViewById(R.id.opreate_db).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,OpearteDbActivity.class));
            }
        });


        findViewById(R.id.design_support_library).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(mContext,DesignActivity.class));
            }
        });

        findViewById(R.id.thread_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,ThreadMainActivity.class));
            }
        });

        findViewById(R.id.serial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,SerialMainActivity.class));
            }
        });

        findViewById(R.id.webview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext,WebViewMainActivity.class));
            }
        });
    }
}
