package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import responses.ListGameResponse;
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

        ListGameResponse resultObject = new GameService(dataAccess).listGames(authToken);

        if (resultObject.message() == null) {
            response.status(200);
        }
        else if (resultObject.message().equals("Error: unauthorized")){
            response.status(401);
        }
        else {
            response.status(500);
        }

        return gson.toJson(resultObject);
    }
}
