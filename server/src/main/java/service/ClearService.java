package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;

public class ClearService {
    private DataAccess dataAccess;

    public ClearService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public void clear() throws ServerException {
        try {
            dataAccess.getUserDao().clear();
            dataAccess.getAuthDao().clear();
            dataAccess.getGameDao().clear();
        } catch (DataAccessException e) {
            throw new ServerException(e);
        }
    }
}
