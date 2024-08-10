package dataaccess;

public interface DataAccess {
    AuthDaoInterface getAuthDao();
    UserDaoInterface getUserDao();
    GameDaoInterface getGameDao();
}
