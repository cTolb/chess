package service;

import dataaccess.DataAccess;
import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import responses.ClearResponse;

public class ClearService {
    private final DataAccess dataAccess;
    public ClearService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public ClearResponse clear() throws DataAccessException {
        try {
            dataAccess.getAuthDao().clear();
            dataAccess.getUserDao().clear();
            dataAccess.getGameDao().clear();
            return new ClearResponse(null);
        } catch (DataAccessException e) {
            return new ClearResponse(e.getMessage());
        }
    }
}
