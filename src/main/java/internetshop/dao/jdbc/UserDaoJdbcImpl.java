package internetshop.dao.jdbc;

import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Role;
import internetshop.model.User;
import internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoJdbcImpl.class);

    private Set<Role> getUserRoles(Connection connection, Long id) throws SQLException {
        String query = "SELECT * FROM roles INNER JOIN users_roles "
                + " ON roles.role_id = users_roles.role_id "
                + "WHERE users_roles.user_id = ?";

        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);

        Set<Role> roles = new HashSet<>();
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String roleName = resultSet.getString("name");
            Long roleId = resultSet.getLong("role_id");

            Role role = Role.of(roleName);
            role.setId(roleId);

            roles.add(role);
        }

        return roles;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Optional<User> optional = Optional.empty();
        String query = "SELECT * FROM users WHERE login = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);

            User user = null;
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");

                Set<Role> roles = getUserRoles(connection, userId);

                user = new User(name, login, password, roles);
                user.setId(userId);

                LOGGER.info("The user " + user.getId() + " is found");
                optional = Optional.of(user);
            }

        } catch (SQLException e) {
            throw new DataProcessingException("User isn't found", e);
        }

        return optional;
    }

    private Role createRole(Connection connection, Role.RoleName roleName)
            throws DataProcessingException {

        try {
            String query = "INSERT INTO roles(name) values(?)";
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, roleName.toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Role role = null;
            if (resultSet.next()) {
                Long roleId = resultSet.getLong(1);
                role = Role.of(roleName.toString());
                role.setId(roleId);
            }
            return role;
        } catch (SQLException e) {
            throw new DataProcessingException("Role " + roleName.toString()
                    + "isn't created", e);
        }
    }

    private Set<Role> createUserRoles(Connection connection, Role.RoleName roleName,
                                      Long userId) throws DataProcessingException {
        try {
            Set<Role> roles = new HashSet<>();

            String queryCheck = "Select * From roles WHERE name = ?";
            PreparedStatement statementCheck = connection.prepareStatement(queryCheck);
            statementCheck.setString(1, roleName.toString());
            ResultSet resultSet = statementCheck.executeQuery();
            Role role = null;
            if (resultSet.next()) {
                role = Role.of(resultSet.getString("name"));
                role.setId(resultSet.getLong("role_id"));
            } else {
                role = createRole(connection, roleName);
            }

            String querySet = "INSERT INTO users_roles(user_id, role_id) VALUES (?, ?)";
            PreparedStatement statementSet = connection.prepareStatement(querySet);
            statementSet.setLong(1, userId);
            statementSet.setLong(2, role.getId());
            statementSet.executeUpdate();

            roles.add(role);
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Roles for user " + userId
                    + "aren't set", e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (name, login, password) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long userId = resultSet.getLong(1);
                user.setId(userId);
                user.setRoles(createUserRoles(connection, Role.RoleName.USER, userId));
            }

            LOGGER.info("The user " + user.getId() + " is created");
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't created", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        Optional<User> optional = Optional.empty();
        String query = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            User user = null;
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");

                Set<Role> roles = getUserRoles(connection, id);

                user = new User(name, login, password, roles);
                user.setId(id);
            }

            LOGGER.info("The user " + user.getId() + " is found");
            optional = Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't found", e);
        }

        return optional;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM users WHERE user_id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);

            User user = null;
            ResultSet resultSet = statement.getResultSet();
            resultSet.first();
            while (resultSet.next()) {
                String login = resultSet.getString("login");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");

                Set<Role> roles = getUserRoles(connection, id);

                user = new User(name, login, password, roles);
                user.setId(id);
            }

            LOGGER.info("The user " + user.getId() + " is found");
            optional = Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't found", e);
        }

        return optional;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
