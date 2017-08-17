package controllers;

import views.App;

public abstract class Controller {
    protected App appInstance;


    public void setAppInstance(App appInstance){
        this.appInstance = appInstance;
    }


    public abstract void init();
    public abstract void update();
}
