package handlers;

import dataaccess.DataAccess;
import model.UserData;
import service.ServerException;
import service.UserService;

public class LoginHandler extends Handler<UserData>{
    public LoginHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<UserData> getRequestClass() {
        return UserData.class;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, UserData request, String token) throws ServerException {
        return new UserService(dataAccess).loginUser(request);
    }
}
