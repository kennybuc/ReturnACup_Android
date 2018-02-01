package com.app.flexivendsymbol.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    /**
     * Validate email address
     *
     * @return true if @param email is valid email address,
     * otherwise false
     */
    public static boolean validateEmail(String email) {
        Pattern p = Pattern.compile("[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
