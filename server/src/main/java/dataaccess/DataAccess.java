package dataaccess;

public class DataAccess {
    private final AuthDao authDao;
    private final UserDao userDao;
    private final GameDao gameDao;

    public DataAccess() {
        authDao = new AuthDao();
        userDao = new UserDao();
        gameDao = new GameDao();
    }


    public AuthDao getAuthDao() {
        return authDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public GameDao getGameDao() {
        return gameDao;
    }
}
