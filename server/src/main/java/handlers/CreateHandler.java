package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.GameData;
import responses.CreateGameResponse;
import service.GameService;
import spark.Request;
import spark.Response;
import spark.Route;


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

        CreateGameResponse responseObject = new GameService(dataAccess).createGame(requestObject, authToken);

        if (responseObject.message() == null) {
            response.status(200);
            //GameData game = responseObject.gameID();
            return gson.toJson(responseObject);
        }
        else if (responseObject.message().equals("Error: bad request")) {
            response.status(400);
            return gson.toJson(responseObject);
        }
        else if (responseObject.message().equals("Error: unauthorized")){
            response.status(401);
            return gson.toJson(responseObject);
        }
        else {
            response.status(500);
            return gson.toJson(responseObject);
        }
    }
}
