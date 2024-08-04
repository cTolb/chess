package dataaccess;

public interface DataAccess {
    AuthDao getAuthDao();
    GameDao getGameDao();
    UserDao getUserDao();
}
