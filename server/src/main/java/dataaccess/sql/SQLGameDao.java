package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.GameDaoInterface;
import model.GameData;

import java.sql.SQLException;
import java.util.Collection;

public class SQLGameDao implements GameDaoInterface {
    @Override
    public void clear() throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "TRUNCATE TABLE games;";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> getAllGames() throws DataAccessException {
        return null;
    }

    @Override
    public int addGame(GameData game) throws DataAccessException {
        return 0;
    }

    @Override
    public void updateGames(GameData game) throws DataAccessException {

    }
}
