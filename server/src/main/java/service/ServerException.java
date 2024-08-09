package service;

public class ServerException extends Exception {

    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable message) {
        super(message);
    }
}
