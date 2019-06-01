package com.example.handlerlib;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MessageQueue {

    // ͨ������Ľṹ�洢 Message ����
    Message[] items;

    //�������ӵ�Ԫ�ص�����λ��
    int putIndex;
    int takeIndex;

    //������
    int count;

    private Lock lock;
    //��������
    private Condition notEmpty;
    private Condition notFull;


    public MessageQueue() {
        //��Ϣ����Ӧ���д�С����
        this.items = new Message[50];
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    /**
     * �������
     *
     * @param msg
     */
    public void enqueueMessage(Message msg) {
        try {
            lock.lock();
            //��Ϣ�������ˣ����߳�ֹͣ������Ϣ ����
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            items[putIndex] = msg;
            //ѭ��ȡֵ
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            count++;
            //���µ�Message ����֪ͨ���߳�
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }

    }

    /**
     * ����
     *
     * @return
     */
    public Message next() {
        Message msg = null;
        try {
            lock.lock();
            //�����Ϣ�����ǿյģ������߳�looper ֹͣ��ѯ
            while (count == 0){
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            msg = items[takeIndex];//ȡ��
            items[takeIndex] = null;//Ԫ���ÿ�
            //ѭ��ȡֵ
            takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;
            count--;
            //ʹ����һ��Message ���󣬼�������
            notFull.signalAll();
        } finally {
            lock.unlock();
        }


        return msg;
    }
}
