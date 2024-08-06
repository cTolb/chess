package dataaccess.memory;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import model.AuthData;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class MemAuthDao implements AuthDao {
    HashMap<String, AuthData> authTokens = new HashMap<>();
    @Override
    public void clear() throws DataAccessException {
        authTokens.clear();
    }

    @Override
    public void addAuthorization(AuthData authData) throws DataAccessException {
        if (authTokens.containsKey(authData.authToken())) {
            throw new DataAccessException("Authorization token already exists");
        }
        authTokens.put(authData.authToken(), authData);
    }

    @Override
    public AuthData getAuthorization(String authToken) throws DataAccessException {
        return authTokens.get(authToken);
    }

    @Override
    public void deleteAuthorization(String authToken) throws DataAccessException {
        authTokens.remove(authToken);
    }
}
