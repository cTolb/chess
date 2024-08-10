package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.UserDaoInterface;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import model.UserData;

import java.util.HashMap;

public class MemoryUserDao implements UserDaoInterface {
    private final HashMap<String, UserData> users = new HashMap<>();

    @Override
    public void clear() throws DataAccessException {
        users.clear();
    }

    @Override
    public void addUser(UserData user) throws DataAccessException {
        if (userExists(user.username())) {
            throw new DataAccessException("User is already registered");
        }
        users.put(user.username(), user);
    }

    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public boolean valid(LoginRequest request) throws DataAccessException {
        UserData user = users.get(request.username());
        return user != null && checkPassword(request.password(), user.password());
    }

    @Override
    public boolean userExists(String username) throws DataAccessException {
        return users.containsKey(username);
    }

    private boolean checkPassword(String regularPassword, String hashedPassword) {
        return BCrypt.checkpw(regularPassword, hashedPassword);
    }
}
