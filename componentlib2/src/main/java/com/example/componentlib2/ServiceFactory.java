package com.example.componentlib2;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    public static ServiceFactory getInstance() {
        return instance;
    }

    private ServiceFactory() {

    }

    private ILoginService mLoginService;
    private IMineService mMineService;


    public ILoginService getLoginService() {
        return mLoginService;
    }

    public void setLoginService(ILoginService mLoginService) {
        this.mLoginService = mLoginService;
    }

    public IMineService getMineService() {
        return mMineService;
    }

    public void setMineService(IMineService mMineService) {
        this.mMineService = mMineService;
    }
}
