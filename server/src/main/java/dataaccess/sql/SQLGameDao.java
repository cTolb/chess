package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.DataAccessException;
import dataaccess.GameDaoInterface;
import model.GameData;
import model.UserData;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        var con = DatabaseManager.getConnection();
        try {
            String statement = "SELECT * FROM games WHERE gameID=?";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.setInt(1, gameID);
            try (var returnStatement = prepStatement.executeQuery()) {
                if (returnStatement.next()) {
                    return readGame(returnStatement);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }


    private GameData readGame(ResultSet rs) throws DataAccessException {
        try {
            var gameID = rs.getInt("gameID");
            var gameName = rs.getString("gameName");
            var whiteUsername = rs.getString("whiteUsername");
            var blackUsername = rs.getString("blackUsername");
            var game = new Gson().fromJson(rs.getString("game"), ChessGame.class);
            return new GameData(gameID, gameName, whiteUsername, blackUsername, game);
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public Collection<GameData> getAllGames() throws DataAccessException {
        return null;
    }

    @Override
    public int addGame(GameData game) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "INSERT INTO games (gameName, whiteUsername, " +
            "blackUsername, game) VALUES (?, ?, ?, ?);";

            int id = SQLDataAccess.executeUpdate(statement, game.gameName(), game.whiteUsername(),
                    game.blackUsername(), game.game());

            con.close();

            return id;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void updateGames(GameData game) throws DataAccessException {

    }

}
