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

    public AuthData register(UserData userData) throws ServerException {
        try {
            if (userData == null || userData.username() == null || userData.password() == null || userData.email() == null) {
                throw new RequestException("Error: UserData can not be null");
            }
            if (dataAccess.getUserDao().getUser(userData.username()) != null) {
                throw new TakenException("Error: username is already taken");
            }
            dataAccess.getUserDao().addUser(userData);
            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            dataAccess.getAuthDao().addAuth(addAuth);
            return addAuth;
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }

    public AuthData loginUser(UserData userData) throws ServerException {
        try {
            if (!dataAccess.getUserDao().userExists(userData.username())) {
                throw new UnauthorizedException("Error: username is incorrect");
            }
            LoginRequest request = new LoginRequest(userData.username(), userData.password());
            if (!dataAccess.getUserDao().valid(request)) {
                throw new UnauthorizedException("Error: Wrong Password");
            }

            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            dataAccess.getAuthDao().addAuth(addAuth);
            return addAuth;
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }

    public void logoutUser(String authToken) throws ServerException {
        try {
            AuthData delete = dataAccess.getAuthDao().getAuthorization(authToken);
            if (delete == null) {
                throw new UnauthorizedException("Error: unauthorized");
            }
            dataAccess.getAuthDao().deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }

    private String createAuthToken() {
        return UUID.randomUUID().toString();
    }
}
