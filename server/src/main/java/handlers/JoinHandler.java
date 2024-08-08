package handlers;

import dataaccess.DataAccess;
import model.GameData;
import model.JoinGameRequest;
import service.GameService;
import service.exceptions.ServerException;

public class JoinHandler extends Handler<JoinGameRequest>{
    public JoinHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<JoinGameRequest> getRequestClass() {
        return JoinGameRequest.class;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, JoinGameRequest request, String token) throws ServerException {
        return new GameService(dataAccess).joinGame(request, token);
    }
}
