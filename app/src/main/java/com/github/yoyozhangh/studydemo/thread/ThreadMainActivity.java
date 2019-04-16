package com.github.yoyozhangh.studydemo.thread;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.github.yoyozhangh.studydemo.db.Person;
import com.github.yoyozhangh.studydemo.db.PersonContentObserver;

/**
 * Created by yoyozhangh on 2019/1/2.
 */

public class ThreadMainActivity extends AppCompatActivity {

    private Context mContext;
    private PersonContentObserver mObserver;

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

        mObserver = new PersonContentObserver(new Handler(), this);
        getContentResolver().registerContentObserver(Person.CONTENT_URI_INSERT, true, mObserver);
        getContentResolver().registerContentObserver(Person.CONTENT_URI_DELETE, true, mObserver);
        getContentResolver().registerContentObserver(Person.CONTENT_URI_DELETE, true, mObserver);
        getContentResolver().registerContentObserver(Person.CONTENT_URI_DELETE, true, mObserver);

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
                Intent intent = new Intent(mContext, MyIntentService.class);
                for (int i = 0; i < url.length; i++) {
                    intent.putExtra(MyIntentService.DOWNLOAD_URL, url[i]);
                    intent.putExtra(MyIntentService.INDEX_FLAG, i);
                    startService(intent);
                }
                UpdateUI updateUI = new UpdateUI() {
                    @Override
                    public void updateUI(Message message) {
                        mUIHandler.sendMessageDelayed(message, message.what * 1000);
                    }
                };

                MyIntentService.setUpdateUI(updateUI);
            }
        });

        findViewById(R.id.async_query_handler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 内容提供者访问对象
                ContentResolver resolver = mContext.getContentResolver();

                for (int i = 0; i < 10; i++) {
                    //
                    ContentValues values = new ContentValues();
                    values.put(Person.KEY_NAME, "wanglei" + i);
                    values.put(Person.KEY_AGE, i);
                    Uri uri = resolver.insert(Person.CONTENT_URI_INSERT, values);
                    Log.i(TAG, "uri: " + uri);
                    long id = ContentUris.parseId(uri);
                    Log.i(TAG, "添加到: " + id);
                }

                MyAsyncQueryHandler asyncQueryHandler = new MyAsyncQueryHandler(resolver);
                asyncQueryHandler.asyncQueryPersonDb();
            }
        });

        findViewById(R.id.pool).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019/1/6  线程池的使用演示 
                // TODO: 2019/1/6  asyncTask 的使用演示 
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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mObserver);
    }
}


