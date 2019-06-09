package com.github.yoyozhangh.studydemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class StudentMangerService extends Service {

    private static final String TAG = StudentMangerService.class.getSimpleName();
    //判断当前的service是否销毁
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);


    // 用于进程安全的传输列表类
    private CopyOnWriteArrayList<Student> mStudentList = new CopyOnWriteArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //在服务端先手动添加两个学生
        mStudentList.add(new Student(1, "netease", "man"));
        mStudentList.add(new Student(2, "net", "woman"));
    }


    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyStudentManger();
    }

    class MyStudentManger extends IStudentManger.Stub {

        @Override
        public List<Student> getStudentList() throws RemoteException {
            SystemClock.sleep(2000);//休眠2s 模拟服务端的耗时操作
            return mStudentList;
        }

        @Override
        public void addStudent(Student student) throws RemoteException {
            mStudentList.add(student);
        }
    }
}
