package handler;

import dataaccess.DataAccess;
import service.ClearService;
import service.ServerException;

public class ClearHandler extends RequestHandler<Void>{
    public ClearHandler(DataAccess dataAccess) {
        super(dataAccess);
    }

    @Override
    protected Object getResult(DataAccess dataAccess, Void request, String authToken) throws ServerException {
        new ClearService(dataAccess).clear();
        return null;
    }

    @Override
    protected Class<Void> getRequestClass() {
        return null;
    }
}
