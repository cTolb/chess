package service;

import chess.ChessGame;
import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.JoinGameRequest;
import responses.CreateGameResponse;
import responses.JoinGameResponse;
import responses.ListGameResponse;
import responses.RegisterResponse;

public class GameServiceTest {
    private static MemoryDataAccess memoryDataAccess;
    @BeforeAll
    public static void beforeAll() {
        memoryDataAccess = new MemoryDataAccess();
    }

    @BeforeEach
    public void beforeEach() throws DataAccessException {
        new ClearService(memoryDataAccess).clear();
    }

    @Test
    public void goodCreate() {
        //Create games and user
        GameData newGame = new GameData(1, null, null, "newGame1", new ChessGame());
        UserData newUser = new UserData("username", "password", "email@email.edu");

        //Register and create game
        RegisterResponse registerResponse = new UserService(memoryDataAccess).register(newUser);
        CreateGameResponse createResponse = new GameService(memoryDataAccess).createGame(newGame, registerResponse.authToken());

        Assertions.assertNull(createResponse.message());
    }

    @Test
    public void badCreate() {
        //Create user and game data
        GameData newGame = new GameData(1, null, null, "newGame1", new ChessGame());

        //Create game without a authToken
        CreateGameResponse createResponse = new GameService(memoryDataAccess).createGame(newGame, "authToken");

        String message = createResponse.message();
        Assertions.assertEquals(message, "Error: unauthorized");
    }

    @Test
    public void goodJoin() {
        //Create games and user
        GameData newGame = new GameData(1, null, null, "newGame1", new ChessGame());
        UserData newUser = new UserData("username", "password", "email@email.edu");

        //Register and create game
        RegisterResponse registerResponse = new UserService(memoryDataAccess).register(newUser);
        CreateGameResponse createResponse = new GameService(memoryDataAccess).createGame(newGame, registerResponse.authToken());

        //Join game
        String authToken = registerResponse.authToken();
        JoinGameRequest newRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, createResponse.gameID());
        JoinGameResponse joinGameResponse = new GameService(memoryDataAccess).joinGame(newRequest, authToken);

        Assertions.assertNull(joinGameResponse);
    }

    @Test
    public void badJoin() {
        //Create games and user
        GameData newGame = new GameData(1, null, null, "newGame1", new ChessGame());
        UserData newUser = new UserData("username", "password", "email@email.edu");

        //Register and create game
        RegisterResponse registerResponse = new UserService(memoryDataAccess).register(newUser);
        CreateGameResponse createResponse = new GameService(memoryDataAccess).createGame(newGame, registerResponse.authToken());

        //Join game
        JoinGameRequest newRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, createResponse.gameID());
        JoinGameResponse joinGameResponse = new GameService(memoryDataAccess).joinGame(newRequest, "authToken");

        Assertions.assertEquals(joinGameResponse.message(), "Error: unauthorized");
    }

    @Test
    public void goodList() {
        //Create games and user
        GameData newGame = new GameData(1, null, null, "newGame1", new ChessGame());
        UserData newUser = new UserData("username", "password", "email@email.edu");

        //Register and create game
        RegisterResponse registerResponse = new UserService(memoryDataAccess).register(newUser);
        String authToken = registerResponse.authToken();
        new GameService(memoryDataAccess).createGame(newGame, registerResponse.authToken());
        ListGameResponse listGameResponse = new GameService(memoryDataAccess).listGames(authToken);

        Assertions.assertNull(listGameResponse.message());
    }

    @Test
    public void badList() {
        //Create games and user
        GameData newGame = new GameData(1, null, null, "newGame1", new ChessGame());
        UserData newUser = new UserData("username", "password", "email@email.edu");

        //Register and create game
        RegisterResponse registerResponse = new UserService(memoryDataAccess).register(newUser);
        new GameService(memoryDataAccess).createGame(newGame, registerResponse.authToken());
        ListGameResponse listGameResponse = new GameService(memoryDataAccess).listGames("authToken");

        Assertions.assertEquals(listGameResponse.message(), "Error: unauthorized");
    }


}
