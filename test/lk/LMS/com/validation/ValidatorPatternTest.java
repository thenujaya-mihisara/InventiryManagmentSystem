package lk.LMS.com.validation;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Unit tests for the validator enum regex patterns.
 * Tests both valid (assertTrue) and invalid (assertFalse) inputs.
 */
public class ValidatorPatternTest {

    // ─── EMAIL TESTS ─────────────────────────────────────────────────────────

    @Test
    public void emailPatternAcceptsValidEmail() {
        assertTrue("manager@shop.lk".matches(validator.EMAIL.validate()));
    }

    @Test
    public void emailPatternAcceptsCommonDomains() {
        assertTrue("user@gmail.com".matches(validator.EMAIL.validate()));
        assertTrue("admin@company.org".matches(validator.EMAIL.validate()));
    }

    @Test
    public void emailPatternRejectsMissingAtSymbol() {
        assertFalse("manager-shop.lk".matches(validator.EMAIL.validate()));
    }

    @Test
    public void emailPatternRejectsMissingDomain() {
        assertFalse("manager@".matches(validator.EMAIL.validate()));
    }

    // ─── MOBILE TESTS ────────────────────────────────────────────────────────

    @Test
    public void mobilePatternAcceptsSriLankanMobileNumber() {
        assertTrue("0712345678".matches(validator.MOBILE.validate()));
    }

    @Test
    public void mobilePatternAcceptsAllValidPrefixes() {
        assertTrue("0771234567".matches(validator.MOBILE.validate()));
        assertTrue("0781234567".matches(validator.MOBILE.validate()));
    }

    @Test
    public void mobilePatternRejectsInvalidPrefix() {
        // 08x numbers are not valid Sri Lankan mobile numbers
        assertFalse("0812345678".matches(validator.MOBILE.validate()));
    }

    @Test
    public void mobilePatternRejectsLandlineNumber() {
        assertFalse("0112345678".matches(validator.MOBILE.validate()));
    }

    // ─── PASSWORD TESTS ──────────────────────────────────────────────────────

    @Test
    public void passwordPatternAcceptsFiveCharacters() {
        assertTrue("abc12".matches(validator.PASSWORD.validate()));
    }

    @Test
    public void passwordPatternAcceptsSevenCharacters() {
        assertTrue("abc1234".matches(validator.PASSWORD.validate()));
    }

    @Test
    public void passwordPatternRejectsTooShortPassword() {
        assertFalse("abcd".matches(validator.PASSWORD.validate()));
    }

    @Test
    public void passwordPatternRejectsTooLongPassword() {
        assertFalse("abc12345".matches(validator.PASSWORD.validate()));
    }
}
