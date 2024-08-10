package handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import responses.ClearResponse;
import service.ClearService;
import spark.Request;
import spark.Response;
import spark.Route;

public class ClearHandler implements Route {
    private final DataAccess dataAccess;

    public ClearHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    @Override
    public Object handle(Request request, Response response) throws DataAccessException {
        Gson gson = new Gson();

        ClearResponse responseObject = new ClearService(dataAccess).clear();
        if (responseObject.message() == null) {
            response.status(200);
        }
        else {
            response.status(500);
        }

        return gson.toJson(responseObject);
    }
}
