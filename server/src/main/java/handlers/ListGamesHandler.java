package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public class ListGamesHandler implements Route {

    private final DataAccess dataAccess;
    public ListGamesHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String authToken = request.headers("authorization");

        Object resultObject = new GameService(dataAccess).listGames(authToken);
        response.status(HttpURLConnection.HTTP_OK);

        return gson.toJson(resultObject);
    }
}
