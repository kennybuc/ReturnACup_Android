package com.app.flexivendsymbol;

import android.app.Application;

import com.app.flexivendsymbol.helpers.LocalStorage;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initSingletons();
    }

    private void initSingletons() {
        LocalStorage.init(this);
    }

}
