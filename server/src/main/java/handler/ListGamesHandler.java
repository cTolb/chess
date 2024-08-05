package handler;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.GameData;
import service.GameService;
import service.ServerException;

public class ListGamesHandler extends RequestHandler<Void>{
    public ListGamesHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, Void request, String authToken) throws ServerException {
        return new GameService(dataAccess).listGames(authToken);
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }
}
