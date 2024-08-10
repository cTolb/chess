package dataaccess;

import model.UserData;
import requests.LoginRequest;

public interface UserDaoInterface {
    void clear() throws DataAccessException;
    void addUser(UserData user) throws DataAccessException;

    UserData getUser(String username);
    boolean valid(LoginRequest request) throws DataAccessException;
    boolean userExists(String username) throws DataAccessException;
}
