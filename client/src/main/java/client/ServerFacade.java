package client;

import com.google.gson.Gson;
import model.GameData;
import model.UserData;
import requests.JoinGameRequest;
import requests.LoginRequest;
import responses.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class ServerFacade {
    private final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }

    public ServerFacade(int port) {
        serverURL = "http://localhost:" + port;
    }

    public RegisterResponse register(UserData user) throws Exception {
        var path = "/user";
        return makeRequest("POST", path, null, user, RegisterResponse.class);
    }

    public LoginResponse login(LoginRequest user) throws Exception {
        var path = "/session";
        return makeRequest("POST", path, null, user, LoginResponse.class);
    }

    public LogoutResponse logout(String token) throws Exception {
        var path = "/session";
        return makeRequest("DELETE", path, token, null, LogoutResponse.class);
    }

    public ListGameResponse listGame(String token) throws Exception {
        var path = "/game";
        return makeRequest("GET", path, token, null, ListGameResponse.class);
    }

    public JoinGameResponse joinGame(String token, JoinGameRequest request) throws Exception {
        var path = "/game";
        return makeRequest("PUT", path, token, request, JoinGameResponse.class);
    }

    public CreateGameResponse createGame(String token, GameData data) throws Exception {
        var path = "/game";
        return makeRequest("POST", path, token, data, CreateGameResponse.class);
    }

    public ClearResponse clear() throws Exception {
        var path = "/db";
        return makeRequest("DELETE", path, null, null, ClearResponse.class);
    }

    private <T> T makeRequest(String method, String path, String header, Object request, Class<T> responseClass) throws Exception {
        try {
            URL url = (new URI(serverURL + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            writeHeader(header, http);
            writeBody(request, http);

            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
    }

    private static void writeHeader(String header, HttpURLConnection http) {
        if (header != null) {
            http.addRequestProperty("authorization", header);
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws Exception {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new Exception(String.valueOf(status));
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
