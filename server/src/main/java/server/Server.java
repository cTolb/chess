package server;

import passoff.exception.ResponseParseException;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::registerUser);
        Spark.post("/session", this::logInUser);
        Spark.delete("/session", this::logOutUser);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::addGame);
        Spark.delete("db", this::clearAll);

        //This line initializes the server and can be removed once you have a functioning endpoint 
        Spark.init();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private Object registerUser(Request req, Response rep) throws ResponseParseException {
        return null;
    }
    private Object logInUser(Request request, Response response) throws ResponseParseException{
        return null;
    }
    private Object logOutUser(Request request, Response response) throws ResponseParseException{
        return null;
    }
    private Object listGames(Request request, Response response) throws ResponseParseException{
        return null;
    }
    private Object createGame(Request request, Response response) throws ResponseParseException{
        return null;
    }
    private Object addGame(Request request, Response response) throws ResponseParseException{
        return null;
    }
    private Object clearAll(Request request, Response response) throws ResponseParseException{
        return "";
    }
}
