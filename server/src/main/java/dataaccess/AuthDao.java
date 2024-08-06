package dataaccess;

import model.AuthData;

public interface AuthDao {

    void clear() throws DataAccessException;

    void addAuthorization(AuthData authData) throws DataAccessException;

    AuthData getAuthorization(String authToken) throws DataAccessException;

    void deleteAuthorization(String authToken) throws DataAccessException;
}
