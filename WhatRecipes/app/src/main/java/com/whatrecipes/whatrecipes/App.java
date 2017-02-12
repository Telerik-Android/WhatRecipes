package com.whatrecipes.whatrecipes;

import android.app.Application;

import com.whatrecipes.whatrecipes.data.DaggerNetComponent;
import com.whatrecipes.whatrecipes.data.FirebaseModule;
import com.whatrecipes.whatrecipes.data.NetComponent;
import com.whatrecipes.whatrecipes.data.NetModule;
import com.whatrecipes.whatrecipes.presenters.PresentationModule;

public class App extends Application {
    private NetComponent mNetComponent;
    private AppComponent mAppComponent;
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://192.168.199.212:3000/"))
                .build();


        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .presentationModule(new PresentationModule())
                .build();


    }
    public static App get() {
        return sInstance;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
