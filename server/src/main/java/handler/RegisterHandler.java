package handler;

import dataaccess.DataAccess;
import model.UserData;
import service.ServerException;
import service.UserService;

public class RegisterHandler extends RequestHandler<UserData>{

    public RegisterHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, UserData request, String authToken) throws ServerException {
        return new UserService(dataAccess).registerUser(request);
    }

    @Override
    protected Class<UserData> getRequestClass() {
        return UserData.class;
    }
}
