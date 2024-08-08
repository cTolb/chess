package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.UserData;
import service.ServerException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public class RegisterHandler implements Route {
    private final DataAccess dataAccess;

    public RegisterHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    @Override
    public Object handle(Request request, Response response) throws ServerException {
        Gson gson = new Gson();

        UserData requestObject = gson.fromJson(request.body(), UserData.class);
        Object responseObject = new UserService(dataAccess).register(requestObject);

        response.status(HttpURLConnection.HTTP_OK);

        return gson.toJson(responseObject);
    }
}
