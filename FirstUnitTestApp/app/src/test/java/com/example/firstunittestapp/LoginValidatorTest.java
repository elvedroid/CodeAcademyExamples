package com.example.firstunittestapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class LoginValidatorTest {

    LoginValidator loginValidator;

    @Before
    public void setUp() {
        loginValidator = new LoginValidator();
    }

    @Test
    public void whenValidEmail_returnTrue() {
        String email = "test@valid.com";
        boolean isEmailValid = loginValidator.validateEmail(email);
        assertTrue(isEmailValid);
    }

    @Test
    public void whenInvalidEmail_returnFalse() {
        String email1 = "invalidEmail";
        boolean isEmailValid = loginValidator.validateEmail(email1);
        assertFalse(isEmailValid);

        String email2 = "invalidEmail@";
        boolean isEmailValid2 = loginValidator.validateEmail(email2);
        assertFalse(isEmailValid2);

        String email3 = "invalidEmail@fd@fd.com";
        boolean isEmailValid3 = loginValidator.validateEmail(email3);
        assertFalse(isEmailValid3);

        String email4 = "invalidEmail@dfosnf";
        boolean isEmailValid4 = loginValidator.validateEmail(email4);
        assertFalse(isEmailValid4);

        String email5 = "invalidEmail@test.com.commkdlid";
        boolean isEmailValid5 = loginValidator.validateEmail(email5);
        assertFalse(isEmailValid5);

        boolean isEmailValid6 = loginValidator.validateEmail(null);
        assertFalse(isEmailValid6);
    }

    @Test
    public void whenValidPassword_ReturnTrue() {
        String password = "123456";
        boolean isValidPassword = loginValidator.validatePassword(password);
        assertTrue(isValidPassword);
    }

    @Test
    public void whenInvalidPassword_ReturnFalse() {
        String password = "1234";
        boolean isValidPassword = loginValidator.validatePassword(password);
        assertFalse(isValidPassword);

        boolean isValidPassword2 = loginValidator.validatePassword(null);
        assertFalse(isValidPassword2);
    }

}
