package dataaccess.sql;

import dataaccess.DataAccessException;
import dataaccess.UserDaoInterface;
import model.UserData;
import org.mindrot.jbcrypt.BCrypt;
import requests.LoginRequest;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUserDao implements UserDaoInterface {
    @Override
    public void clear() throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "TRUNCATE TABLE users;";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void addUser(UserData user) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?);";

            var prepStatement = con.prepareStatement(statement);
            prepStatement.setString(1, user.username());
            prepStatement.setString(2, user.password());
            prepStatement.setString(3, user.email());

            prepStatement.executeUpdate();
            con.close();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "SELECT * FROM users WHERE username=?";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.setString(1, username);
            try (var returnStatement = prepStatement.executeQuery()) {
                if (returnStatement.next()) {
                    return readUser(returnStatement);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    private UserData readUser(ResultSet rs) throws DataAccessException{
        try {
            var username = rs.getString("username");
            var password = rs.getString("password");
            var email = rs.getString("email");
            return new UserData(username, password, email);
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public boolean valid(LoginRequest request) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "SELECT * FROM users WHERE username=?";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.setString(1, request.username());
            try (var returnStatement = prepStatement.executeQuery()) {
                if (returnStatement.next()) {
                    UserData found = readUser(returnStatement);
                    String hashedPassword = found.password();
                    return found != null && checkPassword(request.password(), hashedPassword);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return false;
    }

    private boolean checkPassword(String regularPassword, String hashedPassword) {
        return BCrypt.checkpw(regularPassword, hashedPassword);
    }

    @Override
    public boolean userExists(String username) throws DataAccessException {
        var con = DatabaseManager.getConnection();
        try {
            String statement = "SELECT * FROM users WHERE username=?";
            var prepStatement = con.prepareStatement(statement);
            prepStatement.setString(1, username);
            try (var returnStatement = prepStatement.executeQuery()) {
                return returnStatement.next();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
