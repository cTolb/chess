package server;

import dataaccess.DataAccess;
import handlers.*;
//import service.RequestException;
import service.ServerException;
//import service.TakenException;
//import service.UnauthorizedException;
import spark.*;

import java.net.HttpURLConnection;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        DataAccess data = new DataAccess();

        //Register your endpoints and handle exceptions here.
        Spark.post("/user", new RegisterHandler(data));
        Spark.delete("/db", new ClearHandler(data));
        Spark.post("/session", new LoginHandler(data));
        Spark.delete("/session", new LogoutHandler(data));
        Spark.post("/game", new CreateHandler(data));
        Spark.put("/game", new JoinGameHandler(data));
        Spark.get("/game", new ListGamesHandler(data));
        //Spark.exception(ServerException.class, this::exceptions);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        //Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    /*public void exceptions(ServerException ex, Request req, Response res) {
        res.status(ex.Code());
        Spark.exception(RequestException.class, new ServerExceptionHandler<>(HttpURLConnection.HTTP_BAD_REQUEST));
        Spark.exception(TakenException.class, new ServerExceptionHandler<>(HttpURLConnection.HTTP_FORBIDDEN));
        Spark.exception(ServerException.class, new ServerExceptionHandler<>(HttpURLConnection.HTTP_INTERNAL_ERROR));
        Spark.exception(UnauthorizedException.class, new ServerExceptionHandler<>(HttpURLConnection.HTTP_UNAUTHORIZED));
    }*/

}
