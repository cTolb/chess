package server.service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import server.Server;

import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData registerUser(UserData userData) throws DataAccessException{
        /*if (userData.username() == null) {
            throw new DataAccessException("Error: Username can not be null");
        }
        if (userData.password() == null) {
            throw new DataAccessException("Error: Password can not be null");
        }
        if (userData.email() == null) {
            throw new DataAccessException("Error: Email can not be null");
        }
        if (dataAccess.getUserDao().getUser(userData.username()) != null) {
            throw new DataAccessException("Error: User is already registered");
        }*/

        dataAccess.getUserDao().addUser(userData);
        String authToken = createAuthToken();
        AuthData addAuth = new AuthData(authToken, userData.username());
        dataAccess.getAuthDao().addAuthorization(addAuth);
        return addAuth;
    }

    public AuthData loginUser(UserData userData) throws DataAccessException {
        UserData user = dataAccess.getUserDao().getUser(userData.username());
        if (user == null || !user.password().equals(userData.password())){
            throw new DataAccessException("Error: username or password incorrect");
        }

        String authToken = createAuthToken();
        AuthData addAuth = new AuthData(authToken, userData.username());
        dataAccess.getAuthDao().addAuthorization(addAuth);
        return addAuth;
    }

    public void logoutUser(String authToken) throws DataAccessException {
        dataAccess.getAuthDao().deleteAuthorization(authToken);
    }

    private String createAuthToken() {
        return UUID.randomUUID().toString();
    }
}
