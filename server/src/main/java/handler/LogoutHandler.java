package handler;

import dataaccess.DataAccess;
import model.UserData;
import service.ServerException;
import service.UserService;

public class LogoutHandler extends RequestHandler<Void>{
    public LogoutHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    //finish
    protected Object getResult(DataAccess dataAccess, Void request, String authToken) throws ServerException {
        new UserService(dataAccess).logout(authToken);
        return "";
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }
}
