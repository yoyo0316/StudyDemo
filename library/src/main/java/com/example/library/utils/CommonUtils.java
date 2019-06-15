package com.example.library.utils;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

public class CommonUtils {

    /**
     * 许多定制的Android 系统并不带相机功能，如果强行调用 程序会崩溃
     *
     * @param activity
     * @param intent
     * @param requestCode
     */
    public static void hasCamera(Activity activity, Intent intent, int requestCode) {
        if (activity == null) {
            throw new IllegalArgumentException("activity is null");
        }

        PackageManager pm = activity.getPackageManager();
        boolean hasCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                || Camera.getNumberOfCameras() > 0;
        if (hasCamera) {
            activity.startActivityForResult(intent, requestCode);
        } else {
            Toast.makeText(activity, "当前设备没有相机", Toast.LENGTH_LONG).show();
            throw new IllegalStateException("当前设备没有相机");
        }

    }

    /**
     * 获取拍照的Intent
     *
     * @param outputUri
     * @return
     */
    public static Intent getCameraIntent(Uri outputUri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置 action 为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        return intent;
    }

    /**
     * 跳转到图库选择
     *
     * @param activity
     * @param requestCode
     */
    public static void openAlbun(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }
}
