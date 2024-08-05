package dataaccess.memory;

import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.GameDao;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class MemGameDao implements GameDao {
    private final HashMap<Integer, GameData> games = new HashMap<>();
    private int id = 300;

    @Override
    public void clear() throws DataAccessException {
        games.clear();
    }

    @Override
    public GameData addGameToDB(GameData game) throws DataAccessException{
        if (game == null) {
            throw new DataAccessException("Game is null");
        }
        id++;
        game = new GameData(id, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(game.gameID(), game);
        return game;
    }

    @Override
    public Collection<GameData> listGames(){
        return Collections.unmodifiableCollection(games.values());
    }
}
