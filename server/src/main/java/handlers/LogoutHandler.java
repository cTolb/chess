package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public class LogoutHandler implements Route {
    private final DataAccess dataAccess;
    public LogoutHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String authToken = request.headers("authorization");

        new UserService(dataAccess).logoutUser(authToken);
        response.status(HttpURLConnection.HTTP_OK);

        return gson.toJson(null);
    }
}
