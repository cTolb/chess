package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import model.AuthData;
import responses.RegisterResponse;
import model.UserData;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class RegisterHandler implements Route {
    private final DataAccess dataAccess;

    public RegisterHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();

        UserData requestObject = gson.fromJson(request.body(), UserData.class);
        RegisterResponse responseObject = new UserService(dataAccess).register(requestObject);
        //System.out.println(responseObject.toString());

        AuthData authData;
        if (responseObject.message() == null) {
            response.status(200);
        }
        else if (responseObject.message().equals("Error: UserData can not be null")) {
            response.status(400);
            return gson.toJson(responseObject);
        }
        else if (responseObject.message().equals("Error: username is already taken")) {
            response.status(403);
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
