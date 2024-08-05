package handler;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import service.ServerException;
import spark.Request;
import spark.Response;
import spark.Route;

import java.net.HttpURLConnection;

public abstract class RequestHandler<T> implements Route {
    private final DataAccess dataAccess;

    public RequestHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws ServerException {
        Gson serializer = new Gson();
        String authToken = request.headers("Authorization");

        T requestObject = null;
        Class<T> requestClass = getRequestClass();
        if (requestClass != null) {
            requestObject = serializer.fromJson(request.body(), requestClass);
        }

        Object result = getResult(dataAccess, requestObject, authToken);
        response.status(HttpURLConnection.HTTP_OK);

        return serializer.toJson(result);
    }

    protected abstract Object getResult(DataAccess dataAccess, T request, String authToken) throws ServerException;

    protected abstract Class<T> getRequestClass();
}
