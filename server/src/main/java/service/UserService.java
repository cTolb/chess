package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.*;
import server.Server;

import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public RegisterResponse register(UserData userData) throws  DataAccessException {
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
    }

    public void logoutUser(String authToken) throws ServerException {
        try {
            AuthData delete = dataAccess.getAuthDao().getAuthorization(authToken);
            if (delete == null) {
                throw new ServerException("Error: unauthorized");
            }
            dataAccess.getAuthDao().deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }
    public LoginResponse loginUser(UserData userData) throws ServerException {
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
            throw new ServerException(e);
        }
    }


    private String createAuthToken() {
        return UUID.randomUUID().toString();
    }
}
