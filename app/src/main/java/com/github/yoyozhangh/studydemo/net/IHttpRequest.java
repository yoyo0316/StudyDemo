package com.github.yoyozhangh.studydemo.net;

public interface IHttpRequest {


    void setUrl(String url);

    void setData(byte[] data);

    void setListener(CallBackListener  callBackListener);

    void execute();
}
