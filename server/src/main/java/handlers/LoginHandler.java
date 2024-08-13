package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.memory.MemoryDataAccess;
import model.AuthData;
import responses.LoginResponse;
import model.UserData;
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
    public Object handle(Request request, Response response) throws Exception{
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
        String authToken = responseObject.authToken();
        String userName = responseObject.username();
        authData = new AuthData(authToken, userName);

        return gson.toJson(authData);

    }
}
