package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.GameData;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class MemGameDao implements GameDao {
    HashMap<Integer, GameData> games = new HashMap<>();
    int id = 300;
    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    @Override
    public Collection<GameData> getAllGames() throws DataAccessException {
        return Collections.unmodifiableCollection(games.values());
    }

    @Override
    public GameData addGame(GameData game) throws DataAccessException {
        if (game == null) {
            throw new DataAccessException("Game can not be null");
        }
        id++;
        GameData newGame = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(id, newGame);
        return newGame;
    }

    @Override
    public void updateGames(GameData game) throws DataAccessException {
        if (!games.containsKey(game.gameID())) {
            throw new DataAccessException("Game not found");
        }
        if (game.game() == null) {
            throw new DataAccessException("Game can not be null");
        }
        games.remove(game.gameID());
        games.put(game.gameID(), game);
    }
}
