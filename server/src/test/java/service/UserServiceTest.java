package service;

import dataaccess.AuthDao;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.AuthData;
import model.RegisterResponse;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.*;
import service.ClearService;
import service.ServerException;
import service.UserService;

public class UserServiceTest {
    private static DataAccess dataAccess;
    private static GameDao gameDao;
    private static AuthDao authDao;

    @BeforeAll
    public static void beforeAll() {
        dataAccess = new DataAccess();
        gameDao = dataAccess.getGameDao();
        authDao = dataAccess.getAuthDao();
    }

    @BeforeEach
    public void beforeEach() throws ServerException {
        new ClearService(dataAccess).clear();
    }

    @Test
    public void goodRegister() throws DataAccessException {
        UserData existingUser = new UserData("existingUser", "existingPassword", "existing@byu.edu");

        RegisterResponse response = new UserService(dataAccess).register(existingUser);
        String authToken = response.authData().authToken();
        Assertions.assertNotNull(authToken);
        Assertions.assertEquals(existingUser.username(), response.authData().username());
    }

    @Test
    public void badRegister() throws DataAccessException {
        UserData user1 = new UserData("username1", "userPassword1", "email1@byu.edu");
        new UserService(dataAccess).register(user1);
        RegisterResponse response = new UserService(dataAccess).register(user1);

        Assertions.assertNotNull(response.message());
    }

}
