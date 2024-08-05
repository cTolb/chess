package handler;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.UserData;
import service.UserService;

import java.rmi.ServerException;

public class RegisterHandler extends RequestHandler<UserData>{

    public RegisterHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, UserData request, String authToken) {
        return new UserService(dataAccess).registerUser(request);
    }

    @Override
    protected Class<UserData> getRequestClass() {
        return UserData.class;
    }
}
