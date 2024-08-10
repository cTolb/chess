package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.*;
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
            if (userData == null || userData.username() == null || userData.password() == null || userData.email() == null) {
                return new RegisterResponse(null, "Error: UserData can not be null");
            }
            if (dataAccess.getUserDao().getUser(userData.username()) != null) {
                return new RegisterResponse(null, "Error: username is already taken");
            }
            dataAccess.getUserDao().addUser(userData);
            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            dataAccess.getAuthDao().addAuth(addAuth);

            return new RegisterResponse(addAuth, null);
        } catch (DataAccessException e) {
            return new RegisterResponse(null, e.getMessage());
        }
    }

    public LogoutResponse logoutUser(String authToken) {
        try {
            AuthData delete = dataAccess.getAuthDao().getAuthorization(authToken);
            if (delete == null) {
                return new LogoutResponse("Error: unauthorized");
            }
            dataAccess.getAuthDao().deleteAuth(authToken);
        } catch (DataAccessException e) {
            return new LogoutResponse(e.getMessage());
        }
        return new LogoutResponse(null);
    }

    public LoginResponse loginUser(UserData userData) {
        try {
            if (!dataAccess.getUserDao().userExists(userData.username())) {
                return new LoginResponse(null,"Error: username is incorrect");
            }
            LoginRequest request = new LoginRequest(userData.username(), userData.password());
            if (!dataAccess.getUserDao().valid(request)) {
                return new LoginResponse(null, "Error: Wrong Password");
            }

            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            dataAccess.getAuthDao().addAuth(addAuth);
            return new LoginResponse(addAuth, null);

        } catch (DataAccessException e) {
            return new LoginResponse(null, e.getMessage());
        }
    }

    private String createAuthToken() {
        return UUID.randomUUID().toString();
    }
}
