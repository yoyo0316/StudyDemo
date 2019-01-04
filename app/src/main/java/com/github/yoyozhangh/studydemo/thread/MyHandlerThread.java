package com.github.yoyozhangh.studydemo.thread;

import android.os.HandlerThread;

/**
 * Created by yoyozhangh on 2019/1/2.
 */

public class MyHandlerThread extends HandlerThread {

    public MyHandlerThread(String name) {
        super(name);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();

        //如果 具体业务需要在 HandlerThread 开始 接收消息之前要进行初始化操作的话
        // 可以重写此函数
    }
}
