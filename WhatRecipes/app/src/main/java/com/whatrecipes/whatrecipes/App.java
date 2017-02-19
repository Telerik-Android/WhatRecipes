package com.whatrecipes.whatrecipes;

import android.app.Application;

import com.whatrecipes.whatrecipes.data.firebase.DaggerFirebaseComponent;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseComponent;
import com.whatrecipes.whatrecipes.data.firebase.FirebaseModule;

public class App extends Application {
//    private NetComponent mNetComponent;
    private FirebaseComponent mFirebaseComponent;
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
//        mNetComponent = DaggerNetComponent.builder()
//                .appModule(new AppModule(this))
//                .netModule(new NetModule("http://192.168.199.212:3000/"))
//                .build();


        mFirebaseComponent = DaggerFirebaseComponent.builder()
                .appModule(new AppModule(this))
                .firebaseModule(new FirebaseModule())
                .build();

    }
    public static App get() {
        return sInstance;
    }
//
//    public NetComponent getNetComponent() {
//        return mNetComponent;
//    }
    public FirebaseComponent getFirebaseComponent(){
        return mFirebaseComponent;
    }
}
