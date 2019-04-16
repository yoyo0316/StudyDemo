package com.github.yoyozhangh.studydemo.serial;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.yoyozhangh.studydemo.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by yoyozhangh on 2019/1/6.
 */

public class SerialMainActivity extends AppCompatActivity {

    private final static String TAG = SerialMainActivity.class.getSimpleName();
    private Context mContext;

    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serial_activity);
        textView = (TextView) findViewById(R.id.tv);
        mContext = this;
        findViewById(R.id.serialize_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    serializePerson();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.deserialize_person).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deserializePerson();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void serializePerson() throws FileNotFoundException, IOException {

        Person person = new Person();
        person.setName("yoyozhangh");
        person.setAge(28);
        person.setSex("男");

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(mContext.getCacheDir() + "/person.txt")));
        outputStream.writeObject(person);
        Log.d(TAG, "serializePerson called  Person对象 序列化成功");
        textView.setText("Person对象 序列化成功");
        outputStream.close();
    }

    private Person deserializePerson() throws Exception, IOException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(new File(mContext.getCacheDir() + "/person.txt")));
        Person person = (Person) inputStream.readObject();
        Log.d(TAG, "deserializePerson called Person对象 反序列化成功");
        Log.d(TAG, "deserializePerson called " + person);
        textView.setText("Person对象 反序列化成功\n" + person.toString());
        return person;
    }
}
