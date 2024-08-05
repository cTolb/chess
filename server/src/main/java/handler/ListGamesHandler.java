package handler;

import dataaccess.DataAccess;
import model.GameData;

public class ListGamesHandler extends RequestHandler<Void>{
    public ListGamesHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, Void request, String authToken) {
        return null;
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }
}
