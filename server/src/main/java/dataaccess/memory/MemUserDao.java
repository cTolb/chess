package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;

import java.util.HashMap;

public class MemUserDao implements UserDao {

    HashMap<String, UserData> users = new HashMap<>();

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public AuthData registerUser(UserData user) {
        return null;
    }

    @Override
    public void addUser(UserData user) {

    }
}
