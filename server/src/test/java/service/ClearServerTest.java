package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import responses.ClearResponse;
import responses.RegisterResponse;

public class ClearServerTest {
    private static DataAccess dataAccess;

    @BeforeAll
    public static void beforeAll(){
        dataAccess = new DataAccess();
    }

    @BeforeEach
    public void beforeEach() throws DataAccessException {
        new ClearService(dataAccess).clear();
    }

    @Test
    public void clearTest() throws DataAccessException {
        UserData userData = new UserData("username", "password", "email@email.com");
        GameData gameData = new GameData(0, null, null, "game1", new ChessGame());
        AuthData authData = new AuthData("authToken", userData.username());

        dataAccess.getGameDao().addGame(gameData);
        dataAccess.getUserDao().addUser(userData);
        dataAccess.getAuthDao().addAuth(authData);

        ClearResponse clearResponse = new ClearService(dataAccess).clear();

        Assertions.assertNull(clearResponse.message());
    }
}
