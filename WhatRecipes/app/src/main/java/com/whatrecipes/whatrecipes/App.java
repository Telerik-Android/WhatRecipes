package com.whatrecipes.whatrecipes;

import android.app.Application;

import com.whatrecipes.whatrecipes.data.firebase.FirebaseModule;
import com.whatrecipes.whatrecipes.presenters.PresenterModule;

public class App extends Application {
    private static App sInstance;

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .presenterModule(new PresenterModule())
                .build();
        component.inject(this);
    }

    public static App get() {
        return sInstance;
    }

    public AppComponent component() {
        return component;
    }
}
