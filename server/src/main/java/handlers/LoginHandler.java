package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import responses.LoginResponse;
import model.UserData;
import service.ServerException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LoginHandler implements Route {
    private final DataAccess dataAccess;
    public LoginHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    @Override
    public Object handle(Request request, Response response) throws ServerException {
        Gson gson = new Gson();

        UserData requestObject = gson.fromJson(request.body(), UserData.class);

        LoginResponse responseObject = new UserService(dataAccess).loginUser(requestObject);
        AuthData authData;
        if (responseObject.message() == null) {
            response.status(200);
        }
        else if (responseObject.message().equals("Error: username is incorrect")
                || responseObject.message().equals("Error: Wrong Password")) {
            response.status(401);
            return gson.toJson(responseObject);
        }
        else {
            response.status(500);
            return gson.toJson(responseObject);
        }

        authData = responseObject.authData();

        return gson.toJson(authData);

    }
}
