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
    void encryptNumberTest(){
        String input = "23456789";
        String expected = "67890123";
        assertEquals(expected,NumericCipher.encrypt(Integer.parseInt(input)));
        System.out.println(NumericCipher.encrypt(Long.parseLong("12345678901")));
        ;
    }

    @Test
    void decryptNumberTest(){
        String input = "67890123";
        String expected = "23456789";
        assertEquals(expected,NumericCipher.decrypt(input));
        System.out.println(NumericCipher.decrypt("9733275053034847"));
    }

    @Test
    void decryptTest() {
        String input = "lngazaz";
        String expected = "mohbaba";
        assertEquals(expected,Cipher.decrypt(input));
    }
}