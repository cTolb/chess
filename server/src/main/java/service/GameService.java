package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.JoinGameRequest;
import model.ListGameResponse;

public class GameService {
    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public GameData createGame(GameData request, String authToken) throws ServerException {
        try {
            checkAuth(authToken);

            if (request.gameName() == null) {
                throw new ServerException("Error: game name can not be null");
            }
            GameData addGame = new GameData(0, null, null, request.gameName(), new ChessGame());
            addGame = dataAccess.getGameDao().addGame(addGame);

            return addGame;
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }

    public Object joinGame(JoinGameRequest request, String authToken) throws ServerException{
        try {
            if (request.playerColor() == null) {
                throw new ServerException("Error: player color can not be null");
            }

            GameData gameData = dataAccess.getGameDao().getGame(request.gameID());
            if (gameData == null) {
                throw new ServerException("Error: bad request");
            }

            AuthData authData = checkAuth(authToken);
            ChessGame.TeamColor requestTeamColor = request.playerColor();

            if (isColorAvailable(requestTeamColor, gameData, authData)) {
                throw new ServerException("Error: color already taken");
            }

            if (requestTeamColor == ChessGame.TeamColor.WHITE) {
                gameData = new GameData(gameData.gameID(), authData.username(), gameData.blackUsername(), gameData.gameName(), gameData.game());
            }
            if (requestTeamColor == ChessGame.TeamColor.BLACK) {
                gameData = new GameData(gameData.gameID(), gameData.whiteUsername(), authData.username(), gameData.gameName(), gameData.game());
            }

            dataAccess.getGameDao().updateGames(gameData);

        } catch (DataAccessException e) {
            throw new ServerException(e);
        }

        return null;

    }

    public ListGameResponse listGames(String authToken) throws ServerException {
        try {
            checkAuth(authToken);

            return new ListGameResponse(dataAccess.getGameDao().getAllGames());

        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }

    private boolean isColorAvailable(ChessGame.TeamColor reqColor, GameData gameData, AuthData authData) {
        String authUsername = authData.username();
        if (reqColor == ChessGame.TeamColor.WHITE) {
            return gameData.whiteUsername() != null && !gameData.whiteUsername().equals(authUsername);
        }
        if (reqColor == ChessGame.TeamColor.BLACK) {
            return gameData.blackUsername() != null && !gameData.blackUsername().equals(authUsername);
        }

        return false;
    }

    private AuthData checkAuth(String authToken) throws ServerException {
        try {
            AuthData authData = dataAccess.getAuthDao().getAuthorization(authToken);
            if (authData == null) {
                throw new ServerException("Error: unauthorized");
            }
            return authData;
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }
}
