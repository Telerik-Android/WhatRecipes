package com.whatrecipes.whatrecipes.data;

/**
 * Created by fatal on 2.3.2017 г..
 */

public abstract class FirebaseObject {
    private String uid;

    protected FirebaseObject() {

    }

    public String getUid() {
        return uid;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }
}
