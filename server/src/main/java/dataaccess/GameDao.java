package dataaccess;

import model.GameData;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class GameDao {
    private final HashMap<Integer, GameData> games = new HashMap<>();
    public void clear() throws DataAccessException {
        games.clear();
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    public Collection<GameData> getAllGames() throws DataAccessException {
        return Collections.unmodifiableCollection(games.values());
    }

    public GameData addGame(GameData game) throws DataAccessException {
        if (game.game() == null) {
            throw new DataAccessException("Game can not be null");
        }
        int id = games.size() * 2;
        id++;
        GameData newGame = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(id, newGame);
        return newGame;
    }

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
