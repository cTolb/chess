package service;

public class ExceptionClass extends Exception{
    final private int errorCode;
    public ExceptionClass(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int StatusCode() {
        return errorCode;
    }
}
