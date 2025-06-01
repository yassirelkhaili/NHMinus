package de.hitec.nhplus.datastorage;

import de.hitec.nhplus.model.User;
import de.hitec.nhplus.utils.HashPassword;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Interface <code>DaoImp</code>. Overrides methods to generate
 * specific <code>PreparedStatements</code>,
 * to execute the specific SQL Statements for User operations.
 */
public class UserDao extends DaoImp<User> {

    /**
     * The constructor initiates an object of <code>UserDao</code> and passes the
     * connection to its super class.
     *
     * @param connection Object of <code>Connection</code> to execute the
     *                   SQL-statements.
     */
    public UserDao(Connection connection) {
        super(connection);
    }

    /**
     * Generates a <code>PreparedStatement</code> to persist the given object of
     * <code>User</code>.
     *
     * @param user Object of <code>User</code> to persist.
     * @return <code>PreparedStatement</code> to insert the given user.
     */
    @Override
    protected PreparedStatement getCreateStatement(User user) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "INSERT INTO users (first_name, surname, username, email, password_hash, role, is_active, created_at, updated_at) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPasswordHash());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setBoolean(7, user.isActive());
            preparedStatement.setString(8, user.getCreatedAt().toString());
            preparedStatement.setString(9, user.getUpdatedAt().toString());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query a user by a given user id
     * (uid).
     *
     * @param uid User id to query.
     * @return <code>PreparedStatement</code> to query the user.
     */
    @Override
    protected PreparedStatement getReadByIDStatement(long uid) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM users WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, uid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Maps a <code>ResultSet</code> of one user to an object of <code>User</code>.
     *
     * @param result ResultSet with a single row. Columns will be mapped to an
     *               object of class <code>User</code>.
     * @return Object of class <code>User</code> with the data from the resultSet.
     */
    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDateTime createdAt = LocalDateTime.parse(result.getString(9));
        LocalDateTime updatedAt = LocalDateTime.parse(result.getString(10));
        return new User(
                result.getInt(1), // uid
                result.getString(2), // first_name
                result.getString(3), // surname
                result.getString(4), // username
                result.getString(5), // email
                result.getString(6), // password_hash
                result.getString(7), // role
                result.getBoolean(8), // is_active
                createdAt, // created_at
                updatedAt // updated_at
        );
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all users.
     *
     * @return <code>PreparedStatement</code> to query all users.
     */
    @Override
    protected PreparedStatement getReadAllStatement() {
        PreparedStatement statement = null;
        try {
            final String SQL = "SELECT * FROM users WHERE is_active = 1 ORDER BY surname, first_name";
            statement = this.connection.prepareStatement(SQL);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return statement;
    }

    /**
     * Maps a <code>ResultSet</code> of all users to an <code>ArrayList</code> with
     * objects of class
     * <code>User</code>.
     *
     * @param result ResultSet with all rows. The columns will be mapped to objects
     *               of class <code>User</code>.
     * @return <code>ArrayList</code> with objects of class <code>User</code> of all
     *         rows in the
     *         <code>ResultSet</code>.
     */
    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<User>();
        while (result.next()) {
            LocalDateTime createdAt = LocalDateTime.parse(result.getString(9));
            LocalDateTime updatedAt = LocalDateTime.parse(result.getString(10));
            User user = new User(
                    result.getInt(1), // uid
                    result.getString(2), // first_name
                    result.getString(3), // surname
                    result.getString(4), // username
                    result.getString(5), // email
                    result.getString(6), // password_hash
                    result.getString(7), // role
                    result.getBoolean(8), // is_active
                    createdAt, // created_at
                    updatedAt // updated_at
            );
            list.add(user);
        }
        return list;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query a user by username.
     *
     * @param username Username to query.
     * @return <code>PreparedStatement</code> to query the user by username.
     */
    private PreparedStatement getReadByUsernameStatement(String username) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM users WHERE username = ? AND is_active = 1";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, username);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Queries a user by username and maps the result to an object of class
     * <code>User</code>.
     *
     * @param username Username to query.
     * @return Object of class <code>User</code> or null if not found.
     */
    public User readByUsername(String username) throws SQLException {
        ResultSet result = getReadByUsernameStatement(username).executeQuery();
        if (result.next()) {
            return getInstanceFromResultSet(result);
        }
        return null;
    }

    /**
     * Generates a <code>PreparedStatement</code> to query all users by role.
     *
     * @param role Role to query users for.
     * @return <code>PreparedStatement</code> to query all users of the given role.
     */
    private PreparedStatement getReadAllUsersByRoleStatement(String role) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT * FROM users WHERE role = ? AND is_active = 1 ORDER BY surname, first_name";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, role);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Queries all users of a given role and maps the results to an
     * <code>ArrayList</code> with
     * objects of class <code>User</code>.
     *
     * @param role Role to query all users for.
     * @return <code>ArrayList</code> with objects of class <code>User</code> of all
     *         rows in the
     *         <code>ResultSet</code>.
     */
    public List<User> readUsersByRole(String role) throws SQLException {
        ResultSet result = getReadAllUsersByRoleStatement(role).executeQuery();
        return getListFromResultSet(result);
    }

    /**
     * Authenticates a user with username and password.
     *
     * @param username Username for authentication.
     * @param password Plain text password for authentication.
     * @return User object if authentication successful, null otherwise.
     */
    public User authenticate(String username, String password) throws SQLException {
        User user = readByUsername(username);
        if (user != null && HashPassword.verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }

    /**
     * Checks if a username already exists in the database.
     *
     * @param username Username to check.
     * @return true if username exists, false otherwise.
     */
    public boolean usernameExists(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "SELECT COUNT(*) FROM users WHERE username = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Generates a <code>PreparedStatement</code> to update the given user,
     * identified
     * by the id of the user (uid).
     *
     * @param user User object to update.
     * @return <code>PreparedStatement</code> to update the given user.
     */
    @Override
    protected PreparedStatement getUpdateStatement(User user) {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "UPDATE users SET " +
                    "first_name = ?, " +
                    "surname = ?, " +
                    "username = ?, " +
                    "email = ?, " +
                    "password_hash = ?, " +
                    "role = ?, " +
                    "is_active = ?, " +
                    "updated_at = ? " +
                    "WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPasswordHash());
            preparedStatement.setString(6, user.getRole());
            preparedStatement.setBoolean(7, user.isActive());
            preparedStatement.setString(8, LocalDateTime.now().toString());
            preparedStatement.setLong(9, user.getUid());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Updates user password with proper hashing.
     *
     * @param uid         User ID to update password for.
     * @param newPassword New plain text password.
     * @return true if update successful, false otherwise.
     */
    public boolean updatePassword(long uid, String newPassword) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "UPDATE users SET password_hash = ?, updated_at = ? WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, HashPassword.hashPassword(newPassword));
            preparedStatement.setString(2, LocalDateTime.now().toString());
            preparedStatement.setLong(3, uid);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    /**
     * Generates a <code>PreparedStatement</code> to delete a user with the given
     * id.
     * Note: This performs a soft delete by setting is_active to false.
     *
     * @param uid Id of the User to delete.
     * @return <code>PreparedStatement</code> to delete user with the given id.
     */
    @Override
    protected PreparedStatement getDeleteStatement(long uid) {
        PreparedStatement preparedStatement = null;
        try {
            // Soft delete - set is_active to false instead of hard delete
            final String SQL = "UPDATE users SET is_active = 0, updated_at = ? WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setString(1, LocalDateTime.now().toString());
            preparedStatement.setLong(2, uid);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return preparedStatement;
    }

    /**
     * Permanently deletes a user from the database (hard delete).
     * Use with caution - this cannot be undone.
     *
     * @param uid Id of the User to permanently delete.
     * @return true if deletion successful, false otherwise.
     */
    public boolean hardDelete(long uid) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            final String SQL = "DELETE FROM users WHERE uid = ?";
            preparedStatement = this.connection.prepareStatement(SQL);
            preparedStatement.setLong(1, uid);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}