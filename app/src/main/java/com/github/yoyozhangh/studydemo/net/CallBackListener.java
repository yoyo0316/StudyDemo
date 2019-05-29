package com.github.yoyozhangh.studydemo.net;

import java.io.InputStream;

public interface CallBackListener {
    void onSucess(InputStream inputStream);

    void onFailure();
}
