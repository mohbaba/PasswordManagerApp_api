package com.passwordManager.api.services;

import com.passwordManager.api.data.models.User;
import com.passwordManager.api.data.repositories.UserRepository;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServicesTest {
    @Autowired
    UserServices userServices;
    @Autowired
    UserRepository userRepository;

    private void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("feyi");
        loginRequest.setPassword("pass");
    }


    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("feyi");
        registerUserRequest.setPassword("pass");
        userServices.register(registerUserRequest);
        assertEquals(1L,userServices.countUsers());
    }

    @AfterEach
    public void teardown(){
        userRepository.deleteAll();
    }

    @Test
    public void registerUser_UserIsRegisteredTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("pass");
        userServices.register(registerUserRequest);
        assertEquals(2L,userServices.countUsers());
    }

    @Test
    public void userLogsIn_UserIsLoggedInTest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
        assertTrue(userServices.isLoggedIn("feyi"));
    }

    @Test
    public void userLogsInWithCaseSensitiveData_ThrowsExceptionTest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
        assertTrue(userServices.isLoggedIn("feyi"));
    }

    @Test
    public void userRegistersWithCaseSensitiveDataEqualsAnotherNameInRepo_ThrowsExceptionTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Feyi");
        registerUserRequest.setPassword("pass");
        assertThrows(UsernameExistsException.class,()->userServices.register(registerUserRequest));
    }

    @Test
    public void registerUserWithEmptyString_throwsExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class,()->userServices.register(request));
    }

    @Test
    public void registerUserWithSpace_throwsIllegalArgumentExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("  ");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class,()->userServices.register(request));
    }

    @Test
    public void registerUserWithNameSeparatedBySpace_throwsIllegalArgumentExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("User Name");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class,()->userServices.register(request));
    }

    @Test
    public void saveLoginCredentialTypeForUser_LoginDetailIsSaved(){
        login();
        LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
        loginInfoRequest.setUsernameToBeSaved("moh");
        loginInfoRequest.setUsername("feyi");
        loginInfoRequest.setPasswordToBeSaved("moh");
        userServices.addLoginInfo(loginInfoRequest);
        assertEquals(1L,userServices.countLoginInfoFor("feyi"));
    }

    @Test
    public void deleteLoginInfoForUser_LoginInfoISDeleted(){
        login();
        LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
        loginInfoRequest.setUsernameToBeSaved("moh");
        loginInfoRequest.setUsername("feyi");
        loginInfoRequest.setPasswordToBeSaved("moh");
        userServices.addLoginInfo(loginInfoRequest);

        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        User user = userRepository.findByUsername("feyi");
        String id = user.getLoginDetails().getFirst().getId();
        deleteLoginInfoRequest.setLoginInfoId(id);
        deleteLoginInfoRequest.setUsername("feyi");
        userServices.deleteLoginInfo(deleteLoginInfoRequest);
        assertEquals(0,userServices.countLoginInfoFor("feyi"));

    }

    @Test
    public void editLoginInfo_LoginInfoISEdited(){
        login();
        LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
        loginInfoRequest.setUsernameToBeSaved("moh");
        loginInfoRequest.setUsername("feyi");
        loginInfoRequest.setPasswordToBeSaved("moh");
        userServices.addLoginInfo(loginInfoRequest);
        assertEquals(1,userServices.countLoginInfoFor("feyi"));

        User user = userRepository.findByUsername("feyi");
        assertEquals("moh", user.getLoginDetails().getFirst().getSavedUsername());
        String id = user.getLoginDetails().getFirst().getId();
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setNewPassword("babs");
        editLoginInfoRequest.setPostId(id);
        editLoginInfoRequest.setUsername("feyi");
        editLoginInfoRequest.setNewUsername("baba");
        userServices.editLoginInfo(editLoginInfoRequest);

        user = userRepository.findByUsername("feyi");
        assertEquals(1,userServices.countLoginInfoFor("feyi"));
        assertEquals("baba",user.getLoginDetails().getFirst().getSavedUsername());
        assertEquals("babs",user.getLoginDetails().getFirst().getSavedPassword());
    }

    @Test
    public void addIdentityInfo_IdentityInfoIsAdded(){
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("12345678901");
        userServices.addIdentityInfo(identityInfoRequest);
        assertEquals(1L, userServices.countIdentityInfoFor("feyi"));

    }

    @Test
    public void addIdentityInfoWithIncorrectNiN_ThrowsExceptionTest(){
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("12341");
        assertThrows(InvalidNiNException.class,()->userServices.addIdentityInfo(identityInfoRequest));
    }

    @Test
    public void deleteIdentityInfo_IdentityInfoIsDeleted(){
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("12345678901");
        userServices.addIdentityInfo(identityInfoRequest);
        assertEquals(1,userServices.countIdentityInfoFor("feyi"));

        User user = userRepository.findByUsername("feyi");
        String id = user.getIdentities().getFirst().getId();
        DeleteIdentityInfoRequest deleteIdentityInfoRequest = new DeleteIdentityInfoRequest();
        deleteIdentityInfoRequest.setIdentityInfoId(id);
        deleteIdentityInfoRequest.setUsername("feyi");
        userServices.deleteIdentityInfo(deleteIdentityInfoRequest);
        assertEquals(0,userServices.countIdentityInfoFor("feyi"));

    }

    @Test
    public void editIdentityInfo_IdentityInfoIsEditedTest(){
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("12345678901");
        identityInfoRequest.setAddress("Address");
        userServices.addIdentityInfo(identityInfoRequest);
        assertEquals(1,userServices.countIdentityInfoFor("feyi"));
        User user = userRepository.findByUsername("feyi");

        EditIdentityInfoRequest editIdentityInfoRequest = new EditIdentityInfoRequest();
        editIdentityInfoRequest.setIdentityInfoId(user.getIdentities().getFirst().getId());
        editIdentityInfoRequest.setUsername("feyi");
        editIdentityInfoRequest.setNationalIdentityNumber("11211111111");
        editIdentityInfoRequest.setFirstName("abike");
        userServices.editIdentityInfo(editIdentityInfoRequest);
        assertEquals(1,userServices.countIdentityInfoFor("feyi"));
        assertEquals("abike",userRepository.findByUsername("feyi").getIdentities().getFirst().getFirstName());
        assertEquals("11211111111",userRepository.findByUsername("feyi").getIdentities().getFirst().getNationalIdentityNumber());



    }

    @Test
    public void userLogsInAddWrongIdentityInfo_ThrowsExceptionTest(){
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("1234y678901");
        identityInfoRequest.setAddress("Address");
        assertThrows(IncorrectNINnumberException.class,()->userServices.addIdentityInfo(identityInfoRequest));
    }

    @Test
    public void userAddsCreditCardInfo_CreditCardInfoIsAdded(){
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setCardNumber("1234567890123");
        creditCardInfoRequest.setCardNumber("5399831619690403");
        creditCardInfoRequest.setCvv("422");
        creditCardInfoRequest.setCardholderName("Muhammad Baba Muhammad");
        creditCardInfoRequest.setExpirationMonth(12);
        creditCardInfoRequest.setExpirationYear(2024);
        creditCardInfoRequest.setUsername("feyi");
        userServices.addCreditCardInfo(creditCardInfoRequest);
        assertEquals(1,userServices.countCreditCardInfoFor("feyi"));
    }

    @Test
    public void userAddsCreditCardInfoWithIncorrectCardNumber_ThrowsExceptionTest(){
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setCardNumber("1234567890123");
        creditCardInfoRequest.setCvv("422");
        creditCardInfoRequest.setCardholderName("Muhammad Baba Muhammad");
        creditCardInfoRequest.setExpirationMonth(12);
        creditCardInfoRequest.setExpirationYear(2024);
        creditCardInfoRequest.setUsername("feyi");
        assertThrows(InvalidCreditCardException.class,()->userServices.addCreditCardInfo(creditCardInfoRequest));
    }

    @Test
    public void userAddsCreditCardInfoWithInvalidLength_ThrowsExceptionTest(){
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setCardNumber("12345678901");
        creditCardInfoRequest.setCvv("422");
        creditCardInfoRequest.setCardholderName("Muhammad Baba Muhammad");
        creditCardInfoRequest.setExpirationMonth(12);
        creditCardInfoRequest.setExpirationYear(2024);
        creditCardInfoRequest.setUsername("feyi");
        assertThrows(IncorrectCardDetailsException.class,()->userServices.addCreditCardInfo(creditCardInfoRequest));
    }

    @Test
    public void userAddsCreditCardInfoWithIncorrectCVV_ThrowsExceptionTest(){
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setCardNumber("5399831619690403");
        creditCardInfoRequest.setCvv("4223");
        creditCardInfoRequest.setCardholderName("Muhammad Baba Muhammad");
        creditCardInfoRequest.setExpirationMonth(12);
        creditCardInfoRequest.setExpirationYear(2024);
        creditCardInfoRequest.setUsername("feyi");
        assertThrows(IncorrectCardDetailsException.class,()->userServices.addCreditCardInfo(creditCardInfoRequest));
    }


    @Test
    public void userAddsCreditCardInfoWithInvalidCardNumber_ThrowsExceptionTest(){
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setCardNumber("1234567890123");
        creditCardInfoRequest.setCvv("422");
        creditCardInfoRequest.setCardholderName("Muhammad Baba Muhammad");
        creditCardInfoRequest.setExpirationMonth(12);
        creditCardInfoRequest.setExpirationYear(2024);
        creditCardInfoRequest.setUsername("feyi");
        assertThrows(InvalidCreditCardException.class,()->userServices.addCreditCardInfo(creditCardInfoRequest));
    }

    @Test
    public void userAddsCreditCardInfoWithValidCardTypeShorterCardLength_ThrowsExceptionTest(){
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setCardNumber("53998316196");
        creditCardInfoRequest.setCvv("422");
        creditCardInfoRequest.setCardholderName("Muhammad Baba Muhammad");
        creditCardInfoRequest.setExpirationMonth(12);
        creditCardInfoRequest.setExpirationYear(2024);
        creditCardInfoRequest.setUsername("feyi");
        assertThrows(IncorrectCardDetailsException.class,()->userServices.addCreditCardInfo(creditCardInfoRequest));
    }

    @Test
    public void userDeletesCreditCardInfo_CreditCardInfoISDeleted(){
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setCardNumber("5399831619690403");
        creditCardInfoRequest.setCvv("422");
        creditCardInfoRequest.setCardholderName("Muhammad Baba Muhammad");
        creditCardInfoRequest.setExpirationMonth(12);
        creditCardInfoRequest.setExpirationYear(2024);
        creditCardInfoRequest.setUsername("feyi");
        userServices.addCreditCardInfo(creditCardInfoRequest);

        User user = userRepository.findByUsername("feyi");
        String id = user.getCreditCardDetails().getFirst().getId();

        DeleteCardInfoRequest deleteCardInfoRequest = new DeleteCardInfoRequest();
        deleteCardInfoRequest.setCardId(id);
        deleteCardInfoRequest.setUsername("feyi");
        userServices.deleteCreditCardInfo(deleteCardInfoRequest);
        assertEquals(0,userServices.countCreditCardInfoFor("feyi"));

    }

}