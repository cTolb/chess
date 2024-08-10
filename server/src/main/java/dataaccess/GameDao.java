package dataaccess;

import model.GameData;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class GameDao {
    private final HashMap<Integer, GameData> games = new HashMap<>();
    private int id = 1000;
    public void clear() throws DataAccessException {
        games.clear();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    public Collection<GameData> getAllGames() throws DataAccessException {
        return games.values();
    }

    public int addGame(GameData game) throws DataAccessException {
        if (game.game() == null) {
            throw new DataAccessException("Game can not be null");
        }
        id++;
        GameData newGame = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(id, newGame);
        return id;
    }

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
