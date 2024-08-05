package dataaccess.memory;

import dataaccess.AuthDao;
import dataaccess.DataAccessException;
import model.AuthData;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class MemAuthDao implements AuthDao {
    private final HashMap<String, AuthData> authTokens = new HashMap<>();

    @Override
    public void addAuth(AuthData authData) throws DataAccessException {
        if (authTokens.containsKey(authData.authToken())) {
            throw new DataAccessException("Authorization token already exists");
        }
        authTokens.put(authData.authToken(), authData);

    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        authTokens.remove(authToken);
    }

    @Override
    public AuthData getAuth(String authToken) {
        return authTokens.get(authToken);
    }

    @Override
    public AuthData createAuth(UserData user) {
        return null;
    }

    @Override
    public void clear() throws DataAccessException{
        authTokens.clear();
    }
}
