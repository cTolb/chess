package dataaccess.memory;

import dataaccess.AuthDaoInterface;
import dataaccess.DataAccess;
import dataaccess.GameDaoInterface;
import dataaccess.UserDaoInterface;

public class MemoryDataAccess implements DataAccess {
    private final AuthDaoInterface authDao;
    private final UserDaoInterface userDao;
    private final GameDaoInterface gameDao;

    public MemoryDataAccess() {
        authDao = new MemoryAuthDao();
        userDao = new MemoryUserDao();
        gameDao = new MemoryGameDao();
    }

    @Override
    public AuthDaoInterface getAuthDao() {
        return authDao;
    }

    @Override
    public UserDaoInterface getUserDao() {
        return userDao;
    }

    @Override
    public GameDaoInterface getGameDao() {
        return gameDao;
    }
}
