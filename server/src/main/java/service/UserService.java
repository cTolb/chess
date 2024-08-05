package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import java.util.UUID;

public class UserService {
    private final DataAccess dataAccess;

    public UserService (DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    public AuthData registerUser(UserData user){
        /*if (user == null || user.username() == null || user.password() == null || user.email() == null) {

        }*/
        dataAccess.getUserDao().addUser(user);
        String authToken = createAuthToken();
        AuthData authorizationData = new AuthData(authToken, user.username());
        dataAccess.getAuthDao().addAuth(authorizationData);
        return authorizationData;
    }

    public String createAuthToken() {
        return UUID.randomUUID().toString();
    }
}
