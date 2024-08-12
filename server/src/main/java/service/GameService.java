package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGameResponse;

public class GameService {
    private final DataAccess dataAccess;

    public GameService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public CreateGameResponse createGame(GameData request, String authToken) {
        try {
            if (!isValidAuth(authToken)) {
                return new CreateGameResponse(null, "Error: unauthorized");
            }

            if (request.gameName() == null) {
                return new CreateGameResponse(null, "Error: bad request");
            }
            GameData addGame = new GameData(0, null, null, request.gameName(), new ChessGame());
            int newID = dataAccess.getGameDao().addGame(addGame);

            GameData game = dataAccess.getGameDao().getGame(newID);
            return new CreateGameResponse(game.gameID(), null);
        } catch (DataAccessException e) {
            return new CreateGameResponse(null, e.getMessage());
        }
    }

    public JoinGameResponse joinGame(JoinGameRequest request, String authToken) {
        try {
            if (request.playerColor() == null) {
                return new JoinGameResponse("Error: bad request");
            }

            GameData gameData = dataAccess.getGameDao().getGame(request.gameID());
            if (gameData == null) {
                return new JoinGameResponse("Error: bad request");
            }
            AuthData authData = dataAccess.getAuthDao().getAuthorization(authToken);
            if (authData == null) {
                return new JoinGameResponse("Error: unauthorized");
            }

            ChessGame.TeamColor requestTeamColor = request.playerColor();
            if (isColorAvailable(requestTeamColor, gameData, authData)) {
                return new JoinGameResponse("Error: color already taken");
            }

            if (requestTeamColor == ChessGame.TeamColor.WHITE) {
                dataAccess.getGameDao().updateGames(gameData.setWhiteUsername(authData.username()));
            }
            if (requestTeamColor == ChessGame.TeamColor.BLACK) {
                dataAccess.getGameDao().updateGames(gameData.setBlackUsername(authData.username()));
            }

        } catch (DataAccessException e) {
            return new JoinGameResponse(e.getMessage());
        }

        return null;
    }

    public ListGameResponse listGames(String authToken) {
        try {
            if (!isValidAuth(authToken)) {
                return new ListGameResponse(null, "Error: unauthorized");
            }
            else {
                return new ListGameResponse(dataAccess.getGameDao().getAllGames(), null);
            }
        } catch (DataAccessException e) {
            return new ListGameResponse(null, e.getMessage());
        }
        //return null;
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

    private boolean isValidAuth(String authToken) throws DataAccessException {
        AuthData authData = dataAccess.getAuthDao().getAuthorization(authToken);
        return authData != null;
    }
}
