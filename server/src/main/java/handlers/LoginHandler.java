package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.UserData;
import service.exceptions.ServerException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public class LoginHandler implements Route {
    private final DataAccess dataAccess;
    public LoginHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    @Override
    public Object handle(Request request, Response response) throws ServerException {
        Gson gson = new Gson();

        UserData requestObject = gson.fromJson(request.body(), UserData.class);

        Object resultObject = new UserService(dataAccess).loginUser(requestObject);
        response.status(HttpURLConnection.HTTP_OK);

        return gson.toJson(resultObject);
    }
}
