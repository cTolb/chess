package server.service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;

public class ClearService {
    private final DataAccess dataAccess;
    public ClearService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void clear() throws DataAccessException{
        dataAccess.getAuthDao().clear();
        dataAccess.getUserDao().clear();
        dataAccess.getGameDao().clear();
    }
}
