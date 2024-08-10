package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class GameServiceTest {
    private static DataAccess dataAccess;
    @BeforeAll
    public static void beforeAll() {
        dataAccess = new DataAccess();
    }

    @BeforeEach
    public void beforeEach() throws DataAccessException {
        new ClearService(dataAccess).clear();
    }
}
