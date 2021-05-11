package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlTest {

    @Test
    void checkPhoneNumberFormat() {
        assertEquals(false,Control.checkPhoneNumberFormat("1111111111"));//10
        assertEquals(false,Control.checkPhoneNumberFormat("1111111111a"));//not digit
        assertEquals(true,Control.checkPhoneNumberFormat("11111111111"));
    }

    @Test
    void checkPasswordFormat() {
        assertEquals(false,Control.checkPasswordFormat("123"));//less than 6
        assertEquals(false,Control.checkPasswordFormat("123456789123456789123"));//more than 20
        assertEquals(false,Control.checkPasswordFormat("123456#"));//contain special symbol
        assertEquals(false,Control.checkPasswordFormat("123 456"));//space
        assertEquals(true,Control.checkPasswordFormat("123456"));//6 bits
        assertEquals(true,Control.checkPasswordFormat("11111111111111111111"));//20 bits
        assertEquals(true,Control.checkPasswordFormat("a123456A"));//normal

    }
}