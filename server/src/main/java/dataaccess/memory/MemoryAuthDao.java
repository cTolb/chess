package dataaccess.memory;

import dataaccess.AuthDaoInterface;
import dataaccess.DataAccessException;
import model.AuthData;
import java.util.HashMap;

public class MemoryAuthDao implements AuthDaoInterface {
    private final HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public void addAuth(AuthData authData) throws DataAccessException {
        if (authTokens.containsKey(authData.authToken())) {
            throw new DataAccessException("Authorization token already exists");
        }
        authTokens.put(authData.authToken(), authData);
    }

    @Override
    public void clear() throws DataAccessException {
        authTokens.clear();
    }

    @Override
    public AuthData getAuthorization(String authToken) throws DataAccessException {
        return authTokens.get(authToken);
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        authTokens.remove(authToken);
    }
}
