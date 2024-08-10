package handlers;

import com.google.gson.Gson;
import dataaccess.memory.MemoryDataAccess;
import requests.JoinGameRequest;
import responses.JoinGameResponse;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

public class JoinGameHandler implements Route {
    private final MemoryDataAccess memoryDataAccess;
    public JoinGameHandler(MemoryDataAccess memoryDataAccess) {
        this.memoryDataAccess = memoryDataAccess;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String authToken = request.headers("authorization");

        JoinGameRequest requestObject = gson.fromJson(request.body(), JoinGameRequest.class);
        JoinGameResponse resultObject = new GameService(memoryDataAccess).joinGame(requestObject, authToken);
        if (resultObject == null) {
            response.status(200);
        }
        else if (resultObject.message().equals("Error: bad request")) {
            response.status(400);
        }
        else if (resultObject.message().equals("Error: unauthorized")) {
            response.status(401);
        }
        else if (resultObject.message().equals("Error: color already taken")) {
            response.status(403);
        }
        else {
            response.status(500);
        }
        return gson.toJson(resultObject);
    }

}
