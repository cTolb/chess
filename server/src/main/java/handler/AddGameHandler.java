package handler;

import dataaccess.DataAccess;
import model.GameData;

public class AddGameHandler extends RequestHandler<GameData>{
    public AddGameHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, GameData request, String authToken) {
        return null;
    }

    @Override
    protected Class<GameData> getRequestClass() {
        return null;
    }
}
