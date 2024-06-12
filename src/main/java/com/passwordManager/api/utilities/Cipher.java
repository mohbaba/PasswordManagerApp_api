package com.passwordManager.api.utilities;

public class Cipher {
    public static final String ALPHABETS = "abcdefghijklmnopqrstuvwxyz";
    public static String encrypt(String message, int shiftKey) {
//        int shiftKey = 25;
        StringBuilder cipherText = new StringBuilder();
        for (int position = 0; position < message.length(); position++) {
            char character = message.charAt(position);
            if (Character.isLetter(character)){
                char replaceVal = encrypt(character,shiftKey);
                cipherText.append(replaceVal);
            }else {
                cipherText.append(character);
            }

        }
        return cipherText.toString();
    }

    private static char encrypt(char character, int shiftKey){
        char lowercaseCharacter = Character.toLowerCase(character);
        int position = ALPHABETS.indexOf(lowercaseCharacter);
        int shiftedPosition = (position + shiftKey) % ALPHABETS.length();
        return Character.isLowerCase(character) ? ALPHABETS.charAt(shiftedPosition) :
                Character.toUpperCase(ALPHABETS.charAt(shiftedPosition));
    }

    public static String decrypt(String cipherText, int shiftKey) {
//        int shiftKey = 25;
        StringBuilder decryptedMessage = new StringBuilder();
        for (int position = 0; position < cipherText.length(); position++) {
            char encryptedCharacter = cipherText.charAt(position);
            if (Character.isLetter(encryptedCharacter)){
                char replaceVal = decrypt(encryptedCharacter,shiftKey);
                decryptedMessage.append(replaceVal);
            }else {
                decryptedMessage.append(encryptedCharacter);
            }

        }
        return decryptedMessage.toString();
    }

    private static char decrypt(char encryptedCharacter, int shiftKey){
        char lowercaseCharacter = Character.toLowerCase(encryptedCharacter);
        int position = ALPHABETS.indexOf(lowercaseCharacter);
        int shiftedPosition = (position - shiftKey + ALPHABETS.length()) % ALPHABETS.length();
        return Character.isLowerCase(encryptedCharacter) ? ALPHABETS.charAt(shiftedPosition) :
                Character.toUpperCase(ALPHABETS.charAt(shiftedPosition));
    }


}
