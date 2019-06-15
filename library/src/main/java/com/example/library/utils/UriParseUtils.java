package com.example.library.utils;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.support.v4.content.FileProvider;

import java.io.File;

public class UriParseUtils {

    /**
     * 创建一个图片文件输出路径的URI（FileProvider）
     *
     * @param context
     * @param file
     * @return
     */
    private static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, getFileProvider(context), file);
    }

    /**
     * 获取FileProvider路径  适配6.0+
     *
     * @param context
     * @return
     */
    private static String getFileProvider(Context context) {
        return context.getPackageName() + ".fileprovider";
    }

    /**
     * 获取拍照后照片的Uri
     *
     * @param context
     * @param cacheFile
     * @return
     */
    public static Uri getCameraOutPutUri(Context context, File cacheFile) {
        return getUriForFile(context, cacheFile);
    }

    //    // TODO: 2019/6/15
    public static String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExtetnalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownLoadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri constentUri = ContentUris.withAppendedId(Uri.parse("content:/downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, constentUri, null, null);
            }
        }
        return null;
    }

    // TODO: 2019/6/15  
    private static boolean isDownLoadsDocument(Uri uri) {
        return false;
    }

    // TODO: 2019/6/15  
    private static String getDataColumn(Context context, Uri constentUri, Object o, Object o1) {
        return null;
    }

    // TODO: 2019/6/15
    private static boolean isExtetnalStorageDocument(Uri uri) {
        return false;
    }

}
