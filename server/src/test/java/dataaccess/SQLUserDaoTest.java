package dataaccess;

import dataaccess.sql.SQLDataAccess;
import model.UserData;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;

public class SQLUserDaoTest {

    private static UserDaoInterface userDao;
    private final UserData user1 = new UserData("test1", "test1", "test1@email");
    private final UserData user2 = new UserData("test2", "test2", "test2@email");
    private final UserData user3 = new UserData("test3", "test3", "test3@email");

    @BeforeAll
    public static void beforeAll() throws DataAccessException {
        DataAccess dataAccess = new SQLDataAccess();
        userDao = dataAccess.getUserDao();
        userDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        userDao.clear();
    }

    @Test
    public void clearAll() throws DataAccessException {
        userDao.addUser(user1);
        userDao.addUser(user2);
        userDao.clear();
        Assertions.assertNull(userDao.getUser(user1.username()));
        Assertions.assertNull(userDao.getUser(user2.username()));
    }

    @Test
    public void goodAdd() throws DataAccessException {
        //Add users
        userDao.addUser(user1);
        userDao.addUser(user2);
        userDao.addUser(user3);

        //Check to make sure users were added
        Assertions.assertNotNull(userDao.getUser(user1.username()));
        Assertions.assertNotNull(userDao.getUser(user2.username()));
        Assertions.assertNotNull(userDao.getUser(user3.username()));
    }

    @Test
    void badAdd() throws DataAccessException {
        //Add user
        userDao.addUser(user2);

        //Try to add the same user twice
        Assertions.assertThrows(DataAccessException.class, () -> userDao.addUser(user2));
    }

    @Test
    public void goodGet() throws DataAccessException {
        //Add two users
        userDao.addUser(user1);
        userDao.addUser(user3);

        //Assert that get returns the correct user
        Assertions.assertEquals(user1.username(), userDao.getUser(user1.username()).username());
    }

    @Test
    public void badGet() throws DataAccessException {
        //Add two users
        userDao.addUser(user1);
        userDao.addUser(user3);

        //Try to get user that is not registered
        Assertions.assertNull(userDao.getUser(user2.username()));
    }

    @Test
    public void goodValid() throws DataAccessException {
        //Hash password and add the user to the database with hashed password
        String newPassword = BCrypt.hashpw(user1.password(), BCrypt.gensalt());
        UserData newUser1 = new UserData(user1.username(), newPassword, user1.email());
        userDao.addUser(newUser1);

        //Create request with the original password
        LoginRequest request = new LoginRequest(user1.username(), user1.password());

        Assertions.assertTrue(userDao.valid(request));
    }
    @Test
    public void badValid() throws DataAccessException {
        //Hash password and add the user to the database with hashed password
        String newPassword = BCrypt.hashpw(user1.password(), BCrypt.gensalt());
        UserData newUser1 = new UserData(user1.username(), newPassword, user1.email());
        userDao.addUser(newUser1);

        //Create request with a username that does not match password
        LoginRequest request = new LoginRequest(user2.username(), user1.password());

        Assertions.assertFalse(userDao.valid(request));
    }

    @Test
    public void goodExists() throws DataAccessException {
        //Add user to database
        userDao.addUser(user3);

        Assertions.assertTrue(userDao.userExists(user3.username()));
    }

    @Test
    public void badExists() throws DataAccessException {
        userDao.addUser(user3);

        //Check to see if user exists that is not registers
        Assertions.assertFalse(userDao.userExists(user2.username()));
    }

}
