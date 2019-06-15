package com.example.library.utils;

import android.os.Environment;

public class Constants {
    public final static int CAMERA_CODE = 1001;
    public final static int ALBUM_CODE = 1002;
    public final static String BASE_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/";

    public static final String COMPRESS_CACHE = "compress_cache";

}
