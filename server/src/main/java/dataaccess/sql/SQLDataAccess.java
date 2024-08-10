package dataaccess.sql;

import dataaccess.AuthDaoInterface;
import dataaccess.DataAccessInterface;
import dataaccess.GameDaoInterface;
import dataaccess.UserDaoInterface;
import model.GameData;
import model.UserData;

public class SQLDataAccess implements DataAccessInterface {
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
