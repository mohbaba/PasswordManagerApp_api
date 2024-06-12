package com.passwordManager.api.services;

import com.passwordManager.api.data.models.*;
import com.passwordManager.api.data.repositories.UserRepository;
import com.passwordManager.api.dtos.UserData;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.CreditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.DeleteCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.EditCardInfoRequest;
import com.passwordManager.api.dtos.requests.creditCardInfoRequests.GetCardInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.DeleteIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.EditIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.GetIdentityInfoRequest;
import com.passwordManager.api.dtos.requests.identityInfoRequests.IdentityInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.DeleteLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.EditLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.GetLoginInfoRequest;
import com.passwordManager.api.dtos.requests.loginInfoRequests.LoginInfoRequest;
import com.passwordManager.api.dtos.responses.*;
import com.passwordManager.api.dtos.responses.creditCardResponses.CreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.creditCardResponses.DeleteCreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.creditCardResponses.GetCreditCardInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.DeleteIdentityInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.GetIdentityInfoResponse;
import com.passwordManager.api.dtos.responses.identityInfoResponses.IdentityInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.DeleteLoginInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.GetLoginInfoResponse;
import com.passwordManager.api.dtos.responses.loginInfoResponses.LoginInfoResponse;
import com.passwordManager.api.exceptions.*;

import org.cipher.Cipher;
import org.cipher.NumericCipher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.passwordManager.api.utilities.Mapper.*;
@Service
public class UserServicesImpl implements UserServices{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoginInfoServices loginInfoServices;
    @Autowired
    private IdentityInfoServices identityInfoServices;
    @Autowired
    private CreditCardInfoServices creditCardInfoServices;


    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        validateUsername(registerUserRequest.getUsername());

