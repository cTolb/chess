package dataaccess;

import model.AuthData;
import model.UserData;

public interface UserDao {
    public UserData getUser(String username) throws DataAccessException;

    public AuthData registerUser(UserData user);

    void addUser(UserData user);
}
