package dataaccess;

import model.AuthData;
import model.UserData;
import service.ServerException;

public interface UserDao {
    public UserData getUser(String username) throws ServerException;

    void addUser(UserData user);

    void clear() throws DataAccessException;
}
