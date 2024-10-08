package service;

import com.mysql.cj.log.Log;
import dataaccess.DataAccess;
import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import model.*;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;

import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public RegisterResponse register(UserData userData) {
        try {
            //Check to see if any provided information is null
            if (userData == null) {
                return new RegisterResponse(null,null, "Error: UserData can not be null");
            }
            if (userData.username() == null) {
                return new RegisterResponse(null,null, "Error: UserData can not be null");
            }
            if (userData.password() == null){
                return new RegisterResponse(null,null, "Error: UserData can not be null");
            }
            if (userData.email() == null) {
                return new RegisterResponse(null,null, "Error: UserData can not be null");
            }

            //Check to see if the user is already registered
            if (dataAccess.getUserDao().getUser(userData.username()) != null) {
                return new RegisterResponse(null,null, "Error: username is already taken");
            }

            UserData hashedUser = new UserData(userData.username(), hashPassword(userData.password()), userData.email());
            dataAccess.getUserDao().addUser(hashedUser);

            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            dataAccess.getAuthDao().addAuth(addAuth);

            return new RegisterResponse(authToken, userData.username(), null);
        } catch (DataAccessException e) {
            return new RegisterResponse(null,null, e.getMessage());
        }
    }

    public LogoutResponse logoutUser(String authToken) {
        LogoutResponse response = new LogoutResponse(null);
        try {
            AuthData delete = dataAccess.getAuthDao().getAuthorization(authToken);
            if (delete == null) {
                return new LogoutResponse("Error: unauthorized");
            }
            dataAccess.getAuthDao().deleteAuth(authToken);
        } catch (DataAccessException e) {
            return new LogoutResponse(e.getMessage());
        }
        return response;
    }

    public LoginResponse loginUser(UserData userData) {
        try {
            if (!dataAccess.getUserDao().userExists(userData.username())) {
                return new LoginResponse(null,null,"Error: username is incorrect");
            }
            LoginRequest request = new LoginRequest(userData.username(), userData.password());
            if (!dataAccess.getUserDao().valid(request)) {
                return new LoginResponse(null, null,"Error: Wrong Password");
            }

            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            System.out.println(addAuth);
            dataAccess.getAuthDao().addAuth(addAuth);
            return new LoginResponse(addAuth.username(), addAuth.authToken(), null);

        } catch (DataAccessException e) {
            return new LoginResponse(null, null, e.getMessage());
        }
    }

    private String createAuthToken() {
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
