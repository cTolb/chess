package handler;

import dataaccess.DataAccess;
import model.UserData;
import org.eclipse.jetty.server.Authentication;
import service.UserService;


public class LoginHandler extends RequestHandler<UserData> {
    public LoginHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, UserData request, String authToken) {
        return new UserService(dataAccess).loginUser(request);
    }

    @Override
    protected Class<UserData> getRequestClass() {
        return UserData.class;
    }
}
