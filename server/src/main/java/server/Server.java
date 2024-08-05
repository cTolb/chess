package server;

import server.handlers.ClearHandler;
import server.handlers.RegisterHandler;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        //DataAccess data = new MemDataAccess();

        //Register your endpoints and handle exceptions here.
        Spark.delete("db", new ClearHandler());
        Spark.post("/user", new RegisterHandler());
        /*Spark.post("/session", new LoginHandler(data));
        Spark.delete("/session", new LogoutHandler(data));
        Spark.get("/game", new ListGamesHandler(data));
        Spark.post("/game", new CreateGameHandler(data));
        Spark.put("/game", new JoinGameHandler(data));*/


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
