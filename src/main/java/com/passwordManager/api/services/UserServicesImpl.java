package com.passwordManager.api.services;

import com.passwordManager.api.data.models.*;
import com.passwordManager.api.data.repositories.UserRepository;
import com.passwordManager.api.dtos.requests.*;
import com.passwordManager.api.dtos.responses.RegisterUserResponse;
import com.passwordManager.api.exceptions.*;
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

    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public void login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        validateLogin(loginRequest, user);
        user.setLoggedIn(true);
        userRepository.save(user);
    }

    @Override
    public boolean isLoggedIn(String username) {
        User user = userRepository.findByUsername(username);
        return user.isLoggedIn();
    }


    @Override
    public void addNewClassifiedInfo(ClassInfoTypeRequest classInfoTypeRequest) {

    }



    @Override
    public void addLoginInfo(LoginInfoRequest loginInfoRequest) {
        Login login = loginInfoServices.addLogin(loginInfoRequest);
        User user = userRepository.findByUsername(loginInfoRequest.getUsername());
        checkUserExists(user);
        addLoginDetailsTo(user, login);

    }

    @Override
    public void deleteLoginInfo(DeleteLoginInfoRequest deleteLoginInfoRequest) {
        User user = userRepository.findByUsername(deleteLoginInfoRequest.getUsername());
        checkUserExists(user);

        Login login = loginInfoServices.deleteLoginInfo(deleteLoginInfoRequest);
        user.getLoginDetails().remove(login);
        userRepository.save(user);
    }

    @Override
    public void addIdentityInfo(IdentityInfoRequest identityInfoRequest) {
        Identity savedIdentity = identityInfoServices.addIdentityInfo(identityInfoRequest);
        User user = userRepository.findByUsername(identityInfoRequest.getUser());
        checkUserExists(user);

        List<Identity> userIdentities = user.getIdentities();
        userIdentities.add(savedIdentity);
        user.setIdentities(userIdentities);
        userRepository.save(user);

    }

    @Override
    public long countIdentityInfo(String username) {
        User user = userRepository.findByUsername(username);
        checkUserExists(user);

        return user.getIdentities().size();
    }

    @Override
    public void deleteIdentityInfo(DeleteIdentityInfoRequest deleteIdentityInfoRequest) {
        User user = userRepository.findByUsername(deleteIdentityInfoRequest.getUser());
        checkUserExists(user);
        Identity deletedIdentityInfo = identityInfoServices.deleteIdentityInfo(deleteIdentityInfoRequest);
        user.getIdentities().remove(deletedIdentityInfo);
        userRepository.save(user);

    }

    @Override
    public void editIdentityInfo(EditIdentityInfoRequest editIdentityInfoRequest) {
        User user = userRepository.findByUsername(editIdentityInfoRequest.getUser());
        checkUserExists(user);

        Identity editedIdentity = identityInfoServices.editIdentityInfo(editIdentityInfoRequest);
        List<Identity> userIdentities = user.getIdentities();
        userIdentities.removeIf(identity -> identity.getId().equals(editedIdentity.getId()));
        userIdentities.add(editedIdentity);
        userRepository.save(user);
    }

    @Override
    public void addCreditCardInfo(CreditCardInfoRequest creditCardInfoRequest) {
        User user = userRepository.findByUsername(creditCardInfoRequest.getUser());
        checkUserExists(user);

        CreditCardInfo creditCard = creditCardInfoServices.addCreditCardInfo(creditCardInfoRequest);
        List<CreditCardInfo> userCardDetails = user.getCreditCardDetails();
        userCardDetails.add(creditCard);
        user.setCreditCardDetails(userCardDetails);
        userRepository.save(user);
    }

    @Override
    public int countCreditCardInfo(String username) {
        User user = userRepository.findByUsername(username);
        return user.getCreditCardDetails().size();
    }

    @Override
    public void deleteCreditCardInfo(DeleteCardInfoRequest deleteCardInfoRequest) {
        CreditCardInfo deletedCreditCardInfo =
                creditCardInfoServices.deleteCreditCardInfo(deleteCardInfoRequest);
        User user = userRepository.findByUsername(deleteCardInfoRequest.getUser());
        checkUserExists(user);
        user.getCreditCardDetails().remove(deletedCreditCardInfo);
        userRepository.save(user);

    }

    @Override
    public void editLoginInfo(EditLoginInfoRequest editLoginInfoRequest) {
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

    }


    private void checkInputInfo(String info){
        boolean isValid = info.equals("login") || info.equals("identity") || info.equals("card");
        if (!isValid)throw new InvalidInputException(String.format("%s is an invalid input, enter" +
                " 'login' or 'identity' or 'card'",info));
    }

    @Override
    public long countLoginType(String username) {
        User user = userRepository.findByUsername(username);

        return user.getLoginDetails().size();
    }

    private static void validateLogin(LoginRequest loginRequest, User user){
        check(loginRequest.getUsername());
        if (user == null)throw new UserNotFoundException(String.format("%s does not exist", loginRequest.getUsername()));
        if (!loginRequest.getPassword().equals(user.getPassword()))throw new IncorrectPasswordException("Password " +
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
