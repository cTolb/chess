package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import requests.JoinGameRequest;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public class JoinHandler implements Route {
    private final DataAccess dataAccess;
    public JoinHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String authToken = request.headers("authorization");

        JoinGameRequest requestObject = gson.fromJson(request.body(), JoinGameRequest.class);
        Object resultObject = new GameService(dataAccess).joinGame(requestObject, authToken);
        response.status(HttpURLConnection.HTTP_OK);

        return gson.toJson(resultObject);
    }

}
