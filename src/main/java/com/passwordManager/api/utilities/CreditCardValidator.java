package com.passwordManager.api.utilities;

import com.passwordManager.api.data.models.CreditCardType;
import com.passwordManager.api.exceptions.IncorrectCardDetailsException;

public class CreditCardValidator{
    private static final int[] cardNumList = new int[16];

    public static boolean lengthChecker(String cardNum){

        return cardNum.length() >= 13 && cardNum.length() <= 16;
    }

    public static CreditCardType getCardType(String cardNumber){

        CreditCardType creditCardType = null;
        switch (cardNumber.charAt(0)){
            case '3' -> {
                if (cardNumber.charAt(1) == 7)creditCardType = CreditCardType.AMERICAN_EXPRESS;
            }
            case '4' -> {
                creditCardType = CreditCardType.VISA_CARD;
            }
            case '5' -> {
                creditCardType = CreditCardType.MASTERCARD;
            }
            case '6' -> {
                creditCardType = CreditCardType.DISCOVER;
            }default -> throw new IncorrectCardDetailsException("This card is not valid");
        }
        return creditCardType;
    }

    public static int[] listConverter(String cardNum){

        for(int i = 0; i < cardNum.length() ; i++){
            cardNumList[i] = Integer.parseInt(String.valueOf(cardNum.charAt(i)));
        }

        return cardNumList;
    }

    public static int[] evenDoubleDigits(String cardNumber){
        int[] newArray = new int[cardNumber.length()];
        for(int i = 0 ; i < newArray.length ; i+=2){

            newArray[i] = cardNumList[i] * 2;

        }
        for(int i = 0 ; i < newArray.length ; i+=2){
            if(newArray[i] >= 10){
               int num1 = newArray[i] / 10;
               int num2 = newArray[i] % 10;
                newArray[i] = num1 + num2;
            }

        }
        return newArray;
    }


    public static int[] oddDoubleDigits(String cardNum){
        int[] newArray = new int[cardNum.length()];
        for(int i = 1 ; i < newArray.length ; i+=2){

            newArray[i] = cardNumList[i];

        }
        return newArray;
    }

    public static int addSingleDigit(String cardNum){
        int total = 0;
        int[] newArray = evenDoubleDigits(cardNum);
        for (int j : newArray) {

            total += j;

        }
        return total;
    }

    public static int sumTotal(String cardNum){

        return addSingleDigit(cardNum) + addOdd(cardNum);
    }

    public static int addOdd(String cardNum){
        int oddTotal = 0;
        int[] newArray = oddDoubleDigits(cardNum);
        for (int j : newArray) {

            oddTotal += j;

        }
        return oddTotal;
    }

    public static boolean validityCheck(String cardNum){
        listConverter(cardNum);

        return sumTotal(cardNum) % 10 == 0;
    }

    public static boolean isValidCvv(String cardNumber){
        for (int index = 0; index < cardNumber.length(); index++) {
            if (Character.isLetter(cardNumber.charAt(index)))return false;
        }
        return true;
    }

    public static void outputDisplay(String cardNum){


        System.out.println("***************************************");
        System.out.printf("**Credit Card Type: %s%n", getCardType(cardNum));
        System.out.printf("**Credit Card Number: %s%n", cardNum);
        System.out.printf("**Credit Card Digit Length: %s%n", cardNum.length());
        System.out.printf("**Credit Card Validity Status: %s%n", validityCheck(cardNum) ? "Valid":
                "Invalid");
        System.out.println("***************************************");

    }

//    public static void main(String[]args){
//
//        System.out.println("Hello, Kindly Enter Card details to verify");
//        cardNum = scanner.nextLine();
//        cardDigitLength = cardNum.length();
//
//        while(cardDigitLength < 13 || cardDigitLength > 16){
//            System.out.println("Hello, Please Kindly Enter Correct Card details to verify");
//            cardNum = scanner.nextLine();
//            cardDigitLength = cardNum.length();
//            System.out.println("");
//        }
//
//        System.out.println("");
//        System.out.println("");
//        listConverter();
//        evenDoubleDigits();
//        oddDoubleDigits();
//        addOdd();
//        addSingleDigit();
//        sumTotal();
//        validityCheck();
//
//
//
//    }



}
