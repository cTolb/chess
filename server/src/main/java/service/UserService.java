package service;

import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import model.*;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;

import java.util.UUID;

public class UserService {
    private final MemoryDataAccess memoryDataAccess;

    public UserService(MemoryDataAccess memoryDataAccess) {
        this.memoryDataAccess = memoryDataAccess;
    }

    public RegisterResponse register(UserData userData) {
        try {
            if (userData == null || userData.username() == null || userData.password() == null || userData.email() == null) {
                return new RegisterResponse(null, "Error: UserData can not be null");
            }
            if (memoryDataAccess.getUserDao().getUser(userData.username()) != null) {
                return new RegisterResponse(null, "Error: username is already taken");
            }

            String hashedPassword = hashPassword(userData.password());
            UserData hashedUser = new UserData(userData.username(), hashedPassword, userData.email());
            memoryDataAccess.getUserDao().addUser(hashedUser);
            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            memoryDataAccess.getAuthDao().addAuth(addAuth);

            return new RegisterResponse(addAuth, null);
        } catch (DataAccessException e) {
            return new RegisterResponse(null, e.getMessage());
        }
    }

    public LogoutResponse logoutUser(String authToken) {
        try {
            AuthData delete = memoryDataAccess.getAuthDao().getAuthorization(authToken);
            if (delete == null) {
                return new LogoutResponse("Error: unauthorized");
            }
            memoryDataAccess.getAuthDao().deleteAuth(authToken);
        } catch (DataAccessException e) {
            return new LogoutResponse(e.getMessage());
        }
        return new LogoutResponse(null);
    }

    public LoginResponse loginUser(UserData userData) {
        try {
            if (!memoryDataAccess.getUserDao().userExists(userData.username())) {
                return new LoginResponse(null,"Error: username is incorrect");
            }
            LoginRequest request = new LoginRequest(userData.username(), userData.password());
            if (!memoryDataAccess.getUserDao().valid(request)) {
                return new LoginResponse(null, "Error: Wrong Password");
            }

            String authToken = createAuthToken();
            AuthData addAuth = new AuthData(authToken, userData.username());
            memoryDataAccess.getAuthDao().addAuth(addAuth);
            return new LoginResponse(addAuth, null);

        } catch (DataAccessException e) {
            return new LoginResponse(null, e.getMessage());
        }
    }

    private String createAuthToken() {
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
