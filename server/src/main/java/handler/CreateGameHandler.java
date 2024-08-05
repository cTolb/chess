package handler;

import dataaccess.DataAccess;
import model.GameData;
import service.GameService;
import service.ServerException;

public class CreateGameHandler extends RequestHandler<GameData>{
    public CreateGameHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, GameData request, String authToken) throws ServerException {
        return new GameService(dataAccess).createGame(authToken, request.gameName());
    }

    @Override
    protected Class<GameData> getRequestClass() {
        return GameData.class;
    }
}
