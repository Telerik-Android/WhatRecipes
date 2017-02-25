package com.whatrecipes.whatrecipes.data.firebase;

import com.whatrecipes.whatrecipes.data.firebase.listeners.ResponseListener;

/**
 * Created by fatal on 2/19/2017.
 */
public interface IFirebaseAuthenticationInteractor {
    void logTheUserIn(String email, String password, ResponseListener listener);

    void registerUser(String email, String password, ResponseListener listener);

    void logTheUserOut();

    String getLoggedInUserDisplayName();

    void changeUserDisplayName(String usernameToSet);

    void changeUserImageUrl(String imageUrl);

    String getLoggedInUserEmail();

    String getLoggedInUserImageURL();
}
