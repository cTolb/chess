package handlers;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import service.ClearService;
import service.ServerException;


public class ClearHandler extends Handler<Void> {

    public ClearHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }

    @Override
    protected Object getServiceResponse(DataAccess dataAccess, Void request, String token) throws ServerException {
        new ClearService(dataAccess).clear();
        return null;
    }
}
