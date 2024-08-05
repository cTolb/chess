package handler;

import dataaccess.DataAccess;

public class ClearHandler extends RequestHandler<Void>{
    public ClearHandler(DataAccess dataAccess) {
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
