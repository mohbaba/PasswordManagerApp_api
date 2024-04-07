package com.passwordManager.api.services;

import com.passwordManager.api.data.models.*;
import com.passwordManager.api.data.repositories.UserRepository;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.responses.*;
import com.passwordManager.api.exceptions.*;
import com.passwordManager.api.utilities.Cipher;
import com.passwordManager.api.utilities.NumericCipher;
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
//        checkUserLoggedIn(username);
        User user = userRepository.findByUsername(username);
        checkUserExists(user);
        return user.isLoggedIn();
    }



    @Override
    public LoginInfoResponse addLoginInfo(LoginInfoRequest loginInfoRequest) {
        checkUserLoggedIn(loginInfoRequest.getUsername());
        Login login = loginInfoServices.addLogin(loginInfoRequest);
        User user = userRepository.findByUsername(loginInfoRequest.getUsername());
        checkUserExists(user);
        addLoginDetailsTo(user, login);
        return map(login);
    }

    @Override
    public DeleteLoginInfoResponse deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest) {
        checkUserLoggedIn(deleteLoginInfoRequest.getUsername());
        User user = userRepository.findByUsername(deleteLoginInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user, deleteLoginInfoRequest.getPassword());

        Login login = loginInfoServices.deleteLoginInfo(deleteLoginInfoRequest);
        user.getLoginDetails().remove(login);
        userRepository.save(user);
        DeleteLoginInfoResponse response = new DeleteLoginInfoResponse();
        response.setDeleted(true);
        return response;
    }

    @Override
    public IdentityInfoResponse addIdentityInfo(IdentityInfoRequest identityInfoRequest) {
        checkUserLoggedIn(identityInfoRequest.getUsername());
        Identity savedIdentity = identityInfoServices.addIdentityInfo(identityInfoRequest);
        User user = userRepository.findByUsername(identityInfoRequest.getUsername());
        checkUserExists(user);

        List<Identity> userIdentities = user.getIdentities();
        userIdentities.add(savedIdentity);
        user.setIdentities(userIdentities);
        userRepository.save(user);

        return mapResponse(savedIdentity,user);

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
        Identity deletedIdentityInfo = identityInfoServices.deleteIdentityInfo(deleteIdentityInfoRequest);
        user.getIdentities().remove(deletedIdentityInfo);
        userRepository.save(user);

        return mapResponse();
    }



    @Override
    public IdentityInfoResponse editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest) {
        if (editIdentityInfoRequest.getUsername() == null) throw new PasswordManagerException("You must provide a username");
        checkUserLoggedIn(editIdentityInfoRequest.getUsername());
        User user = userRepository.findByUsername(editIdentityInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user,editIdentityInfoRequest.getPassword());

        return getIdentityInfoResponse(editIdentityInfoRequest, user);
    }

    private IdentityInfoResponse getIdentityInfoResponse(EditIdentityInfoRequest editIdentityInfoRequest, User user) {
        Identity editedIdentity = identityInfoServices.editIdentityInfo(editIdentityInfoRequest);
        List<Identity> userIdentities = user.getIdentities();
        userIdentities.removeIf(identity -> identity.getId().equals(editedIdentity.getId()));
        userIdentities.add(editedIdentity);
        User updatedUser = userRepository.save(user);

        return mapResponse(editedIdentity, updatedUser);
    }

    public GetIdentityInfoResponse getIdentityInfo(GetIdentityInfoRequest getIdentityInfoRequest){
        if (getIdentityInfoRequest.getUsername() == null)throw new FieldRequiredException(
                "Username required");
        if (getIdentityInfoRequest.getPassword() == null)throw new FieldRequiredException("Password required");
        checkUserLoggedIn(getIdentityInfoRequest.getUsername());
        User user = userRepository.findByUsername(getIdentityInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user,getIdentityInfoRequest.getPassword());

        Identity savedIdentity = identityInfoServices.getIdentityInfo(getIdentityInfoRequest);

        return decryptIdentity(savedIdentity);


    }

    private static GetIdentityInfoResponse decryptIdentity(Identity savedIdentity) {
        GetIdentityInfoResponse response = mapResponse(savedIdentity);
        response.setNationalIdentityNumber(NumericCipher.decrypt(savedIdentity.getNationalIdentityNumber()));
        return response;
    }


    @Override
    public CreditCardInfoResponse addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest) {
        checkUserLoggedIn(creditCardInfoRequest.getUsername());
        User user = userRepository.findByUsername(creditCardInfoRequest.getUsername());
        checkUserExists(user);

        CreditCardInfo creditCard = creditCardInfoServices.addCreditCardInfo(creditCardInfoRequest);
        List<CreditCardInfo> userCardDetails = user.getCreditCardDetails();
        userCardDetails.add(creditCard);
        user.setCreditCardDetails(userCardDetails);
        userRepository.save(user);

        return mapResponse(creditCard, user);
    }

    @Override
    public GetCreditCardInfoResponse getCreditCardInfo(GetCardInfoRequest getCardInfoRequest){

        checkNullFields(getCardInfoRequest);
        checkUserLoggedIn(getCardInfoRequest.getUsername());
        User user = userRepository.findByUsername(getCardInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user,getCardInfoRequest.getPassword());

        CreditCardInfo savedCard = creditCardInfoServices.getCreditCardInfo(getCardInfoRequest);
        GetCreditCardInfoResponse response = mapResponseTo(savedCard);
        response.setCardNumber(NumericCipher.decrypt(savedCard.getCardNumber()));
        response.setCvv(NumericCipher.decrypt(savedCard.getCvv()));
        return response;

    }

    @Override
    public EditCreditCardInfoResponse editCreditCardInfo(EditGetCardInfoRequest editGetCardInfoRequest){
        checkUserLoggedIn(editGetCardInfoRequest.getUsername());
        User user = userRepository.findByUsername(editGetCardInfoRequest.getUsername());
        checkUserExists(user);
        authenticateUser(user,editGetCardInfoRequest.getPassword());

        CreditCardInfo editedCard = creditCardInfoServices.editCreditCardInfo(editGetCardInfoRequest);
        replaceCardInfo(user, editedCard);
        userRepository.save(user);


        return decryptCardDetails(editedCard);

    }

    private static void replaceCardInfo(User user, CreditCardInfo editedCard) {
        List<CreditCardInfo> userCardDetails = user.getCreditCardDetails();
        userCardDetails.removeIf(card -> card.getId().equals(editedCard.getId()));
        userCardDetails.add(editedCard);
    }

    private static EditCreditCardInfoResponse decryptCardDetails(CreditCardInfo editedCard) {
        EditCreditCardInfoResponse response = map(editedCard);
        response.setCvv(NumericCipher.decrypt(editedCard.getCvv()));
        response.setCardNumber(NumericCipher.decrypt(editedCard.getCardNumber()));
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
        checkUserLoggedIn(deleteCardInfoRequest.getUser());
        CreditCardInfo deletedCreditCardInfo =
                creditCardInfoServices.deleteCreditCardInfo(deleteCardInfoRequest);
        User user = userRepository.findByUsername(deleteCardInfoRequest.getUser());
        checkUserExists(user);
        user.getCreditCardDetails().remove(deletedCreditCardInfo);
        userRepository.save(user);

        DeleteCreditCardInfoResponse response = new DeleteCreditCardInfoResponse();
        response.setDeleted(true);
        return response;
    }

    @Override
    public LoginInfoResponse editLoginInfo(EditLoginInfoRequest editLoginInfoRequest) {
        checkUserLoggedIn(editLoginInfoRequest.getUsername());
        User user = userRepository.findByUsername(editLoginInfoRequest.getUsername());
        checkUserExists(user);

        Login editedLogin = loginInfoServices.editLoginInfo(editLoginInfoRequest);
        List<Login> userLogins = user.getLoginDetails();
        for (int i = 0; i < userLogins.size(); i++) {
            Login eachLogin = userLogins.get(i);
            if (eachLogin.getId().equals(editedLogin.getId())){
                userLogins.remove(eachLogin);
                break;
            }
        }
        userLogins.add(editedLogin);
        user.setLoginDetails(userLogins);
        userRepository.save(user);
        return map(editedLogin);
    }

    @Override
    public GetLoginInfoResponse getLoginInfo(GetLoginInfoRequest getLoginInfoRequest){
        if (getLoginInfoRequest.getUsername() == null) throw new FieldRequiredException("Enter " +
                "necessary details to fetch login details");
        if (getLoginInfoRequest.getPassword() == null) throw new FieldRequiredException("Enter " +
                "necessary details to fetch login details");
        checkUserLoggedIn(getLoginInfoRequest.getUsername());
        User user = userRepository.findByUsername(getLoginInfoRequest.getUsername());
        authenticateUser(user, getLoginInfoRequest.getPassword());

        Login login = loginInfoServices.getLoginInfo(getLoginInfoRequest);
        GetLoginInfoResponse response = decryptLogin(login);
        return mapToResponse(login, response);
    }

    private static GetLoginInfoResponse decryptLogin(Login login) {
        GetLoginInfoResponse response = new GetLoginInfoResponse();
        response.setSavedPassword(Cipher.decrypt(login.getSavedPassword()));
        return response;
    }

    ;

    private void authenticateUser(User user, String password){
        if (!Cipher.decrypt(user.getPassword()).equals(password))throw new IncorrectPasswordException(
                "The " +
                "password you entered is incorrect");
    }


    @Override
    public long countLoginInfoFor(String username) {
        checkUserLoggedIn(username);
        User user = userRepository.findByUsername(username);

        return user.getLoginDetails().size();
    }

    private static void validateLogin(LoginRequest loginRequest, User user){
        check(loginRequest.getUsername());
        if (user == null)throw new UserNotFoundException(String.format("%s does not exist", loginRequest.getUsername()));
        if (!loginRequest.getPassword().equals(Cipher.decrypt(user.getPassword())))throw new IncorrectPasswordException(
                "Password " +
                "is not correct");
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

    private void addLoginDetailsTo(User user, Login login) {
        List<Login> userLoginDetails = user.getLoginDetails();
        userLoginDetails.add(login);
        user.setLoginDetails(userLoginDetails);
        userRepository.save(user);
    }

    private void checkUserExists(User user){
        if (user == null)throw new UserNotFoundException("User does not exist");
    }
}
