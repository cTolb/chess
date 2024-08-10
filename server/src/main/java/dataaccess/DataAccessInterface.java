package dataaccess;

public interface DataAccessInterface {
    AuthDaoInterface getAuthDao();
    UserDaoInterface getUserDao();
    GameDaoInterface getGameDao();
}
