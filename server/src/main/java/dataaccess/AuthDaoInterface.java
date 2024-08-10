package dataaccess;

import model.AuthData;

public interface AuthDaoInterface {
    void addAuth(AuthData authData) throws DataAccessException;
    void clear() throws DataAccessException;
    AuthData getAuthorization(String authToken) throws DataAccessException;
    void deleteAuth(String authToken) throws DataAccessException;
}
