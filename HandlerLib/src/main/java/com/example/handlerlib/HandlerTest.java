package com.example.handlerlib;

import java.util.UUID;

public class HandlerTest {
    public static void main(String[] args) {

        // 轮询器初始化
        Looper.prepare();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println(Thread.currentThread().getName() + ",received:" + msg.toString());
            }
        };

        // 子线程发生消息
        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    while (true) {
                        Message message = new Message();
                        message.what = 1;
                        synchronized (UUID.class){
                            message.obj = Thread.currentThread().getName() + " ,send Message:" + UUID.randomUUID().toString();
                        }
                        System.out.println(message);
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }.start();
        }
        Looper.loop();


    }
}
