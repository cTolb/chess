package server;

import dataaccess.DataAccess;
import dataaccess.memory.MemDataAccess;
import handler.*;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        DataAccess data = new MemDataAccess();

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler(data));
        Spark.post("/session", new LoginHandler(data));
        Spark.delete("/session", new LogoutHandler(data));
        Spark.get("/game", new ListGamesHandler(data));
        Spark.post("/game", new CreateGameHandler(data));
        Spark.put("/game", new AddGameHandler(data));
        Spark.delete("db", new ClearHandler(data));

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}
