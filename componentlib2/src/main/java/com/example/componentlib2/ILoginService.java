package com.example.componentlib2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public interface ILoginService {

    // 跳转 从主 app  跳转到 login app
    void launch(Context context, String targetClass);

    //获取数据主app从login app 获取返回数据并显示
    Fragment newUserInfoFragment(FragmentManager fragmentManager, int viewId, Bundle bundle);

}
