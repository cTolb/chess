package handlers;

import dataaccess.DataAccess;
import service.GameService;
import service.exceptions.ServerException;

public class ListGamesHandler extends Handler<Void>{
    public ListGamesHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, Void request, String token) throws ServerException {
        return new GameService(dataAccess).listGames(token);
    }
}
