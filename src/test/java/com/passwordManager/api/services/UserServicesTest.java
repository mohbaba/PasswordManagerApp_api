package com.passwordManager.api.services;

import com.passwordManager.api.data.models.User;
import com.passwordManager.api.data.repositories.UserRepository;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.DeleteCardInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.IdentityInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;
import com.passwordManager.api.exceptions.*;
import com.passwordManager.api.utilities.Cipher;
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

    private void login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
    }

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("feyi");
        registerUserRequest.setPassword("pass");
        userServices.register(registerUserRequest);
        assertEquals(1L, userServices.countUsers());
    }

    @AfterEach
    public void teardown() {
        userRepository.deleteAll();
    }

    @Test
    public void registerUser_UserIsRegisteredTest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("username");
        registerUserRequest.setPassword("pass");
        userServices.register(registerUserRequest);
        assertEquals(2L, userServices.countUsers());
    }

    @Test
    public void userLogsIn_UserIsLoggedInTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
        assertTrue(userServices.isLoggedIn("feyi"));
    }

    @Test
    public void userLogsInWithCaseSensitiveData_ThrowsExceptionTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
        assertTrue(userServices.isLoggedIn("feyi"));
    }

    @Test
    public void userRegistersWithCaseSensitiveDataEqualsAnotherNameInRepo_ThrowsExceptionTest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Feyi");
        registerUserRequest.setPassword("pass");
        assertThrows(UsernameExistsException.class, () -> userServices.register(registerUserRequest));
    }

    @Test
    public void registerUserWithEmptyString_throwsExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class, () -> userServices.register(request));
    }

    @Test
    public void registerUserWithSpace_throwsIllegalArgumentExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("  ");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class, () -> userServices.register(request));
    }

    @Test
    public void registerUserWithNameSeparatedBySpace_throwsIllegalArgumentExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("User Name");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class, () -> userServices.register(request));
    }

    @Test
    public void saveLoginCredentialTypeForUser_LoginDetailIsSaved() {
        login();
        LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
        loginInfoRequest.setUsernameToBeSaved("moh");
        loginInfoRequest.setUsername("feyi");
        loginInfoRequest.setPasswordToBeSaved("moh");
        userServices.addLoginInfo(loginInfoRequest);
        assertEquals(1L, userServices.countLoginInfoFor("feyi"));
    }

    @Test
    public void deleteLoginInfoForUser_LoginInfoISDeleted() {
        login();
        LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
        loginInfoRequest.setUsernameToBeSaved("moh");
        loginInfoRequest.setUsername("feyi");
        loginInfoRequest.setPasswordToBeSaved("moh");
        userServices.addLoginInfo(loginInfoRequest);

        DeleteLoginInfoRequest deleteLoginInfoRequest = new DeleteLoginInfoRequest();
        User user = userRepository.findByUsername("feyi");
        String id = user.getLoginInfoDetails().get(0).getId(); // Changed getFirst() to get(0)
        deleteLoginInfoRequest.setLoginInfoId(id);
        deleteLoginInfoRequest.setUsername("feyi");
        deleteLoginInfoRequest.setPassword("pass");
        userServices.deleteLoginInfo(deleteLoginInfoRequest);
        assertEquals(0, userServices.countLoginInfoFor("feyi"));
    }

    @Test
    public void editLoginInfo_LoginInfoISEdited() {
        login();
        LoginInfoRequest loginInfoRequest = new LoginInfoRequest();
        loginInfoRequest.setUsernameToBeSaved("moh");
        loginInfoRequest.setUsername("feyi");
        loginInfoRequest.setPasswordToBeSaved("moh");
        userServices.addLoginInfo(loginInfoRequest);
        assertEquals(1, userServices.countLoginInfoFor("feyi"));

        User user = userRepository.findByUsername("feyi");
        assertEquals("moh", user.getLoginInfoDetails().get(0).getSavedUsername()); // Changed getFirst() to get(0)
        String id = user.getLoginInfoDetails().get(0).getId(); // Changed getFirst() to get(0)
        EditLoginInfoRequest editLoginInfoRequest = new EditLoginInfoRequest();
        editLoginInfoRequest.setNewPassword("babs");
        editLoginInfoRequest.setPostId(id);
        editLoginInfoRequest.setUsername("feyi");
        editLoginInfoRequest.setNewUsername("baba");
        userServices.editLoginInfo(editLoginInfoRequest);

        user = userRepository.findByUsername("feyi");
        assertEquals(1, userServices.countLoginInfoFor("feyi"));
        assertEquals("baba", user.getLoginInfoDetails().get(0).getSavedUsername()); // Changed getFirst() to get(0)
        // assertEquals("babs", Cipher.decrypt(user.getLoginInfoDetails().get(0).getSavedPassword())); // Uncomment if needed
    }

    @Test
    public void addIdentityInfo_IdentityInfoIsAdded() {
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
    public void addIdentityInfoWithIncorrectNiN_ThrowsExceptionTest() {
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("12341");
        assertThrows(InvalidNiNException.class, () -> userServices.addIdentityInfo(identityInfoRequest));
    }

    @Test
    public void deleteIdentityInfo_IdentityInfoIsDeleted() {
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("12345678901");
        userServices.addIdentityInfo(identityInfoRequest);
        assertEquals(1, userServices.countIdentityInfoFor("feyi"));

        User user = userRepository.findByUsername("feyi");
        String id = user.getIdentities().get(0).getId(); // Changed getFirst() to get(0)
        DeleteIdentityInfoRequest deleteIdentityInfoRequest = new DeleteIdentityInfoRequest();
        deleteIdentityInfoRequest.setIdentityInfoId(id);
        deleteIdentityInfoRequest.setUsername("feyi");
        userServices.deleteIdentityInfo(deleteIdentityInfoRequest);
        assertEquals(0, userServices.countIdentityInfoFor("feyi"));
    }

    @Test
    public void editIdentityInfo_IdentityInfoIsEdited() {
        login();
        IdentityInfoRequest identityInfoRequest = new IdentityInfoRequest();
        identityInfoRequest.setUsername("feyi");
        identityInfoRequest.setFirstName("Mohammad");
        identityInfoRequest.setLastName("Baba");
        identityInfoRequest.setMiddleName("Muhammad");
        identityInfoRequest.setNationalIdentityNumber("12345678901");
        userServices.addIdentityInfo(identityInfoRequest);
        assertEquals(1, userServices.countIdentityInfoFor("feyi"));

        User user = userRepository.findByUsername("feyi");
        assertEquals("Mohammad", user.getIdentities().get(0).getFirstName()); // Changed getFirst() to get(0)
        String id = user.getIdentities().get(0).getId(); // Changed getFirst() to get(0)
        EditIdentityInfoRequest editIdentityInfoRequest = new EditIdentityInfoRequest();
        editIdentityInfoRequest.setIdentityInfoId(id);
        editIdentityInfoRequest.setUsername("feyi");
        editIdentityInfoRequest.setFirstName("Bashir");
        userServices.editIdentityInfo(editIdentityInfoRequest);

        user = userRepository.findByUsername("feyi");
        assertEquals(1, userServices.countIdentityInfoFor("feyi"));
        assertEquals("Bashir", user.getIdentities().get(0).getFirstName()); // Changed getFirst() to get(0)
    }

    @Test
    public void addCreditCardInfo_CreditCardInfoIsAdded() {
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setUsername("feyi");
        creditCardInfoRequest.setCardNumber("1234567812345678");
        creditCardInfoRequest.setExpirationMonth(10);
        creditCardInfoRequest.setExpirationYear(25);
        creditCardInfoRequest.setCvv("123");
        userServices.addCreditCardInfo(creditCardInfoRequest);
        assertEquals(1L, userServices.countCreditCardInfoFor("feyi"));
    }

    @Test
    public void deleteCreditCardInfo_CreditCardInfoIsDeleted() {
        login();
        CreditCardInfoRequest creditCardInfoRequest = new CreditCardInfoRequest();
        creditCardInfoRequest.setUsername("feyi");
        creditCardInfoRequest.setCardNumber("1234567812345678");
        creditCardInfoRequest.setExpirationMonth(10);
        creditCardInfoRequest.setExpirationYear(25);
        creditCardInfoRequest.setCvv("123");
        userServices.addCreditCardInfo(creditCardInfoRequest);
        assertEquals(1, userServices.countCreditCardInfoFor("feyi"));

        User user = userRepository.findByUsername("feyi");
        String id = user.getCreditCardDetails().get(0).getId(); // Changed getFirst() to get(0)
        DeleteCardInfoRequest deleteCardInfoRequest = new DeleteCardInfoRequest();
        deleteCardInfoRequest.setCardId(id);
        deleteCardInfoRequest.setUsername("feyi");
        userServices.deleteCreditCardInfo(deleteCardInfoRequest);
        assertEquals(0, userServices.countCreditCardInfoFor("feyi"));
    }
}
