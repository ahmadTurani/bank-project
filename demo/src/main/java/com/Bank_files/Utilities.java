package com.Bank_files;

public class Utilities {
    public static double turn_double(String str){
        try {
            return Double.parseDouble(str);
        } 
        catch (NumberFormatException e) {
            return -1.0;
            }
        }
    public static MessageInfo<Account> passwordSecure(String password) {
        boolean hasUpper = false; boolean hasLower = false; boolean hasDigit = false; boolean hasSpecial = false;
        if (password.length() < 8) {
            return new MessageInfo<>(false, "Password must be at least 8 characters long!");
        }
        for (int i = 0; i<password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isWhitespace(c)) {
                return new MessageInfo<>(false, "Password cannot contain whitespace!");
            }
            if (Character.isLowerCase(c)) {
                hasLower = true;
            }
            else if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            else {
                hasSpecial = true;
            }
            }
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial || !hasLower) {
            return new MessageInfo<>(false, "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character!");
        }
        return new MessageInfo<>(true, "Password is secure!");
    }
}