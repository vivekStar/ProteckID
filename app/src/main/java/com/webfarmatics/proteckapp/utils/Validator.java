package com.webfarmatics.proteckapp.utils;

import android.content.Context;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static final int VALID = 1;

    public static final int INVALID = 0;
    public static final int INVALID_EMTRY = -1;
    public static final int INVALID_LENGTH = -2;
    public static final int INVALID_STARTS_WITH_ZERO = -3;
    public static final int INVALID_EMTRY_PASS = -4;

    public static final int INVALID_LENGTH_SIX = -5;

    public static final int INVALID_EMTRY_CONF_PASS = -6;

    public static final int INVALID_PASS_MISSMATCH = -7;

    public static boolean addressTextValidation(String text) {
        Pattern p = Pattern.compile("[^a-zA-Z. /,-:]");
        boolean b = p.matcher(text).find();
        return b;
    }

    /**
     * Author - Ashish Arun Zade It checks for text contains AlfaNumeric values
     *
     * @param text
     * @return
     */
    public static boolean alfaNumericTextValidation(String text) {
        Pattern p = Pattern.compile("[^a-zA-Z0987654321]");
        boolean b = p.matcher(text).find();
        return b;
    }

    public static int checkForText(String name) {
        if (name == null || name.length() == 0) {
            return INVALID_EMTRY;
        }
        return VALID;
    }


    public static boolean validateText(String name) {
        if (name == null || name.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * Author - Ashish Arun Zade  This method checks for if text contains more
     * than 35 char or not
     *
     * @param text
     * @return true or false
     */

    public static boolean is35Chars(String text) {
        return text.length() > 35;
    }

    /**
     * Author - Ashish Arun Zade This method checks for if text contains more
     * than 35 char or not
     *
     * @param text
     * @return true or false
     */

    public static int checkLength(String text) {
        if (text.length() < 6)
            return INVALID_LENGTH_SIX;
        else
            return VALID;
    }

    /**
     * Author - Ashish Arun Zade It checks for text contains special character.
     * this method mainly used for name of person or things
     *
     * @param text
     * @return
     */

    public static boolean textContainsSpecialChars(String text) {

        // Pattern p = Pattern.compile("[^a-zA-Z\u0900-\u097F. ]");
        Pattern p = Pattern.compile("[^a-zA-Z. ]");

        boolean b = p.matcher(text).find();
        return b;

    }

    public static int validatePassword(String password,
                                       String rePassword) {

        if (password == null || password.length() == 0) {
            return INVALID_EMTRY_PASS;
        }

        if (password.length() < 6) {
            return INVALID_LENGTH_SIX;
        }

        if (rePassword == null || rePassword.length() == 0) {
            return INVALID_EMTRY_CONF_PASS;
        }

        if (!password.equals(rePassword)) {
            return INVALID_PASS_MISSMATCH;
        }

        return VALID;
    }

    /**
     * Author - Ashish Arun Zade It checks for text contains only numbers
     *
     * @param mobileNumber
     * @return
     */
    public static int validatePhoneNumber(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.length() == 0) {
            return INVALID_EMTRY;
        } else if (mobileNumber.length() < 10) {
            return INVALID_LENGTH;
        }
        Pattern p = Pattern.compile("[0987654321]");
        boolean b = p.matcher(mobileNumber).find();
        if (b) {
            return VALID;
        } else {
            return INVALID;
        }
    }



    public static int validateZipCode(String zipcode) {

        if (zipcode == null || zipcode.length() == 0) {
            return INVALID_EMTRY;
        } else if (zipcode.length() != 5) {
            return INVALID_LENGTH;
        }
        Pattern p = Pattern.compile("[0987654321]");
        boolean b = p.matcher(zipcode).find();
        if (b) {
            return VALID;
        } else {
            return INVALID;
        }
    }

    /**
     * Auther -Ashish Arun Zade Method will check if spinner is select any item
     * or not.
     *
     * @param spinner      - spinner is select any item or not.
     * @param defaultIndex - if defaultIndex is not 0, then spinner sets to default
     *                     position
     * @return - false - if spinner is at 0th position and defaultIndex = 0
     * otherwise returns true
     */
    public static boolean validateSpinner(Spinner spinner, int defaultIndex) {

        if (spinner.getSelectedItemPosition() == 0 && defaultIndex == 0) {
            return false;
        } else if (defaultIndex != 0 && spinner.getCount() > defaultIndex) {
            spinner.setSelection(defaultIndex);
        }
        return true;
    }


    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public static int validateEmail(String hex) {
        Pattern pattern;
        Matcher matcher;

        String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(hex);
        if (matcher.matches()) {
            return VALID;
        } else {
            return INVALID;
        }
    }


    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static int validateEditText(EditText editText) {

        if (editText == null) {
            return INVALID;
        }

        String text = editText.getText().toString();

        if (text.length() == 0) {
            return INVALID_EMTRY_PASS;
        }

        if (text.length() < 2) {
            return INVALID_LENGTH_SIX;
        }

        return VALID;
    }

}
