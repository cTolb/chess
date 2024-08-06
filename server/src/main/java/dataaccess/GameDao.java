package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDao {

    void clear() throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    Collection<GameData> getAllGames() throws DataAccessException;

    GameData addGame(GameData game) throws DataAccessException;

    void updateGames(GameData game) throws DataAccessException;
}
