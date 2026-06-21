/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lk.LMS.com.validation;


public enum validator {
    EMAIL() {
        @Override
        public String validate() {
            return "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        }
    },
    MOBILE() {
        @Override
        public String validate() {
            return "^(0{1})(7{1})([0|1|2|4|5|6|7|8]{1})([0-9]{7})";
        }
    },
    PASSWORD() {
        @Override
        public String validate() {
            return "^[\\s\\S]{5,7}$";
        }
    };

    public String validate() {
        return "";
    }
}
