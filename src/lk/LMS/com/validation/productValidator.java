
package lk.LMS.com.validation;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class productValidator {
   public static boolean isTextFieldValid(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    fieldName + " cannot be empty.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean isComboBoxValid(JComboBox<String> combo, String fieldName) {
        if (combo.getSelectedIndex() == 0) { 
            JOptionPane.showMessageDialog(null,
                    "Please select a valid " + fieldName + ".",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

      public static boolean isNonNegativeInt(String value, String fieldName) {
    try {
        double num = Integer.parseInt(value.trim());
        if (num < 0) {
            JOptionPane.showMessageDialog(null,
                    fieldName + " must be 0 or a positive number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null,
                fieldName + " must be a valid number.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }
}

    public static boolean isNonNegativeDouble(String value, String fieldName) {
    try {
        double num = Double.parseDouble(value.trim());
        if (num < 0) {
            JOptionPane.showMessageDialog(null,
                    fieldName + " must be 0 or a positive number.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null,
                fieldName + " must be a valid number.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
        return false;
    }
}
    
}
