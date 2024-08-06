package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.UserData;

import java.util.HashMap;

public class MemUserDao implements UserDao {
    HashMap<String, UserData> users = new HashMap<>();
    @Override
    public void clear() throws DataAccessException {
        users.clear();
    }

    @Override
    public void addUser(UserData user) throws DataAccessException {
        if (user == null) {
            throw new DataAccessException("User can not be null");
        }
        if (users.containsKey(user.username())) {
            throw new DataAccessException("User is already registered");
        }
        users.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }
}
