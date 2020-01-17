package com.example.firstunittestapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public boolean validatePassword(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 6;
    }
}
