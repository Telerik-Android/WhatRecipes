package com.whatrecipes.whatrecipes.data.firebase;

/**
 * Created by fatal on 2/19/2017.
 */

public interface ResponseListener {
    void onSuccessfulAuthentication();

    void onFailedAuthentication();
}