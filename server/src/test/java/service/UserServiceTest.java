package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import responses.LoginResponse;
import responses.LogoutResponse;
import responses.RegisterResponse;
import model.UserData;
import org.junit.jupiter.api.*;

public class UserServiceTest {
    private static DataAccess dataAccess;

    @BeforeAll
    public static void beforeAll() {
        dataAccess = new DataAccess();
    }

    @BeforeEach
    public void beforeEach() throws DataAccessException {
        new ClearService(dataAccess).clear();
    }

    @Test
    public void goodRegister() {
        //Create and register a new user
        UserData existingUser = new UserData("existingUser", "existingPassword", "existing@byu.edu");
        RegisterResponse response = new UserService(dataAccess).register(existingUser);

        //Check that response from service matches what was submitted
        String authToken = response.authData().authToken();
        Assertions.assertNotNull(authToken);
        Assertions.assertEquals(existingUser.username(), response.authData().username());
    }

    @Test
    public void badRegister() {
        //register a user
        UserData user1 = new UserData("username1", "userPassword1", "email1@byu.edu");
        new UserService(dataAccess).register(user1);

        //Attempt to re-register a user
        RegisterResponse response = new UserService(dataAccess).register(user1);

        Assertions.assertNotNull(response.message());
    }

    @Test
    public void goodLogin() {
        //create and register a new user
        UserData user = new UserData("specialUsername*", "specialPassword*", "special@email.com");
        new UserService(dataAccess).register(user);

        //log in the registered user
        LoginResponse response = new UserService(dataAccess).loginUser(user);
        Assertions.assertNotNull(response.authData().authToken());
        Assertions.assertEquals(response.authData().username(), user.username());
    }

    @Test
    public void badLogin() {
        //Register a good user
        UserData goodUser = new UserData("specialUsername*", "specialPassword*", "special@email.com");
        new UserService(dataAccess).register(goodUser);

        //Try to login a user with a different username than is registered
        UserData badUser = new UserData("specialUsername", "specialPassword*", "special@email.com");
        LoginResponse response = new UserService(dataAccess).loginUser(badUser);

        Assertions.assertNotNull(response.message());
    }

    @Test
    public void goodLogout() {
        //create and register a new user
        UserData user = new UserData("specialUsername*", "specialPassword*", "special@email.com");
        new UserService(dataAccess).register(user);

        //log in the registered user
        LoginResponse loginResponse = new UserService(dataAccess).loginUser(user);
        String authToken = loginResponse.authData().authToken();

        //Logout user
        LogoutResponse logoutResponse = new UserService(dataAccess).logoutUser(authToken);

        Assertions.assertNull(logoutResponse.message());
    }

    @Test
    public void badLogout() {
        //create and register a new user
        UserData user = new UserData("specialUsername*", "specialPassword*", "special@email.com");
        new UserService(dataAccess).register(user);

        //log in the registered user
        LoginResponse loginResponse = new UserService(dataAccess).loginUser(user);
        String authToken = loginResponse.authData().authToken();

        //Logout User with wrong authToken
        LogoutResponse logoutResponse = new UserService(dataAccess).logoutUser("authToken");

        Assertions.assertNotNull(logoutResponse.message());
    }
}
