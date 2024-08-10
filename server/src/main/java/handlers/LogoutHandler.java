package handlers;

import com.google.gson.Gson;
import dataaccess.memory.MemoryDataAccess;
import responses.LogoutResponse;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

public class LogoutHandler implements Route {
    private final MemoryDataAccess memoryDataAccess;
    public LogoutHandler(MemoryDataAccess memoryDataAccess) {
        this.memoryDataAccess = memoryDataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson gson = new Gson();
        String authToken = request.headers("authorization");


        LogoutResponse responseObject = new UserService(memoryDataAccess).logoutUser(authToken);
        if (responseObject.message() == null) {
            response.status(200);
        }
        else if (responseObject.message().equals("Error: unauthorized")) {
            response.status(401);
            return gson.toJson(responseObject);
        }
        else {
            response.status(500);
            return gson.toJson((responseObject));
        }

        return gson.toJson(null);
    }
}
