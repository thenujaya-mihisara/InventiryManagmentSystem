/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lk.LMS.com.validation;

import javax.swing.JOptionPane;

public class UserValidate {
    public static boolean isEmailValid(String value) {
        if (value.isBlank()) {
            JOptionPane.showMessageDialog(null,
                    "Email input can't be empty",
                    "Email Validation",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!value.matches(validator.EMAIL.validate())) {
            JOptionPane.showMessageDialog(null,
                    "Enter valid Email Address",
                    "Email Validation",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean isMobileValid(String value) {
        if (value.isBlank()) {
            JOptionPane.showMessageDialog(null,
                    "Mobile field can't be empty",
                    "Mobile Validation",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!value.matches(validator.MOBILE.validate())) {
            JOptionPane.showMessageDialog(null,
                    "Enter a valid Mobile Number",
                    "Mobile Validation",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(String value) {
        if (value.isBlank()) {
            JOptionPane.showMessageDialog(null,
                    "Password field can't be empty",
                    "Password Validation",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!value.matches(validator.PASSWORD.validate())) {
            JOptionPane.showMessageDialog(null,
                    "Password must include the following characters. "
                    + "The password must be greater than 4 and less than 8 characters",
                    "Password Validation",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean isInputFieldValid(String value) {
        if (value.isBlank()) {
            JOptionPane.showMessageDialog(null,
                    "Input field can't be empty",
                    "Validation Message",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean isSelectedItemValid(int value) {
        if (value == 0) {
            JOptionPane.showMessageDialog(null,
                    "Please select a valid option",
                    "Validation Message",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
