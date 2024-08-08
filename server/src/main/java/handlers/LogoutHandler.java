package handlers;

import dataaccess.DataAccess;
import service.exceptions.ServerException;
import service.UserService;

public class LogoutHandler extends Handler<Void>{
    public LogoutHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, Void request, String token) throws ServerException {
        new UserService(dataAccess).logoutUser(token);
        return null;
    }
}
