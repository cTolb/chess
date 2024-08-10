package dataaccess.sql;

import dataaccess.*;

public class SQLDataAccess implements DataAccess {
    private final AuthDaoInterface authDao;
    private final UserDaoInterface userDao;
    private final GameDaoInterface gameDao;

    public SQLDataAccess() {
            authDao = new SQLAuthDao();
            userDao = new SQLUserDao();
            gameDao = new SQLGameDao();
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
