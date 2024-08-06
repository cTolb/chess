package dataaccess;

import model.UserData;

public interface UserDao {
    void clear() throws DataAccessException;

    void addUser(UserData user) throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;
}
