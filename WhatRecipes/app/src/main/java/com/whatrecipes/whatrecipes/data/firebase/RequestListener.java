package com.whatrecipes.whatrecipes.data.firebase;

/**
 * Created by fatal on 2/19/2017.
 */
public interface RequestListener<T> {
    void onSuccessfulRequest(T callback);

    void onFailedRequest();
}
