package service;

import dataaccess.DataAccess;
import model.AuthData;
import model.UserData;

public class UserService {
    private final DataAccess dataAccess;

    public UserService (DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    public AuthData registerUser(UserData user) {
        dataAccess.getUserDao().addUser(user);
    }
}
