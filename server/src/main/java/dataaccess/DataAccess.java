package dataaccess;

public interface DataAccess {
    AuthDao getAuthDao();
    UserDao getUserDao();
    GameDao getGameDao();
}
