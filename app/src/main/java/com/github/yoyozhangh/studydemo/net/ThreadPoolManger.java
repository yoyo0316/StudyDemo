package com.github.yoyozhangh.studydemo.net;


import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManger {
    private static ThreadPoolManger threadPoolManger = new ThreadPoolManger();

    public static ThreadPoolManger getInstance() {
        return threadPoolManger;
    }

    private ThreadPoolManger() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        //将拒绝的线程 重新放回队列
                        addTask(r);
                    }
                });
        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    //创建监听 线程 不停的获取
    public Runnable coreThread = new Runnable() {
        Runnable runn = null;

        @Override
        public void run() {
            while (true) {
                try {
                    runn = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runn);
            }
        }
    };


    //创建队列，将网络请求任务添加到队列中 (队列 特性  先进先出)
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque<>();

    public void addTask(Runnable runnable) {
        if (runnable != null) {
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    public ThreadPoolExecutor mThreadPoolExecutor;

    //创建延迟队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public void addDelayTask(HttpTask httpTask) {
        if (httpTask != null) {
            httpTask.setDelayTime(3000);
            mDelayQueue.offer(httpTask);
        }
    }

    //创建延迟线程
    public Runnable delayThread = new Runnable() {
        HttpTask httpTask = null;

        @Override
        public void run() {
            while (true) {
                try {
                    httpTask = mDelayQueue.take();
                    // 如果说当前任务 重试次数小于 三次 继续将其丢给线程池管理，否则直接放弃
                    if (httpTask.getRetryCount() < 3) {
                        mThreadPoolExecutor.execute(httpTask);
                        httpTask.setRetryCount(httpTask.getRetryCount() + 1);
                        Log.e("===重试机制===", httpTask.getRetryCount() + "  " + System.currentTimeMillis());
                    } else {
                        Log.e("===重试机制===", "重试超过 3次，直接放弃");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
