package dataaccess.memory;

import dataaccess.DataAccessException;
import dataaccess.GameDaoInterface;
import model.GameData;
import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDao implements GameDaoInterface {
    private final HashMap<Integer, GameData> games = new HashMap<>();
    private int id = 1000;
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
        return games.values();
    }

    @Override
    public int addGame(GameData game) throws DataAccessException {
        if (game.game() == null) {
            throw new DataAccessException("Game can not be null");
        }
        id++;
        GameData newGame = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(id, newGame);
        return id;
    }

    @Override
    public void updateGames(GameData game) throws DataAccessException {
        if (!games.containsKey(game.gameID())) {
            throw new DataAccessException("Error: Game not found");
        }
        if (game.game() == null) {
            throw new DataAccessException("Error: Game can not be null");
        }
        games.remove(game.gameID());
        games.put(game.gameID(), game);
    }
}
