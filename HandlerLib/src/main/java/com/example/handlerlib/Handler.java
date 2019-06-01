package com.example.handlerlib;

public class Handler {

    private MessageQueue mQueue;
    private Looper mLooper;

    public Handler() {
        //��ȡ���̵߳�Looper����
        mLooper = Looper.myLooper();
        this.mQueue = mLooper.mQueue;
    }

    /**
     * ������Ϣ��ѹ�˶���
     */
    public void sendMessage(Message msg) {
        msg.target = this;//��Handler �󶨵�msg �� target
        mQueue.enqueueMessage(msg);
    }

    public void handleMessage(Message msg) {

    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}
