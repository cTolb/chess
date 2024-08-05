package server;

import dataaccess.DataAccess;
import dataaccess.memory.MemDataAccess;
import handler.RegisterHandler;
import passoff.exception.ResponseParseException;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        DataAccess data = new MemDataAccess();

        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler(data));
        /*Spark.post("/session", logInUser);
        Spark.delete("/session", this::logOutUser);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::addGame);
        Spark.delete("db", this::clearAll);*/

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
