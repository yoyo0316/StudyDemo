package com.github.yoyozhangh.studydemo;

import android.app.Application;

import com.example.componentlib2.AppConfig;
import com.example.componentlib2.IAppComponent;


public class MainApplication extends Application implements IAppComponent {

    private static MainApplication application;

    public static MainApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initalliza(this);
    }

    @Override
    public void initalliza(Application app) {
        //将主App的上下文传递到login以及main 中
        for (String component : AppConfig.COMPONENTS) {
            try {
                Class<?> clazz = Class.forName(component);
                Object object = clazz.newInstance();
                if (object instanceof IAppComponent) {
                    ((IAppComponent) object).initalliza(this);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }
}
