package service;

import chess.ChessGame;
import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.ClearResponse;

public class ClearServerTest {
    private static MemoryDataAccess memoryDataAccess;

    @BeforeAll
    public static void beforeAll(){
        memoryDataAccess = new MemoryDataAccess();
    }

    @BeforeEach
    public void beforeEach() throws DataAccessException {
        new ClearService(memoryDataAccess).clear();
    }

    @Test
    public void clearTest() throws DataAccessException {
        UserData userData = new UserData("username", "password", "email@email.com");
        GameData gameData = new GameData(0, null, null, "game1", new ChessGame());
        AuthData authData = new AuthData("authToken", userData.username());

        memoryDataAccess.getGameDao().addGame(gameData);
        memoryDataAccess.getUserDao().addUser(userData);
        memoryDataAccess.getAuthDao().addAuth(authData);

        ClearResponse clearResponse = new ClearService(memoryDataAccess).clear();

        Assertions.assertNull(clearResponse.message());
    }
}
