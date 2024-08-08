package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.GameData;
import service.GameService;
import service.exceptions.ServerException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public class CreateHandler implements Route {
    private final DataAccess dataAccess;

    public CreateHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String authToken = request.headers("authorization");

        GameData requestObject = gson.fromJson(request.body(), GameData.class);

        Object responseObject = new GameService(dataAccess).createGame(requestObject, authToken);
        response.status(HttpURLConnection.HTTP_OK);

        return gson.toJson(responseObject);
    }
}
