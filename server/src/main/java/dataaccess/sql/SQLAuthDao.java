package dataaccess.sql;

import dataaccess.AuthDaoInterface;
import dataaccess.DataAccessException;
import model.AuthData;

public class SQLAuthDao implements AuthDaoInterface {
    @Override
    public void addAuth(AuthData authData) throws DataAccessException {

    }

    @Override
    public void clear() throws DataAccessException {

    }

    @Override
    public AuthData getAuthorization(String authToken) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {

    }
}
