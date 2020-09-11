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

    @Override
    public Optional<User> getByLogin(String login) {
        Optional<User> optional = Optional.empty();
        String query;

        try (Connection connection = ConnectionUtil.getConnection()) {
            query = "SELECT users.user_id, users.name as userName, users.login, users.password, "
                    + "roles.role_id, roles.name as roleName FROM users "
                    + "INNER JOIN users_roles ON users.user_id = users_roles.user_id "
                    + "INNER JOIN roles ON users_roles.role_id = roles.role_id "
                    + "WHERE users.login = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            if (resultSet.next()) {
                    Long userId = resultSet.getLong("user_id");
                    String name = resultSet.getString("userName");
                    String password = resultSet.getString("password");
                    user = new User(name, login, password);
                    user.setId(userId);


                String roleName = resultSet.getString("roleName");
                Long roleId = resultSet.getLong("role_id");

                Role role = Role.of(roleName);
                role.setId(roleId);

                roles.add(role);
            }

//            if (roles.isEmpty()) {
//                user = null;
//            }
            if (user != null) {
                LOGGER.info("The user " + user.getId() + " is found");
                user.setRoles(roles);
            }

            optional = Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't found", e);
        }

        return optional;
    }

    @Override
    public User create(User user) {
        Role.RoleName defaultRole = Role.RoleName.USER;
        String query;

        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);

            query = "INSERT INTO users (name, login, password) VALUES (?, ?, ?)";
            PreparedStatement createUserStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            createUserStatement.setString(1, user.getName());
            createUserStatement.setString(2, user.getLogin());
            createUserStatement.setString(3, user.getPassword());
            createUserStatement.executeUpdate();
            ResultSet resultSetUser = createUserStatement.getGeneratedKeys();
            if (resultSetUser.next()) {
                Long userId = resultSetUser.getLong(1);
                user.setId(userId);

                Set<Role> roles = new HashSet<>();

                query = "Select * From roles WHERE name = ?";
                PreparedStatement getRoleStatement = connection.prepareStatement(query);
                getRoleStatement.setString(1, defaultRole.toString());
                ResultSet resultSetRole = getRoleStatement.executeQuery();

                if (resultSetRole.next()) {
                    Role role = Role.of(resultSetRole.getString("name"));
                    role.setId(resultSetRole.getLong("role_id"));

                    query = "INSERT INTO users_roles(user_id, role_id) VALUES (?, ?)";
                    PreparedStatement linkUserRoleStatement = connection.prepareStatement(query);
                    linkUserRoleStatement.setLong(1, userId);
                    linkUserRoleStatement.setLong(2, role.getId());
                    linkUserRoleStatement.executeUpdate();

                    roles.add(role);
                }

                user.setRoles(roles);
            }
            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.info("The user " + user.getId() + " is created");
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't created", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        Optional<User> optional = Optional.empty();
        String query;

        try (Connection connection = ConnectionUtil.getConnection()) {
            query = "SELECT users.user_id, users.name as userName, users.login, users.password, "
                    + "roles.role_id, roles.name as roleName FROM users "
                    + "INNER JOIN users_roles ON users.user_id = users_roles.user_id "
                    + "INNER JOIN roles ON users_roles.role_id = roles.role_id "
                    + "WHERE users.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            User user = null;
            Long currentId = 0L;
            Set<Role> roles = new HashSet<>();

            while (resultSet.next()) {
                if (currentId != id) {
                    String login = resultSet.getString("login");
                    String name = resultSet.getString("userName");
                    String password = resultSet.getString("password");
                    user = new User(name, login, password);
                    user.setId(id);

                    currentId = id;
                }
                String roleName = resultSet.getString("roleName");
                Long roleId = resultSet.getLong("role_id");

                Role role = Role.of(roleName);
                role.setId(roleId);

                roles.add(role);
            }

            if (roles.isEmpty()) {
                user = null;
            }
            if (user != null) {
                LOGGER.info("The user " + user.getId() + " is found");
                user.setRoles(roles);
            }

            optional = Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't found", e);
        }

        return optional;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String query = "SELECT users.user_id, users.name as userName, users.login, users.password, "
                + "roles.role_id, roles.name as roleName FROM users "
                + "INNER JOIN users_roles ON users.user_id = users_roles.user_id "
                + "INNER JOIN roles ON users_roles.role_id = roles.role_id";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            Long currentId = 0L;
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");

                if (currentId != userId) {
                    String login = resultSet.getString("login");
                    String name = resultSet.getString("userName");
                    String password = resultSet.getString("password");
                    User user = new User(name, login, password);
                    user.setId(userId);

                    if (!roles.isEmpty()) {
                        user.setRoles(roles);
                        roles = new HashSet<>();
                    }

                    list.add(user);
                    currentId = userId;
                }

                String roleName = resultSet.getString("roleName");
                Long roleId = resultSet.getLong("role_id");

                Role role = Role.of(roleName);
                role.setId(roleId);

                roles.add(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Users aren't gotten", e);
        }

        return list;
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
