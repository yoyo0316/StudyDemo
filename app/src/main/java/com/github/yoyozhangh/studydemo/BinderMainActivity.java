package com.github.yoyozhangh.studydemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.yoyozhangh.studydemo.binder.SecondActivity;

import java.util.List;

public class BinderMainActivity extends AppCompatActivity {

    private final static String TAG = BinderMainActivity.class.getSimpleName();


    private IStudentManger mRemoteStudentManger;
    private int student_size = 2;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获取到IstudentManger 对象
            IStudentManger iStudentManger = IStudentManger.Stub.asInterface(service);
            mRemoteStudentManger = iStudentManger;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mRemoteStudentManger = null;
            Log.e(TAG, "onServiceDisconnected.threadName :" + Thread.currentThread().getName());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.binder_main_activity);
        Intent intent = new Intent(this, StudentMangerService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    public void toSecondActivity(View view) {
        Student.NAME = "netEase";
        startActivity(new Intent(this, SecondActivity.class));
        Log.e(TAG, "toSecondActivity name=" + Student.NAME);
    }

    public void getStudentList(View view) {
        Toast.makeText(this, "正在获取学生列表", Toast.LENGTH_SHORT).show();
        //由于服务端的查询是耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mRemoteStudentManger != null) {
                    try {
                        final List<Student> studentList = mRemoteStudentManger.getStudentList();
                        student_size = studentList.size();
                        Log.e(TAG, "获取到的学生列表 " + studentList.toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void addStudent(View view) {
        if (mRemoteStudentManger != null) {
            int student_id = student_size + 1;
            Student newStudent = new Student(student_id, "zhangsan" + student_id, "man");
            try {
                mRemoteStudentManger.addStudent(newStudent);
                Log.e(TAG, "添加一位学生 " + newStudent.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
