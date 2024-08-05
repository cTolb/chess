package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.AuthData;
import model.UserData;
import service.ServerException;

import java.util.HashMap;

public class MemUserDao implements UserDao {

    HashMap<String, UserData> users = new HashMap<>();

    @Override
    public UserData getUser(String username) throws ServerException {
        return users.get(username);
    }

    @Override
    public void addUser(UserData user) {
        users.put(user.username(), user);
    }

    @Override
    public void clear() throws DataAccessException {
        users.clear();
    }
}
