package lk.LMS.com.validation;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Unit tests for UserValidate class.
 *
 * NOTE: UserValidate methods call JOptionPane.showMessageDialog() internally
 * when validation fails. In a headless test environment this may cause issues.
 * Therefore, we test the underlying regex patterns directly from the validator
 * enum for negative (invalid) cases, and call UserValidate for positive cases
 * where no dialog is shown.
 */
public class UserValidateTest {

    // ─── isEmailValid ─────────────────────────────────────────────────────────

    @Test
    public void validEmailReturnsTrue() {
        // Valid email — no dialog shown, returns true
        assertTrue(UserValidate.isEmailValid("admin@example.com"));
    }

    @Test
    public void validEmailWithDotReturnsTrue() {
        assertTrue(UserValidate.isEmailValid("user.name@domain.lk"));
    }

    @Test
    public void invalidEmailPatternReturnsFalse() {
        // Test pattern directly (avoids JOptionPane popup in headless env)
        assertFalse("invalidemail".matches(validator.EMAIL.validate()));
    }

    @Test
    public void emailMissingAtReturnsFalse() {
        assertFalse("userdomain.com".matches(validator.EMAIL.validate()));
    }

    // ─── isMobileValid ────────────────────────────────────────────────────────

    @Test
    public void validMobileReturnsTrue() {
        assertTrue(UserValidate.isMobileValid("0771234567"));
    }

    @Test
    public void validMobileWith072PrefixReturnsTrue() {
        assertTrue(UserValidate.isMobileValid("0721234567"));
    }

    @Test
    public void invalidMobilePatternReturnsFalse() {
        // Test pattern directly
        assertFalse("0812345678".matches(validator.MOBILE.validate()));
    }

    @Test
    public void mobileWithLettersReturnsFalse() {
        assertFalse("07ABCDEFGH".matches(validator.MOBILE.validate()));
    }

    // ─── isPasswordValid ──────────────────────────────────────────────────────

    @Test
    public void validPasswordReturnsTrue() {
        assertTrue(UserValidate.isPasswordValid("abc12"));
    }

    @Test
    public void validSevenCharPasswordReturnsTrue() {
        assertTrue(UserValidate.isPasswordValid("abc1234"));
    }

    @Test
    public void tooShortPasswordReturnsFalse() {
        // Test pattern directly
        assertFalse("ab1".matches(validator.PASSWORD.validate()));
    }

    @Test
    public void tooLongPasswordReturnsFalse() {
        assertFalse("abcdefgh1".matches(validator.PASSWORD.validate()));
    }

    // ─── isSelectedItemValid ──────────────────────────────────────────────────

    @Test
    public void selectedItemAboveZeroReturnsTrue() {
        assertTrue(UserValidate.isSelectedItemValid(1));
    }

    @Test
    public void selectedItemZeroReturnsFalse() {
        // index 0 = "Select..." placeholder — should be invalid
        assertFalse(UserValidate.isSelectedItemValid(0));
    }

    @Test
    public void selectedItemHighValueReturnsTrue() {
        assertTrue(UserValidate.isSelectedItemValid(5));
    }
}
