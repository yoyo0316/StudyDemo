package com.github.yoyozhangh.studydemo.binder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.yoyozhangh.studydemo.R;
import com.github.yoyozhangh.studydemo.Student;

public class SecondActivity extends AppCompatActivity {

    private final static String TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "SecondActivity onCreate: name =" + Student.NAME);
    }
}
