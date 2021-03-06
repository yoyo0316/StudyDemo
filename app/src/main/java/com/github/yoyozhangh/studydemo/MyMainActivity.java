package com.github.yoyozhangh.studydemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.componentlib2.ServiceFactory;
import com.github.yoyozhangh.studydemo.net.IJsonDataListener;
import com.github.yoyozhangh.studydemo.net.NeNetUtil;
import com.github.yoyozhangh.studydemo.net.ResponseClass;

public class MyMainActivity extends AppCompatActivity {

    private String url = "http://v.juhe.cn/historyWeather/citys?province_id=2&key=bb52107206585ab074f5e59a8c73875b";
//    private String url = "http://xxxx";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_activity_main);

        findViewById(R.id.goLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceFactory.getInstance().getLoginService().launch(MyMainActivity.this, "");
            }
        });

        findViewById(R.id.goMine).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceFactory.getInstance().getMineService().launch(MyMainActivity.this, 1);
            }
        });

        findViewById(R.id.getFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                ServiceFactory.getInstance().getLoginService().newUserInfoFragment(getSupportFragmentManager(), R.id.container, bundle);

            }
        });

//        sendRequest();
//        hand();
    }

    private void hand() {
        final Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };


        new Thread() {
            @Override
            public void run() {

                Message message = Message.obtain();
                message.what = 1;
                message.arg1 = 666;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    private void sendRequest() {
        NeNetUtil.sendJsonRequest(url, null, ResponseClass.class, new IJsonDataListener<ResponseClass>() {
            @Override
            public void onSuccess(ResponseClass o) {
                Log.e("===>", "onSuccess: " + o.toString());
            }

            @Override
            public void onFailure() {

            }
        });
    }


}
