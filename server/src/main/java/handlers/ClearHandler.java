package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import responses.LogoutResponse;
import service.ClearService;
import service.ServerException;
import service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;


public class ClearHandler implements Route {
    private final DataAccess dataAccess;

    public ClearHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws ServerException {
        Gson gson = new Gson();

        new ClearService(dataAccess).clear();
        response.status(200);

        return gson.toJson(null);
    }
}
