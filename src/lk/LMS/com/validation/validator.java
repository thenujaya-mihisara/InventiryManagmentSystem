/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lk.LMS.com.validation;

public enum validator {

    EMAIL("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"),
    MOBILE("^(0{1})(7{1})([0|1|2|4|5|6|7|8]{1})([0-9]{7})"),
    PASSWORD("^[\\s\\S]{5,7}$");

    private final String pattern;

    validator(String pattern) {
        this.pattern = pattern;
    }

    public String validate() {
        return pattern;
    }
}

