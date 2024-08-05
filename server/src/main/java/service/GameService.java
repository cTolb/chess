package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public class GameService {

    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public GameData createGame(String authToken, String gameName) throws ServerException{
        try {
            boolean current = isCurrentUser(authToken);
            if (!current) {
                throw new ServerException("Error: unauthorized");
            }
            if (gameName == null) {
                throw new ServerException("Game name can not be null");
            }
            GameData game = new GameData(1, null, null, gameName, new ChessGame());
            game = dataAccess.getGameDao().addGameToDB(game);
            return game;
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }

    }

    private boolean isCurrentUser(String authToken) {
        AuthData find = dataAccess.getAuthDao().getAuth(authToken);

        if (find != null) {
            return true;
        }
        return false;
    }

    public Collection<GameData> listGames(String authToken) throws ServerException {
            boolean current = isCurrentUser(authToken);
            if (!current) {
                throw new ServerException("Error: unauthorized");
            }
            return dataAccess.getGameDao().listGames();

    }
}
