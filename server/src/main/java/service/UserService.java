package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.LoginRequest;
import model.UserData;

import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData register(UserData userData) throws DataAccessException{
        if (userData == null || userData.username() == null || userData.password() == null || userData.email() == null) {
            throw new DataAccessException("Error: UserData can not be null");
        }
        if (dataAccess.getUserDao().getUser(userData.username()) != null) {
            throw new DataAccessException("Error: username is already taken");
        }
        dataAccess.getUserDao().addUser(userData);
        String authToken = createAuthToken();
        AuthData addAuth = new AuthData(authToken, userData.username());
        dataAccess.getAuthDao().addAuth(addAuth);
        return addAuth;
    }

    public AuthData loginUser(UserData userData) throws DataAccessException {
        if (!dataAccess.getUserDao().userExists(userData.username())){
            throw new DataAccessException("Error: username is incorrect");
        }
        LoginRequest request = new LoginRequest(userData.username(), userData.password());
        if (!dataAccess.getUserDao().valid(request)) {
            throw new DataAccessException("WrongPassword");
        }

        String authToken = createAuthToken();
        AuthData addAuth = new AuthData(authToken, userData.username());
        dataAccess.getAuthDao().addAuth(addAuth);
        return addAuth;
    }

    public void logoutUser(String authToken) throws DataAccessException {
        dataAccess.getAuthDao().deleteAuth(authToken);
    }

    private String createAuthToken() {
        return UUID.randomUUID().toString();
    }
}
