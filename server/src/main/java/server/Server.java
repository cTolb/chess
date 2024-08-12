package server;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.memory.MemoryDataAccess;
import dataaccess.sql.SQLDataAccess;
import handlers.*;
import spark.*;

import javax.xml.crypto.Data;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        DataAccess data;
        try {
            data = new SQLDataAccess();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        //data = new MemoryDataAccess();

        //Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler(data));
        Spark.delete("/db", new ClearHandler(data));
        Spark.post("/session", new LoginHandler(data));
        Spark.delete("/session", new LogoutHandler(data));
        Spark.post("/game", new CreateHandler(data));
        Spark.put("/game", new JoinGameHandler(data));
        Spark.get("/game", new ListGamesHandler(data));

        //This line initializes the server and can be removed once you have a functioning endpoint
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

}
