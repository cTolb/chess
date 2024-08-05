package dataaccess.memory;

import dataaccess.AuthDao;
import dataaccess.DataAccess;
import dataaccess.GameDao;
import dataaccess.UserDao;
import model.AuthData;

public class MemDataAccess implements DataAccess {

    private final MemAuthDao authDao;
    private final MemUserDao userDao;
    private final MemGameDao gameDao;

    public MemDataAccess() {
        authDao = new MemAuthDao();
        userDao = new MemUserDao();
        gameDao = new MemGameDao();
    }

    @Override
    public AuthDao getAuthDao() {
        return authDao;
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public GameDao getGameDao() {
        return gameDao;
    }
}
