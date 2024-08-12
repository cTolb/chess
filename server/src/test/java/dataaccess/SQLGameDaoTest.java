package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.sql.SQLDataAccess;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

public class SQLGameDaoTest {
    private static GameDaoInterface gameDao;
    private final GameData game1 = new GameData(0, null, null, "randomName", new ChessGame());
    private final GameData game2 = new GameData(0, null, null, "secondGame", new ChessGame());
    private final GameData badgame = new GameData(0, null, null, null, new ChessGame());

    @BeforeAll
    static void beforeAll() throws DataAccessException {
        gameDao = new SQLDataAccess().getGameDao();
        gameDao.clear();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        gameDao.clear();
    }

    @Test
    public void clearAll() throws DataAccessException {
        gameDao.addGame(game1);
        gameDao.addGame(game2);

        gameDao.clear();

        Assertions.assertNull(gameDao.getGame(game1.gameID()));
        Assertions.assertNull(gameDao.getGame(game2.gameID()));
    }

    @Test
    public void goodAdd() throws DataAccessException{
        int addedID = gameDao.addGame(game1);

        GameData addedGame = new GameData(addedID, game1.whiteUsername(), game1.blackUsername(), game1.gameName(), game1.game());

        GameData found = gameDao.getGame(addedID);

        Assertions.assertEquals(addedID, found.gameID());
        Assertions.assertEquals(addedGame.gameName(), found.gameName());
        Assertions.assertEquals(addedGame.whiteUsername(), found.whiteUsername());
        Assertions.assertEquals(addedGame.blackUsername(), found.blackUsername());
        Assertions.assertEquals(new Gson().toJson(addedGame.game()), new Gson().toJson(found.game()));
    }

    @Test
    public void badAdd() throws DataAccessException {
        Assertions.assertThrows(DataAccessException.class, () -> gameDao.addGame(badgame));
    }
}
