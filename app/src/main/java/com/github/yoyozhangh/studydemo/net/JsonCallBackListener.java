package com.github.yoyozhangh.studydemo.net;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonCallBackListener<T> implements CallBackListener {

    private Class<T> responseClass;

    private Handler mHandler = new Handler(Looper.getMainLooper());
    private IJsonDataListener jsonDataListener;

    public JsonCallBackListener(Class<T> responseClass, IJsonDataListener listener) {
        this.responseClass = responseClass;
        this.jsonDataListener = listener;
    }

    @Override
    public void onSucess(InputStream inputStream) {
        // 将流转换成我们对应的 T 类型
        String response = getContent(inputStream);
        final T clazz = JSON.parseObject(response, responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                jsonDataListener.onSuccess(clazz);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    @Override
    public void onFailure() {

    }
}
