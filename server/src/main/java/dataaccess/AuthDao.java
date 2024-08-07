package dataaccess;

import model.AuthData;
import java.util.HashMap;

public class AuthDao {
    private final HashMap<String, AuthData> authTokens = new HashMap<>();

    public void addAuth(AuthData authData) throws DataAccessException {
        if (authTokens.containsKey(authData.authToken())) {
            throw new DataAccessException("Authorization token already exists");
        }
        authTokens.put(authData.authToken(), authData);
    }

    public void clear() throws DataAccessException {
        authTokens.clear();
    }

    public AuthData getAuthorization(String authToken) throws DataAccessException {
        return authTokens.get(authToken);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        authTokens.remove(authToken);
    }
}
