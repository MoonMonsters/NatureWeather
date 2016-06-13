package edu.csuft.application;

import android.app.Application;

/**
 * Created by Chalmers on 2016-05-29 12:38.
 * email:qxinhai@yeah.net
 */
public class MyApplication extends Application {

    private static MyApplication instance;


    public static MyApplication getContext(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
    }
}

