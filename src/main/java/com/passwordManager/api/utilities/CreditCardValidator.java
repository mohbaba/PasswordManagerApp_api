package com.passwordManager.api.utilities;

import com.passwordManager.api.data.models.CreditCardType;
import com.passwordManager.api.exceptions.IncorrectCardDetailsException;

import java.util.*;

public class CreditCardValidator{
    private static final int[] cardNumList = new int[16];
    private static String cardNum;
    private static int cardDigitLength;

    public static boolean lengthChecker(){

        return cardNum.length() >= 13 && cardNum.length() <= 16;
    }

    public static CreditCardType cardTypeChecker(String cardNumber){

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

    public static void listConverter(){

        for(int i = 0; i < cardDigitLength ; i++){
            cardNumList[i] = Integer.parseInt(String.valueOf(cardNum.charAt(i)));
        }


    }

    public static int[] evenDoubleDigits(){
        int num1 = 0;
        int num2 = 0;
        int[] newArray = new int[cardDigitLength];
        for(int i = 0 ; i < newArray.length ; i+=2){

            newArray[i] = cardNumList[i] * 2;

        }
        for(int i = 0 ; i < newArray.length ; i+=2){
            if(newArray[i] >= 10){
                num1 = newArray[i] / 10;
                num2 = newArray[i] % 10;
                newArray[i] = num1 + num2;
            }

        }
        return newArray;
    }


    public static int[] oddDoubleDigits(){
        int[] newArray = new int[cardDigitLength];
        for(int i = 1 ; i < newArray.length ; i+=2){

            newArray[i] = cardNumList[i];

        }
        return newArray;
    }

    public static int addSingleDigit(){
        int total = 0;
        int[] newArray = evenDoubleDigits();
        for (int j : newArray) {

            total += j;

        }
        return total;
    }

    public static int sumTotal(){

        return addSingleDigit() + addOdd();
    }

    public static int addOdd(){
        int oddTotal = 0;
        int[] newArray = oddDoubleDigits();
        for (int j : newArray) {

            oddTotal += j;

        }
        return oddTotal;
    }

    public static boolean validityCheck(){


        return sumTotal() % 10 == 0;
    }

    public static void outputDisplay(String cardNum){


        System.out.println("***************************************");
        System.out.printf("**Credit Card Type: %s%n",cardTypeChecker(cardNum));
        System.out.printf("**Credit Card Number: %s%n", cardNum);
        System.out.printf("**Credit Card Digit Length: %s%n", cardDigitLength);
        System.out.printf("**Credit Card Validity Status: %s%n", validityCheck() ? "Valid": "Invalid");
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
//        outputDisplay(cardNum);
//
//
//    }



}
