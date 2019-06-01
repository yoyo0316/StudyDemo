package com.example.handlerlib;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {

    // 通过数组的结构存储 Message 对象
    Message[] items;

    //入队与出队的元素的索引位置
    int putIndex;
    int takeIndex;

    //计数器
    int count;

    private Lock lock;
    //条件变量
    private Condition notEmpty;
    private Condition notFull;


    public MessageQueue() {
        //消息队列应该有大小限制
        this.items = new Message[50];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * 加入队列
     *
     * @param msg
     */
    public void enqueueMessage(Message msg) {
        try {
            lock.lock();
            //消息队列满了，子线程停止发送消息 阻塞
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            items[putIndex] = msg;
            //循环取值
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            count++;
            //有新的Message 对象，通知主线程
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }

    }

    /**
     * 出队
     *
     * @return
     */
    public Message next() {
        Message msg = null;
        try {
            lock.lock();
            //如果消息队列是空的，，主线程looper 停止轮询
            while (count == 0){
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            msg = items[takeIndex];//取出
            items[takeIndex] = null;//元素置空
            //循环取值
            takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;
            count--;
            //使用了一个Message 对象，继续生产
            notFull.signalAll();
        } finally {
            lock.unlock();
        }


        return msg;
    }
}
