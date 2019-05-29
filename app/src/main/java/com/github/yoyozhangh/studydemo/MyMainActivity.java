package com.github.yoyozhangh.studydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

        sendRequest();
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
