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

    public static boolean validateRequiredEditTextField(EditText et, String messageOnFail){
        if(stringEmptyOrNull(et.getText().toString())) {
            et.setError(messageOnFail);
            return false;
        }
        return true;
    }

}
