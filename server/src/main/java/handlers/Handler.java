package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public abstract class Handler<T> implements Route {
    private final DataAccess dataAccess;

    public Handler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();
        String token = request.headers("Authorization");

        T requestObject = null;
        Class<T> requestClass = getRequestClass();
        if (requestClass != null) {
            requestObject = gson.fromJson(request.body(), requestClass);
        }
        Object res = getServiceResponse(dataAccess, requestObject, token);
        response.status(HttpURLConnection.HTTP_OK);

        return gson.toJson(res);
    }

    protected abstract Class<T> getRequestClass();

    protected abstract Object getServiceResponse(DataAccess dataAccess, T request, String token) throws DataAccessException;
}
