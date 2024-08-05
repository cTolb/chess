package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;
import server.Server;

import javax.xml.crypto.Data;
import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService (DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    public AuthData registerUser(UserData user) throws ServerException{
        try {
            if (user == null || user.username() == null || user.password() == null || user.email() == null) {
                throw new ServerException("Values can not be empty");
            }
            if (dataAccess.getUserDao().getUser(user.username()) != null) {
                throw new ServerException("Username already registered");
            }
            dataAccess.getUserDao().addUser(user);
            String authToken = createAuthToken();
            AuthData authorizationData = new AuthData(authToken, user.username());
            dataAccess.getAuthDao().addAuth(authorizationData);
            return authorizationData;
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }

    public AuthData loginUser(UserData user) throws ServerException {
        try {
            UserData exists = dataAccess.getUserDao().getUser(user.username());
            if (exists == null) {
                throw new ServerException("Username not registered");
            }
            if (!user.password().equals(exists.password())) {
                throw new ServerException("Password is incorrect");
            }
            String authToken = createAuthToken();
            AuthData authorizationData = new AuthData(authToken, user.username());
            dataAccess.getAuthDao().addAuth(authorizationData);
            return authorizationData;
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }

    }

    public String createAuthToken() {
        return UUID.randomUUID().toString();
    }
    public void logout(String authToken) throws ServerException {
        try {
            AuthData found = dataAccess.getAuthDao().getAuth(authToken);
            if (found == null) {
                throw new ServerException("Error: unauthorized");
            }
            dataAccess.getAuthDao().deleteAuth(authToken);
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }
}
