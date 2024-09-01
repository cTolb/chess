package client;

import model.UserData;
import org.junit.jupiter.api.*;
import responses.ClearResponse;
import responses.RegisterResponse;
import server.Server;

public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(8080);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    public void clear() throws Exception{
        facade.clear();
    }

    @Test
    public void goodRegister() throws Exception{
        RegisterResponse response = facade.register(new UserData("username", "password", "email"));

        Assertions.assertNull(response.message());
    }

    @Test
    public void badRegister() throws Exception{
        facade.register(new UserData("username", "password", "email"));

        Assertions.assertThrows(Exception.class, () -> facade.register(new UserData("username", "password", "email")));

    }

}
