package io.github.arnabmaji19.networkcomplaints.util;

import java.util.regex.Pattern;

public class Validations {

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        /*
            Be between 8 and 40 characters long
            Contain at least one digit.
            Contain at least one lower case character.
            Contain at least one upper case character.
            Contain at least on special character from [ @ # $ % ! . ].
         */
        String passwordRegex = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }

    public static boolean isContactNumberValid(String contactNumber) {
        try {
            Long.parseLong(contactNumber); //check if it contains only integers
        } catch (NumberFormatException e) {
            return false;
        }
        if (contactNumber.length() == 12 && contactNumber.substring(0, 2).equals("91")) return true;
        if (contactNumber.length() == 11 && contactNumber.charAt(0) == '0') return true;

        return contactNumber.length() == 10;
    }
}
