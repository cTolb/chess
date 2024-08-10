package dataaccess.memory;

import dataaccess.DataAccessException;
import requests.LoginRequest;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDao {
    private final HashMap<String, UserData> users = new HashMap<>();
    public void clear() throws DataAccessException {
        users.clear();
    }

    public void addUser(UserData user) throws DataAccessException {
        if (userExists(user.username())) {
            throw new DataAccessException("User is already registered");
        }
        users.put(user.username(), user);
    }

    public UserData getUser(String username) {
        return users.get(username);
    }

    public boolean valid(LoginRequest request) throws DataAccessException {
        UserData user = users.get(request.username());
        return user != null && user.password().equals(request.password());
    }

    public boolean userExists(String username) throws DataAccessException {
        return users.containsKey(username);
    }
}
