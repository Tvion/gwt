package com.netcracker.shared;

public class FieldVerifier {


    public static boolean isValidName(String name) {
        if (name == null || name.contains("<")) {
            return false;
        }
        return name.length() > 0 && name.length() < 20;
    }

    public static boolean isValidNumber(String number) {
        if (number.isEmpty()) return false;
        try {
            if (Integer.parseInt(number) > 0) {
                return true;
            }
        } catch (RuntimeException e) {
            return false;
        }
        return false;
    }


}
