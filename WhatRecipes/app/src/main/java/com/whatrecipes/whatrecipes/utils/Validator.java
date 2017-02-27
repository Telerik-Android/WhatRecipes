package com.whatrecipes.whatrecipes.utils;

import android.widget.EditText;
/**
 * Created by fatal on 2/17/2017.
 */

public class Validator {
    public static boolean stringEmptyOrNull(String... strings) {
        for (String current : strings) {
            if (current == null || current.isEmpty() || current.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateRequiredEditTextFields(String messageOnFail, EditText... et){
        for(EditText current: et)
        if(stringEmptyOrNull(current.getText().toString())) {
            current.setError(messageOnFail);
            return false;
        }
        return true;
    }

    public final static boolean isValidEmail(String target) {
        if (stringEmptyOrNull(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidPasswordLength(String password){
        if(password.length()<= 5) {
            return false;
        }else{
            return true;
        }
    }

}
