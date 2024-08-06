package server.handlers;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import server.service.ClearService;


public class ClearHandler extends Handler<Void> {

    public ClearHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, Void request, String token) throws DataAccessException {
        new ClearService(dataAccess).clear();
        return null;
    }
}
