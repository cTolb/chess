package server.handlers;

import com.google.gson.Gson;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.UserDao;
import model.AuthData;
import model.RegisterResponse;
import model.UserData;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.RegexConversion;
import server.service.ServerException;
import server.service.UserService;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.xml.crypto.Data;
import java.net.HttpURLConnection;

public class RegisterHandler extends Handler<UserData> {

    public RegisterHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<UserData> getRequestClass() {
        return UserData.class;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, UserData request, String token) throws DataAccessException {
        return new UserService(dataAccess).registerUser(request);
    }

}
