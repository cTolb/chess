package handlers;

import dataaccess.DataAccess;
import model.GameData;
import service.GameService;
import service.exceptions.ServerException;

public class CreateHandler extends Handler<GameData>{

    public CreateHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<GameData> getRequestClass() {
        return GameData.class;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, GameData request, String token) throws ServerException {
        return new GameService(dataAccess).createGame(request, token);
    }
}
