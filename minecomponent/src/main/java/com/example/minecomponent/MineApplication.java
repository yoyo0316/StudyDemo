package com.example.minecomponent;

import android.app.Application;

import com.example.componentlib2.IAppComponent;
import com.example.componentlib2.ServiceFactory;

public class MineApplication extends Application implements IAppComponent {

    private static Application application;

    public static Application getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initalliza(this);
    }

    @Override
    public void initalliza(Application app) {
        application = app;
        ServiceFactory.getInstance().setMineService(new MineService());
    }
}
