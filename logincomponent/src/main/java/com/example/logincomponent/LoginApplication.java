package com.example.logincomponent;

import android.app.Application;

import com.example.componentlib2.IAppComponent;
import com.example.componentlib2.ServiceFactory;

public class LoginApplication extends Application implements IAppComponent {

    private static Application application;

    public static Application getApplication() {
        return application;
    }

    // 当 login 是 application 的时候 调用 initalliza 方法时，上下文指向的就是 LoginApplication
    @Override
    public void onCreate() {
        super.onCreate();
        initalliza(this);
    }

    //当 login是 lib 的时候 被主 App 调用被赋值
    @Override
    public void initalliza(Application app) {
        application = app;
        ServiceFactory.getInstance().setLoginService(new LoginService());
    }
}
