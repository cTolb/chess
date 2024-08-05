package handler;

import dataaccess.DataAccess;
import model.UserData;

public class LogoutHandler extends RequestHandler<Void>{
    public LogoutHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    //finish
    protected Object getResult(DataAccess dataAccess, Void request, String authToken) {
        return null;
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }
}
