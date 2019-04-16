package com.github.yoyozhangh.studydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;

/**
 * 简介：
 * WebView 是一个基于WebKit 引擎，展示Web页面的控件
 * Android的Webview在低版本和高版本采用了不同的webkit版本内核，4.4后直接使用了Chrome
 * <p>
 * <p>
 * 作用：
 * 显示和渲染Web页面
 * 直接使用HTML 文件（网络和本地assets中）作布局
 * 可以和JavaScript 交互调用
 * WebView控件功能强大，除了具有一般View的属性和设置外，还可以对url请求、页面加载、渲染、页面交互进行强大的处理
 * <p>
 * 使用介绍：
 * 一般来说WebView 可单独使用，可联合其工具类一起使用
 * WebView 类自身的常见方法
 * WebView 最常用的工具类 WebSettings WebViewClient WebChromeClient
 * Android 和 Js 的交互
 * <p>
 * Created by yoyozhangh on 2019/1/6.
 */

public class WebViewMainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);

        webView = (WebView) findViewById(R.id.webview);

        findViewById(R.id.web_view_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();

            }
        });
    }

    /**
     * 加载方式根据资源分为 3 种
     */
    private void load() {

        //方式1. 加载一个网页：
        webView.loadUrl("https://blog.csdn.net/yllp_1230/article/details/80655350");

        //方式2：加载apk包中的html页面
        webView.loadUrl("file:///android_asset/test.html");

        //方式3：加载手机本地的html页面
        webView.loadUrl("content://com.android.htmlfileprovider/sdcard/test.html");

        // 方式4： 加载 HTML 页面的一小段内容
        // WebView.loadData(String data, String mimeType, String encoding)
        // 参数说明：
        // 参数1：需要截取展示的内容
        // 内容里不能出现 ’#’, ‘%’, ‘\’ , ‘?’ 这四个字符，若出现了需用 %23, %25, %27, %3f 对应来替代，否则会出现异常
        // 参数2：展示内容的类型
        // 参数3：字节码
    }


}
