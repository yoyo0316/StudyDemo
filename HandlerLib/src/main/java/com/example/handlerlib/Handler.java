package com.example.handlerlib;

public class Handler {

    private MessageQueue mQueue;
    private Looper mLooper;

    public Handler() {
        //获取主线程的Looper对象
        mLooper = Looper.myLooper();
        this.mQueue = mLooper.mQueue;
    }

    /**
     * 发生消息，压人队列
     */
    public void sendMessage(Message msg) {
        msg.target = this;//将Handler 绑定到msg 的 target
        mQueue.enqueueMessage(msg);
    }

    public void handleMessage(Message msg) {

    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}
