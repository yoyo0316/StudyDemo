package com.github.yoyozhangh.studydemo.thread;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.support.annotation.Nullable;

/**
 * Created by yoyozhangh on 2019/1/3.
 */

public class MyIntentService extends IntentService {

    public static final String DOWNLOAD_URL ="download_url";
    public static final String INDEX_FLAG ="index_flag";

    public static UpdateUI updateUI;

    public MyIntentService() {
        super("MyIntentService");
    }

    public static void setUpdateUI( UpdateUI updateUIInterface){
        updateUI = updateUIInterface;
    }

    /**
     * 实现异步任务的方法
     *
     * @param intent Activity 传递过来的Intent ,数据封装在intent中
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        // 在子线程进行网络请求
        Bitmap bitmap = Utils.downloadUrlBitmap(intent.getStringExtra(DOWNLOAD_URL));
        Message message = new Message();
        message.what = intent.getIntExtra(INDEX_FLAG,0);
        ImageModel imageModel = new ImageModel();
        imageModel.bitmap = bitmap;
        imageModel.url = intent.getStringExtra(DOWNLOAD_URL);
        message.obj = imageModel;

        if (updateUI != null){
            updateUI.updateUI(message);
        }
    }

}
