package dataaccess;

import model.AuthData;

public interface AuthDao {
    void addAuth(String authToken);
    void deleteAuth(String authToken);

    AuthData getAuth(String authToken);
}
