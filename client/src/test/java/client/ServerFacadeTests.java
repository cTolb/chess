package client;

import chess.ChessGame;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import requests.JoinGameRequest;
import requests.LoginRequest;
import responses.*;
import server.Server;

public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    private final UserData userData1 = new UserData("username", "password", "email");
    private final UserData userData2 = new UserData("anotherUsername", "anotherPassword", "anotherEmail");
    private final GameData gameData = new GameData(0, null, null, "gameName", new ChessGame());

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clear() throws Exception {
        facade.clear();
    }

    @Test
    public void goodRegister() throws Exception {
        RegisterResponse response = facade.register(userData1);

        Assertions.assertNull(response.message());
    }

    @Test
    public void badRegister() throws Exception {
        facade.register(userData1);

        Assertions.assertThrows(Exception.class, () -> facade.register(userData1));
    }

    @Test
    public void goodLogin() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);
        facade.logout(registerResponse.authToken());

        LoginResponse loginResponse = facade.login(new LoginRequest("username", "password"));

        Assertions.assertNull(loginResponse.message());
    }

    @Test
    public void badLogin() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);
        facade.logout(registerResponse.authToken());

        Assertions.assertThrows(Exception.class, () -> facade.login(new LoginRequest("username", "wrongPass")));
    }

    @Test
    public void goodLogout() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);
        LogoutResponse logoutResponse = facade.logout(registerResponse.authToken());

        Assertions.assertNull(logoutResponse);
    }

    @Test
    public void badLogout() throws Exception {
        facade.register(userData1);

        Assertions.assertThrows(Exception.class, () -> facade.logout("thisIsARandomString144$"));
    }

    @Test
    public void goodList() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);
        facade.createGame(registerResponse.authToken(), gameData);

        ListGameResponse listGameResponse = facade.listGame(registerResponse.authToken());
        Assertions.assertNotNull(listGameResponse.games());
        Assertions.assertNull(listGameResponse.message());
    }

    @Test
    public void badList() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);
        facade.createGame(registerResponse.authToken(), gameData);

        Assertions.assertThrows(Exception.class, () ->facade.listGame("thisIsARandomToken144$"));
    }

    @Test
    public void goodJoin() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);

        CreateGameResponse createGameResponse = facade.createGame(registerResponse.authToken(), gameData);
        JoinGameRequest joinGameRequest = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.gameID());

        JoinGameResponse joinGameResponse = facade.joinGame(registerResponse.authToken(), joinGameRequest);

        Assertions.assertNull(joinGameResponse.message());
    }

    @Test
    public void badJoin() throws Exception {
        RegisterResponse registerResponse1 = facade.register(userData1);
        RegisterResponse registerResponse2 = facade.register(userData2);

        CreateGameResponse createGameResponse = facade.createGame(registerResponse1.authToken(), gameData);

        JoinGameRequest joinGameRequest1 = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.gameID());
        JoinGameRequest joinGameRequest2 = new JoinGameRequest(ChessGame.TeamColor.WHITE, createGameResponse.gameID());

        facade.joinGame(registerResponse1.authToken(), joinGameRequest1);

        Assertions.assertThrows(Exception.class, () -> facade.joinGame(registerResponse2.authToken(), joinGameRequest2));

    }

    @Test
    public void goodCreate() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);

        CreateGameResponse createGameResponse = facade.createGame(registerResponse.authToken(), gameData);

        Assertions.assertNull(createGameResponse.message());
    }

    @Test
    public void badCreate() throws Exception {
        RegisterResponse registerResponse = facade.register(userData1);

        Assertions.assertThrows(Exception.class, () -> facade.createGame("aRandomAuthToken144$", gameData));
    }

}
