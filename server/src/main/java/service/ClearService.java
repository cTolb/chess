package service;

import dataaccess.memory.MemoryDataAccess;
import dataaccess.DataAccessException;
import responses.ClearResponse;

public class ClearService {
    private final MemoryDataAccess memoryDataAccess;
    public ClearService(MemoryDataAccess memoryDataAccess) {
        this.memoryDataAccess = memoryDataAccess;
    }

    public ClearResponse clear() throws DataAccessException {
        try {
            memoryDataAccess.getAuthDao().clear();
            memoryDataAccess.getUserDao().clear();
            memoryDataAccess.getGameDao().clear();
            return new ClearResponse(null);
        } catch (DataAccessException e) {
            return new ClearResponse(e.getMessage());
        }
    }
}
