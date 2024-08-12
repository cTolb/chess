package client;

public class ServerFacade {
    private final String serverURL;

    public ServerFacade(String url) {
        serverURL = url;
    }

    public ServerFacade(int port) {
        serverURL = "http://localhost:" + port;
    }
}
