package dataaccess.sql;

import dataaccess.AuthDaoInterface;
import dataaccess.DataAccessException;
import model.AuthData;

import javax.xml.crypto.Data;
import java.sql.*;

public class SQLAuthDao implements AuthDaoInterface {
    @Override
    public void clear() throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "TRUNCATE TABLE auths;";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void addAuth(AuthData authData) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "INSERT INTO auths (authToken, username) VALUES (?, ?);";

            var prepStatement = con.prepareStatement(statement);

            prepStatement.setString(1, authData.authToken());
            prepStatement.setString(2, authData.username());
            prepStatement.executeUpdate();

            con.close();

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public AuthData getAuthorization(String authToken) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "SELECT * FROM auths WHERE authToken = ?;";

            var prepStatement = con.prepareStatement(statement);

            prepStatement.setString(1, authToken);
            var rs = prepStatement.executeQuery();
            String token = null;
            String username = null;
            while (rs.next()) {
                token = rs.getString("authToken");
                username = rs.getString("username");
            }

            con.close();

            if (token == null) {
                return null;
            }
            return new AuthData(token, username);

        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void deleteAuth(String authToken) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "DELETE FROM auths WHERE authToken = ?;";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.setString(1, authToken);
            prepStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
