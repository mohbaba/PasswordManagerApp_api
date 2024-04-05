package com.passwordManager.api.utilities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CipherTest {

    @Test
    void encryptTest() {
        String input = "mohbaba";
        String expected = "lngazaz";
        assertEquals(expected,Cipher.encrypt(input));
    }

    @Test
    void decryptTest() {
        String input = "lngazaz";
        String expected = "mohbaba";
        assertEquals(expected,Cipher.decrypt(input));
    }
}