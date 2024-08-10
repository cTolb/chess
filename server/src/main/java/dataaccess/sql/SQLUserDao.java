package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.UserDaoInterface;
import model.UserData;
import requests.LoginRequest;

public class SQLUserDao implements UserDaoInterface {
    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public void addUser(UserData user) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) {
        return null;
    }

    @Override
    public boolean valid(LoginRequest request) throws DataAccessException {
        return false;
    }

    @Override
    public boolean userExists(String username) throws DataAccessException {
        return false;
    }
}
