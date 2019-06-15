package com.example.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CompressUtil {

    /**
     * 压缩文件到Bitmap
     *
     * @param filePath 图片文件地址
     * @param quality  要压缩的质量
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int quality) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        //计算采样，现在主流手机比较多是800*480 分辨率
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        //用样本大小集解码位图
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if (bitmap == null) {
            return null;
        }
        //读取图片角度
        int degree = readPitcureDegree(filePath);

        //旋转位图
        bitmap = rotateBitmap(bitmap, degree);

        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        } finally {
            try {

                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return bitmap;
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
        if (bitmap == null)
            return null;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    private static int readPitcureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    /**
     * 按大小压缩
     *
     * @return
     */
    private Bitmap getImage(String srcpath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时 把 options.inJustDecodeBounds 设回 true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap;
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;

        //现在主流手机比较多是800*480 分辨率 所以高和宽我们设置为

        float hh = 800f;//这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f

        //缩放比，由于是固定比例缩放，只用高或宽其中一个数据进行计算即可
        int be = 1;// 表示不进行缩放
        if (w > h && w > ww) {//如果宽度大的话 根据宽度大小进行压缩
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度大的话 根据高度大小进行压缩
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;
        // 重新载入图片 ，注意此时已经把options.inJustDecodeBounds 设回 false 了
        bitmap = BitmapFactory.decodeFile(srcpath, newOpts);
        return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 质量压缩
     *
     * @param image
     * @return
     */
    private Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //质量压缩方法，这里100 表示不压缩 把压缩后的数据存放到 baos 中
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {//循环判断 如果压缩图片师范大于100kb,大于继续压缩
            baos.reset();//重置 baos 即清空 baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos); //这里压缩 options 把压缩后的数据存放到 baos 中
            options -= 10;
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据存放到 ByteArrayInputStream
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream 数据生成图片
        return bitmap;
    }

}
