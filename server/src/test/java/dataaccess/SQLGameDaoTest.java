package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.sql.SQLDataAccess;
import model.GameData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import spark.utils.Assert;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

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
    public void badAdd() {
        Assertions.assertThrows(DataAccessException.class, () -> gameDao.addGame(badgame));
    }

    @Test
    public void goodGet() throws DataAccessException {
        int addedID = gameDao.addGame(game1);

        GameData found = gameDao.getGame(addedID);
        Assertions.assertNotNull(found);
    }

    @Test
    public void badGet() throws DataAccessException {
        int addedID = gameDao.addGame(game2);

        Assertions.assertNull(gameDao.getGame(addedID + 1));
    }

    @Test
    public void goodGetAll() throws DataAccessException {
        Collection<GameData> games = new HashSet<>();
        gameDao.addGame(game1);
        gameDao.addGame(game2);

        games = gameDao.getAllGames();

        Assertions.assertEquals(2, games.size());
    }

    @Test
    public void badGetAll() throws DataAccessException {
        Collection<GameData> games = gameDao.getAllGames();
        Assertions.assertEquals(0, games.size());
    }

    @Test
    public void goodUpdate() throws DataAccessException {
        int addedID = gameDao.addGame(game2);
        GameData update = new GameData(addedID, "newPlayer1", "newPlayer2", game2.gameName(), game2.game());
        gameDao.updateGames(update);
        GameData found = gameDao.getGame(addedID);
        Assertions.assertEquals(addedID, found.gameID());
        Assertions.assertEquals(update.whiteUsername(), found.whiteUsername());
        Assertions.assertEquals(update.blackUsername(), found.blackUsername());
        Assertions.assertEquals(update.gameName(), found.gameName());
        Assertions.assertEquals(new Gson().toJson(update.game()), new Gson().toJson(found.game()));
    }

    @Test
    public void badUpdate() throws DataAccessException {
        int addedID = gameDao.addGame(game2);
        GameData update = new GameData(222, "newPlayer1", "newPlayer2", game2.gameName(), game2.game());
        Assertions.assertThrows(DataAccessException.class, () ->gameDao.updateGames(update));
    }
}
