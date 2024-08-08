package handlers;

import com.google.gson.Gson;
import spark.ExceptionHandler;
import spark.Request;
import spark.Response;

import java.util.Map;

public class ServerExceptionHandler<T extends Exception> implements ExceptionHandler<T> {
    private final int serverCode;

    public ServerExceptionHandler(int serverCode) {
        this.serverCode = serverCode;
    }

    @Override
    public void handle(T t, Request request, Response response) {
        if (t.getCause() != null) {
            t.printStackTrace();
        }
        response.status(serverCode);
        response.body(new Gson().toJson(Map.of("message",t.getMessage())));
    }
}
