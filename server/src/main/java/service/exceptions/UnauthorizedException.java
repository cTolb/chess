package service.exceptions;

public class UnauthorizedException extends ServerException {

    public UnauthorizedException(String message) {
        super(message);
    }
}