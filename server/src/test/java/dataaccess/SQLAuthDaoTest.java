package dataaccess;

import dataaccess.sql.SQLDataAccess;
import model.AuthData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SQLAuthDaoTest {
    private static AuthDaoInterface authDao;
    private final AuthData authData1 = new AuthData("thisisTotallyrandom44", "thisismYusername");
    private final AuthData authData2 = new AuthData("anotherrandomstring25", "whoamI");
    private final AuthData authData3 = new AuthData("**finalrandomstring", "thisisreallyBoring");

    @BeforeAll
    public static void beforeAll() throws DataAccessException {
        authDao = new SQLDataAccess().getAuthDao();
        authDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        authDao.clear();
    }

    @Test
    public void clearAll() throws DataAccessException {
        authDao.addAuth(authData1);
        authDao.addAuth(authData2);
        authDao.addAuth(authData3);

        authDao.clear();

        Assertions.assertNull(authDao.getAuthorization(authData1.authToken()));
        Assertions.assertNull(authDao.getAuthorization(authData2.authToken()));
        Assertions.assertNull(authDao.getAuthorization(authData3.authToken()));
    }

    @Test
    public void goodAdd() throws DataAccessException {
        //Add auth data to database
        authDao.addAuth(authData1);
        authDao.addAuth(authData2);
        authDao.addAuth(authData3);

        Assertions.assertNotNull(authDao.getAuthorization(authData1.authToken()));
        Assertions.assertNotNull(authDao.getAuthorization(authData2.authToken()));
        Assertions.assertNotNull(authDao.getAuthorization(authData3.authToken()));
    }

    @Test
    public void badAdd() throws DataAccessException {
        authDao.addAuth(authData1);

        //Try to re-add user
        Assertions.assertThrows(DataAccessException.class, () -> authDao.addAuth(authData1));
    }

    @Test
    public void goodGet() throws DataAccessException {
        authDao.addAuth(authData1);
        authDao.addAuth(authData2);

        //check to see it is returning the correct information
        Assertions.assertEquals(authData1.authToken(), authDao.getAuthorization(authData1.authToken()).authToken());
        Assertions.assertEquals(authData1.username(), authDao.getAuthorization(authData1.authToken()).username());

        Assertions.assertEquals(authData2.authToken(), authDao.getAuthorization(authData2.authToken()).authToken());
        Assertions.assertEquals(authData2.username(), authDao.getAuthorization(authData2.authToken()).username());
    }

    @Test
    public void badGet() throws DataAccessException {
        authDao.addAuth(authData1);
        authDao.addAuth(authData3);

        Assertions.assertNull(authDao.getAuthorization(authData2.authToken()));
    }

    @Test
    public void goodDelete() throws DataAccessException {
        //Add auth then delete it
        authDao.addAuth(authData1);
        authDao.deleteAuth(authData1.authToken());

        Assertions.assertNull(authDao.getAuthorization(authData1.authToken()));
    }

    @Test
    public void badDelete() throws DataAccessException {
        authDao.addAuth(authData2);
        authDao.deleteAuth(authData3.authToken());

        Assertions.assertNotNull(authDao.getAuthorization(authData2.authToken()));
    }
}
