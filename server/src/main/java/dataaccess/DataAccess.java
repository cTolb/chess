package dataaccess;

import dataaccess.memory.MemoryAuthDao;
import dataaccess.memory.MemoryGameDao;
import dataaccess.memory.MemoryUserDao;

public class DataAccess {
    private final MemoryAuthDao memoryAuthDao;
    private final MemoryUserDao memoryUserDao;
    private final MemoryGameDao memoryGameDao;

    public DataAccess() {
        memoryAuthDao = new MemoryAuthDao();
        memoryUserDao = new MemoryUserDao();
        memoryGameDao = new MemoryGameDao();
    }


    public MemoryAuthDao getAuthDao() {
        return memoryAuthDao;
    }

    public MemoryUserDao getUserDao() {
        return memoryUserDao;
    }

    public MemoryGameDao getGameDao() {
        return memoryGameDao;
    }
}
