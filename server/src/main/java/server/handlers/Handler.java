package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class Handler<T> implements Route {
    private final DataAccess dataAccess;

    public Handler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Gson serializer = new Gson();
        String token = request.headers("authorization");

        T reqObj = null;
        Class<T> reqCls = getRequestClass();
        if (reqCls != null) {
            reqObj = serializer.fromJson(request.body(), reqCls);
        }
        Object res = getServiceResponse(dataAccess, reqObj, token);
        response.status(200);

        return serializer.toJson(res);
    }

    protected abstract Class<T> getRequestClass();

    protected abstract Object getServiceResponse(DataAccess dataAccess, T request, String token) throws DataAccessException;
}
