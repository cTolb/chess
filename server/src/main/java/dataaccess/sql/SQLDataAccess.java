package dataaccess.sql;

import chess.ChessGame;
import com.google.gson.Gson;
import dataaccess.*;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static dataaccess.sql.DatabaseManager.getConnection;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLDataAccess implements DataAccess {
    private final AuthDaoInterface authDao;
    private final UserDaoInterface userDao;
    private final GameDaoInterface gameDao;

    public SQLDataAccess() throws DataAccessException {
            authDao = new SQLAuthDao();
            userDao = new SQLUserDao();
            gameDao = new SQLGameDao();
            configureDatabase();
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

    public static int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (var con = DatabaseManager.getConnection()) {
            try (var prepStatement = con.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    switch (param) {
                        case String p -> prepStatement.setString(i + 1, p);
                        case Integer p -> prepStatement.setInt(i + 1, p);
                        case ChessGame p -> prepStatement.setString(i + 1, new Gson().toJson(p));
                        case null -> prepStatement.setNull(i + 1, Types.NULL);
                        default -> throw new DataAccessException("Unexpected datatype: " + param.getClass());
                    }
                }

                prepStatement.executeUpdate();

                var rs = prepStatement.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 0;
                }

            }
        } catch (SQLException e) {
            throw new DataAccessException(String.format("executeUpdate error: %s, %s", statement, e.getMessage()));
        }
    }

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var con = getConnection()){
            for (var statement : getStatement()) {
                try (var prepStatement = con.prepareStatement(statement)) {
                    prepStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    private String[] getStatement() {
        String userString = "CREATE TABLE IF NOT EXISTS users (" +
                "username VARCHAR(250) NOT NULL, " +
                "password VARCHAR(250) NOT NULL, " +
                "email VARCHAR(250) NOT NULL, " +
                "PRIMARY KEY (username));";

        String authString = "CREATE TABLE IF NOT EXISTS auths (" +
                "authToken VARCHAR(250) NOT NULL, " +
                "username VARCHAR(250) NOT NULL, " +
                "PRIMARY KEY (authToken));";

        String gamesString = "CREATE TABLE IF NOT EXISTS games (" +
                "gameID INT NOT NULL AUTO_INCREMENT, " +
                "gameName VARCHAR(250) NOT NULL, " +
                "whiteUsername VARCHAR(250), " +
                "blackUsername VARCHAR(250), " +
                "game LONGTEXT NOT NULL, " +
                "PRIMARY KEY (gameID));";

        String[] statements = new String[3];

        statements[0] = userString;
        statements[1] = authString;
        statements[2] = gamesString;

        return statements;
    }
}