        User user = map(registerUserRequest);
        userRepository.save(user);
        return map(userRepository.save(user));
    }

    private void checkUserLoggedIn(String username){
        if (!isLoggedIn(username))throw new LoginRequiredException("User must be logged in");
    }

    @Override
    public long countUsers() {

        return userRepository.count();
    }

    @Override
    public LoginUserResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        validateLogin(loginRequest, user);
        user.setLoggedIn(true);
        return mapTo(userRepository.save(user));
    }

    @Override
    public LogoutUserResponse logout(LogoutUserRequest logoutUserRequest){
        checkUserLoggedIn(logoutUserRequest.getUsername());
        User user = userRepository.findByUsername(logoutUserRequest.getUsername());
        checkUserExists(user);
        user.setLoggedIn(false);
        return mapFrom(userRepository.save(user));
    }

    @Override
    public boolean isLoggedIn(String username) {
        User user = userRepository.findByUsername(username);
        checkUserExists(user);
        return user.isLoggedIn();
    }



    @Override
    public LoginInfoResponse addLoginInfo(LoginInfoRequest loginInfoRequest) throws InvalidURLException {
        checkUserLoggedIn(loginInfoRequest.getUsername());
        User user = userRepository.findByUsername(loginInfoRequest.getUsername());
        checkUserExists(user);
        UserData userData = getUserDataFrom(user);
        LoginInfo loginInfo = loginInfoServices.addLogin(loginInfoRequest, userData);
        addLoginDetailsTo(user, loginInfo);
        return map(loginInfo);
    }

    private static UserData getUserDataFrom(User user) {
        UserData userData = new UserData();
        userData.setUserData(user);
        return userData;
    }

    @Override
    public DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest) {
        checkUserLoggedIn(deleteLoginInfoRequest.getUsername());
        User user = userRepository.findByUsername(deleteLoginInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user, deleteLoginInfoRequest.getPassword());

        LoginInfo loginInfo = loginInfoServices.deleteLoginInfo(deleteLoginInfoRequest);
        user.getLoginInfoDetails().remove(loginInfo);
        userRepository.save(user);
        DeleteLoginInfoResponse response = new DeleteLoginInfoResponse();
        response.setDeleted(true);
        return response;
    }

    @Override
    public IdentityInfoResponse addIdentityInfo(IdentityInfoRequest identityInfoRequest) {
        checkUserLoggedIn(identityInfoRequest.getUsername());
        User user = userRepository.findByUsername(identityInfoRequest.getUsername());
        checkUserExists(user);
        UserData userData = getUserDataFrom(user);
        IdentityInfo savedIdentityInfo = identityInfoServices.addIdentityInfo(identityInfoRequest
                , userData);

        addIdentityInfoTo(user, savedIdentityInfo);
        return mapResponse(savedIdentityInfo,user);

    }

    private  void addIdentityInfoTo(User user, IdentityInfo savedIdentityInfo) {
        List<IdentityInfo> userIdentities = user.getIdentities();
        userIdentities.add(savedIdentityInfo);
        user.setIdentities(userIdentities);
        userRepository.save(user);
    }


    @Override
    public long countIdentityInfoFor(String username) {
        checkUserLoggedIn(username);
        User user = userRepository.findByUsername(username);
        checkUserExists(user);

        return user.getIdentities().size();
    }

    @Override
    public DeleteIdentityInfoResponse deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest) {
        checkUserLoggedIn(deleteIdentityInfoRequest.getUsername());
        User user = userRepository.findByUsername(deleteIdentityInfoRequest.getUsername());
        checkUserExists(user);
        IdentityInfo deletedIdentityInfoInfo = identityInfoServices.deleteIdentityInfo(deleteIdentityInfoRequest);
        user.getIdentities().remove(deletedIdentityInfoInfo);
        userRepository.save(user);

        return mapResponse();
    }



    @Override
    public GetIdentityInfoResponse editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest) {
        checkNullFields(editIdentityInfoRequest);
        checkUserLoggedIn(editIdentityInfoRequest.getUsername());
        User user = userRepository.findByUsername(editIdentityInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user,editIdentityInfoRequest.getPassword());
        return getIdentityInfoResponse(editIdentityInfoRequest, user);
    }

    private static void checkNullFields(EditIdentityInfoRequest editIdentityInfoRequest) {
        if (editIdentityInfoRequest.getUsername() == null) throw new PasswordManagerException("You must provide a username");
        if (editIdentityInfoRequest.getPassword() == null) throw new PasswordManagerException("You must provide a password");
    }

    private GetIdentityInfoResponse getIdentityInfoResponse(EditIdentityInfoRequest editIdentityInfoRequest, User user) {
        IdentityInfo editedIdentityInfo = identityInfoServices.editIdentityInfo(editIdentityInfoRequest);
        replaceIdentityInfo(user, editedIdentityInfo);
        GetIdentityInfoResponse response = mapResponse(editedIdentityInfo);
        decryptIdentityInfo(response, editedIdentityInfo,user);
        return response;
    }

    private static void decryptIdentityInfo(GetIdentityInfoResponse response,
                                            IdentityInfo editedIdentityInfo,User user) {
        response.setNationalIdentityNumber(NumericCipher.decrypt(editedIdentityInfo.getNationalIdentityNumber(),user.getKey()));
    }

    private void replaceIdentityInfo(User user, IdentityInfo editedIdentityInfo) {
        List<IdentityInfo> userIdentities = user.getIdentities();
        userIdentities.removeIf(identity -> identity.getId().equals(editedIdentityInfo.getId()));
        userIdentities.add(editedIdentityInfo);
        user.setIdentities(userIdentities);
        userRepository.save(user);
    }

    public GetIdentityInfoResponse getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest){
        checkNullFields(getIdentityInfoRequest);
        checkUserLoggedIn(getIdentityInfoRequest.getUsername());
        User user = userRepository.findByUsername(getIdentityInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user,getIdentityInfoRequest.getPassword());

        IdentityInfo savedIdentityInfo = identityInfoServices.getIdentityInfo(getIdentityInfoRequest);

        return decryptIdentity(savedIdentityInfo,user);


    }

    private static void checkNullFields(GetIdentityInfoRequest getIdentityInfoRequest) {
        if (getIdentityInfoRequest.getUsername() == null)throw new FieldRequiredException(
                "Username required");
        if (getIdentityInfoRequest.getPassword() == null)throw new FieldRequiredException("Password required");
    }

    private static GetIdentityInfoResponse decryptIdentity(IdentityInfo savedIdentityInfo,
                                                           User user) {
        GetIdentityInfoResponse response = mapResponse(savedIdentityInfo);
        decryptIdentityInfo(response, savedIdentityInfo, user);
        return response;
    }


    @Override
    public CreditCardInfoResponse addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest) {
        checkUserLoggedIn(creditCardInfoRequest.getUsername());
        User user = userRepository.findByUsername(creditCardInfoRequest.getUsername());
        checkUserExists(user);
        UserData userData = getUserDataFrom(user);

        CreditCardInfo creditCard =
                creditCardInfoServices.addCreditCardInfo(creditCardInfoRequest,userData);
        addCreditCardInfoTo(user, creditCard);


        return mapResponse(creditCard, user);
    }

    private void addCreditCardInfoTo(User user, CreditCardInfo creditCard) {
        List<CreditCardInfo> userCardDetails = user.getCreditCardDetails();
        userCardDetails.add(creditCard);
        user.setCreditCardDetails(userCardDetails);
        userRepository.save(user);
    }

    @Override
    public GetCreditCardInfoResponse getCreditCardInfo(GetCardInfoRequest getCardInfoRequest){

        checkNullFields(getCardInfoRequest);
        checkUserLoggedIn(getCardInfoRequest.getUsername());
        User user = userRepository.findByUsername(getCardInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user,getCardInfoRequest.getPassword());

        CreditCardInfo savedCard = creditCardInfoServices.getCreditCardInfo(getCardInfoRequest);
        return decryptCardInfo(savedCard, user);
    }

    @Override
    public GetCreditCardInfoResponse editCreditCardInfo(EditCardInfoRequest editCardInfoRequest){
        checkUserLoggedIn(editCardInfoRequest.getUsername());
        User user = userRepository.findByUsername(editCardInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user, editCardInfoRequest.getPassword());
        UserData userData = getUserDataFrom(user);
        CreditCardInfo editedCard = creditCardInfoServices.editCreditCardInfo(editCardInfoRequest
                ,userData);
        replaceCardInfo(user, editedCard);
        userRepository.save(user);


        return decryptCardInfo(editedCard, user);

    }

    private static void replaceCardInfo(User user, CreditCardInfo editedCard) {
        List<CreditCardInfo> userCardDetails = user.getCreditCardDetails();
        userCardDetails.removeIf(card -> card.getId().equals(editedCard.getId()));
        userCardDetails.add(editedCard);
        user.setCreditCardDetails(userCardDetails);
    }

    private static GetCreditCardInfoResponse decryptCardInfo(CreditCardInfo editedCard, User user) {
        GetCreditCardInfoResponse response = map(editedCard);
        response.setCvv(NumericCipher.decrypt(editedCard.getCvv(), user.getKey()));
        response.setCardNumber(NumericCipher.decrypt(editedCard.getCardNumber(), user.getKey()));
        return response;
    }


    private static void checkNullFields(GetCardInfoRequest getCardInfoRequest) {
        if (getCardInfoRequest.getUsername() == null)throw new FieldRequiredException(
                "Username required");
        if (getCardInfoRequest.getPassword() == null)throw new FieldRequiredException("Password required");
    }


    @Override
    public int countCreditCardInfoFor(String username) {
        checkUserLoggedIn(username);
        User user = userRepository.findByUsername(username);
        return user.getCreditCardDetails().size();
    }

    @Override
    public DeleteCreditCardInfoResponse deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest) {
        checkUserLoggedIn(deleteCardInfoRequest.getUsername());
        CreditCardInfo deletedCreditCardInfo = creditCardInfoServices.deleteCreditCardInfo(deleteCardInfoRequest);
        User user = userRepository.findByUsername(deleteCardInfoRequest.getUsername());
        checkUserExists(user);
        user.getCreditCardDetails().remove(deletedCreditCardInfo);
        userRepository.save(user);

        DeleteCreditCardInfoResponse response = new DeleteCreditCardInfoResponse();
        response.setDeleted(true);
        return response;
    }

    @Override
    public LoginInfoResponse editLoginInfo(EditLoginInfoRequest editLoginInfoRequest) throws InvalidURLException {
        checkUserLoggedIn(editLoginInfoRequest.getUsername());
        User user = userRepository.findByUsername(editLoginInfoRequest.getUsername());
        checkUserExists(user);

        UserData userData = getUserDataFrom(user);
        LoginInfo editedLoginInfo = loginInfoServices.editLoginInfo(editLoginInfoRequest, userData);
        replaceLoginInfo(user, editedLoginInfo);
        userRepository.save(user);
        return map(editedLoginInfo);
    }

    private static void replaceLoginInfo(User user, LoginInfo editedLoginInfo) {
        List<LoginInfo> userLoginInfos = user.getLoginInfoDetails();
        userLoginInfos.removeIf(eachLogin -> eachLogin.getId().equals(editedLoginInfo.getId()));
        userLoginInfos.add(editedLoginInfo);
        user.setLoginInfoDetails(userLoginInfos);
    }

    @Override
    public GetLoginInfoResponse getLoginInfo(GetLoginInfoRequest getLoginInfoRequest){
        checkNullFields(getLoginInfoRequest);
        checkUserLoggedIn(getLoginInfoRequest.getUsername());
        User user = userRepository.findByUsername(getLoginInfoRequest.getUsername());
        authenticateUser(user, getLoginInfoRequest.getPassword());

        LoginInfo loginInfo = loginInfoServices.getLoginInfo(getLoginInfoRequest);
        GetLoginInfoResponse response = decryptLoginInfo(loginInfo,user);
        return mapToResponse(loginInfo, response);
    }

    private static void checkNullFields(GetLoginInfoRequest getLoginInfoRequest) {
        if (getLoginInfoRequest.getUsername() == null) throw new FieldRequiredException("Enter " +
                "necessary details to fetch login details");
        if (getLoginInfoRequest.getPassword() == null) throw new FieldRequiredException("Enter " +
                "necessary details to fetch login details");
    }

    private static GetLoginInfoResponse decryptLoginInfo(LoginInfo loginInfo,User user) {
        GetLoginInfoResponse response = new GetLoginInfoResponse();
        response.setSavedPassword(Cipher.decrypt(loginInfo.getSavedPassword(), user.getKey()));
        return response;
    }



    private void authenticateUser(User user, String password){
        checkUserExists(user);
        if (!Cipher.decrypt(user.getPassword(),user.getKey()).equals(password))throw new IncorrectPasswordException(
                "The " +
                "password you entered is incorrect");
    }


    @Override
    public long countLoginInfoFor(String username) {
        checkUserLoggedIn(username);
        User user = userRepository.findByUsername(username);

        return user.getLoginInfoDetails().size();
    }

    private static void validateLogin(LoginRequest loginRequest, User user){
        check(loginRequest.getUsername());
        if (user == null)throw new UserNotFoundException("Username or password " +
                        "entered is not correct");
        if (!loginRequest.getPassword().equals(Cipher.decrypt(user.getPassword(), user.getKey())))throw new IncorrectPasswordException("Username or password entered is not correct");
    }

    private static void check(String username){
        boolean isInvalid = username.isEmpty() || username.isBlank();
        boolean containSpace = username.contains(" ");
        if (isInvalid)throw new IncorrectUsernameFormatException("Enter valid username");
        if (containSpace)throw new IncorrectUsernameFormatException("Remove spaces");
    }

    private void validateUsername(String username){
        check(username);
        if (userRepository.existsByUsername(username))throw new UsernameExistsException(String.format("%s already exists",username));
    }

    private void addLoginDetailsTo(User user, LoginInfo loginInfo) {
        List<LoginInfo> userLoginDetailInfos = user.getLoginInfoDetails();
        userLoginDetailInfos.add(loginInfo);
        user.setLoginInfoDetails(userLoginDetailInfos);
        userRepository.save(user);
    }

    private void checkUserExists(User user){
        if (user == null)throw new UserNotFoundException("User does not exist");
    }
}
