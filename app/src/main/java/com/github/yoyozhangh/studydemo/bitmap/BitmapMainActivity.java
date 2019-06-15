package com.github.yoyozhangh.studydemo.bitmap;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.library.CompressConfig;
import com.example.library.Photo;
import com.example.library.utils.CachePathUtils;
import com.example.library.utils.CommonUtils;
import com.example.library.utils.Constants;
import com.example.library.utils.UriParseUtils;
import com.github.yoyozhangh.studydemo.R;

import java.io.File;
import java.util.ArrayList;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class BitmapMainActivity extends AppCompatActivity {

    private CompressConfig compressConfig;//拍照配置

    private ProgressDialog dialog;//压缩dialog

    private String cameraCachePath;//拍照后，源文件路径

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bitmap);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] perms = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (checkSelfPermission(perms[0]) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(perms[1]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(perms, 200);
            }
        }
        testLuban();
    }

    private void testLuban() {
        String mCacheDir = Constants.BASE_CACHE_PATH + getPackageName() + "/cache/" + Constants.COMPRESS_CACHE;
        Log.e("=====>", "testLuban: " + mCacheDir);
        Luban.with(this)
                .load("")//需要压缩的图片或图片集合
                .ignoreBy(100)// 100Kb 以下，不启用压缩
                .setTargetDir(mCacheDir)//压缩到什么目录
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        Log.e("=====>", "onStart: ");
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.e("=====>", "onSuccess: " + file.getAbsolutePath());
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("=====>", "onError: " + e);
                        // TODO 当压缩过程出现问题时调用
                    }
                }).launch();
    }

    //相册
    public void album(View view) {
        CommonUtils.openAlbun(this, Constants.ALBUM_CODE);
    }

    //拍照按钮
    public void camera(View view) {

        Uri outputUri;
        File file = CachePathUtils.getCacmeCacheFile();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            outputUri = UriParseUtils.getCameraOutPutUri(this, file);
        } else {
            outputUri = Uri.fromFile(file);
        }

        cameraCachePath = file.getAbsolutePath();
        CommonUtils.hasCamera(this, CommonUtils.getCameraIntent(outputUri), Constants.CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 拍照后返回

        if (requestCode == Constants.CAMERA_CODE && resultCode == RESULT_OK) {
            //开始压缩
            preCompress(cameraCachePath);
        }

        //相册返回
        if (requestCode == Constants.ALBUM_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                String path = UriParseUtils.getPath(this, uri);
                preCompress(path);
            }
        }
    }

    private void preCompress(String photosPath) {

        ArrayList<Photo> photos = new ArrayList<>();
        photos.add(new Photo(photosPath));
        if (!photos.isEmpty()) {

            //开始批量压缩
            compress(photos);

        }
    }

    //开始压缩
    private void compress(ArrayList<Photo> photos) {

    }
}
