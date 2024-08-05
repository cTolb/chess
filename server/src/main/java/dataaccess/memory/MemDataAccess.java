package dataaccess.memory;

import dataaccess.AuthDao;
import dataaccess.DataAccess;
import dataaccess.GameDao;
import dataaccess.UserDao;

public class MemDataAccess implements DataAccess {
    private final AuthDao authDao;
    private final GameDao gameDao;
    private final UserDao userDao;

    public MemDataAccess() {
        authDao = new MemAuthDao();
        gameDao = new MemGameDao();
        userDao = new MemUserDao();
    }
    @Override
    public AuthDao getAuthDao() {
        return authDao;
    }

    @Override
    public GameDao getGameDao() {
        return gameDao;
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }
}
