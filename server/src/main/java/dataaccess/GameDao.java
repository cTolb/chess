package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public interface GameDao {

    void clear() throws DataAccessException;

    GameData addGameToDB(GameData game) throws DataAccessException;
    Collection<GameData> listGames();
}
