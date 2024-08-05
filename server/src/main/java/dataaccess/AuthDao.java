package dataaccess;

import model.AuthData;
import model.UserData;

public interface AuthDao {
    void addAuth(AuthData authData) /*throws DataAccessException*/;
    void deleteAuth(String authToken);
    AuthData getAuth(String authToken);
    AuthData createAuth(UserData user);

    void clear();
}