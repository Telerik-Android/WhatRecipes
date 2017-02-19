package com.whatrecipes.whatrecipes.data;

import com.whatrecipes.whatrecipes.utils.Validator;

/**
 * Created by fatal on 2/19/2017.
 */

public class User {
    private String userDisplayName;

    public User() {
    }

    public User(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }


    protected boolean validateModel() {
        return !Validator.stringEmptyOrNull(userDisplayName);
    }
}
