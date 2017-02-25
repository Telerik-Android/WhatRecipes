package com.whatrecipes.whatrecipes.data.firebase.listeners;

/**
 * Created by fatal on 2/19/2017.
 */
public interface RequestListener<T> {
    void onSuccessfulRequest(T callback);

    void onFailedRequest();
}
