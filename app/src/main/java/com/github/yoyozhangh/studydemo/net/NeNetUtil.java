package com.github.yoyozhangh.studydemo.net;

public class NeNetUtil {

    public static <T, M> void sendJsonRequest(String url, T requestData, Class<M> respone, IJsonDataListener listener) {
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListener callBackListener = new JsonCallBackListener<>(respone, listener);
        HttpTask httpTask = new HttpTask(url, requestData, httpRequest, callBackListener);
        ThreadPoolManger.getInstance().addTask(httpTask);
    }
}
