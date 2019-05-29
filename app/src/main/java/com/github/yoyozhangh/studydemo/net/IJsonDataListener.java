package com.github.yoyozhangh.studydemo.net;

public interface IJsonDataListener<T> {
    void onSuccess(T t);

    void onFailure();
}
