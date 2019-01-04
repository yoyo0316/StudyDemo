package com.github.yoyozhangh.studydemo.thread;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.yoyozhangh.studydemo.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yoyozhangh on 2019/1/2.
 */

public class ThreadMainActivity extends AppCompatActivity {

    private Context mContext;
    private final static String TAG = ThreadMainActivity.class.getSimpleName();
    /**
     * 图片地址集合
     */
    private String url[] = {
            "http://e.hiphotos.baidu.com/image/pic/item/a8773912b31bb05114a597be3b7adab44bede0a7.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/adaf2edda3cc7cd9ebe507433401213fb90e915b.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a29cf18475f9425bc315d607c4b.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/a686c9177f3e6709c7c4d26e36c79f3df9dc555e.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de05ee5ff799eef01f3b2979c1.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/14ce36d3d539b600501bde11e450352ac75cb7bb.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/35a85edf8db1cb131da73787d054564e93584b53.jpg"
    };

    private ImageView imageView;

    private Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "handleMessage() called with: 次数 = [" + msg.what + "]");
            ImageModel model = (ImageModel) msg.obj;
            imageView.setImageBitmap(model.bitmap);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.thread_activity);
        imageView = (ImageView) findViewById(R.id.imge);
        mContext = this;

        findViewById(R.id.handler_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建实例对象
                HandlerThread handlerThread = new MyHandlerThread("downloadImage");

                // 必须先开启线程
                handlerThread.start();

                //构建异步 handler
                Handler childHandler = new Handler(handlerThread.getLooper(), new ChildCallback());

                for (int i = 0; i < url.length; i++) {
                    // 每隔1s 去更新图片
                    childHandler.sendEmptyMessageDelayed(i, 1000 * i);
                }
            }
        });


       findViewById(R.id.intent_service).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(mContext,MyIntentService.class);
               for (int i= 0;i< url.length;i++){
                   intent.putExtra(MyIntentService.DOWNLOAD_URL,url[i]);
                   intent.putExtra(MyIntentService.INDEX_FLAG,i);
                   startService(intent);
               }
               UpdateUI updateUI = new UpdateUI() {
                   @Override
                   public void updateUI(Message message) {
                       mUIHandler.sendMessageDelayed(message,message.what*1000);
                   }
               };

               MyIntentService.setUpdateUI(updateUI);
           }
       });

    }

    /**
     * 该callback 运行在子线程中
     */
    class ChildCallback implements Handler.Callback {

        @Override
        public boolean handleMessage(Message msg) {
            // 在子线程中 进行相应的网络请求
            //do some things
            Bitmap bitmap = Utils.downloadUrlBitmap(url[msg.what]);
            ImageModel imageModel = new ImageModel();
            imageModel.bitmap = bitmap;
            imageModel.url = url[msg.what];
            Message message = new Message();
            message.what = msg.what;
            message.obj = imageModel;

            // 通知主线程去更新UI
            mUIHandler.sendMessage(message);
            return false;
        }
    }




}


