package internetshop.dao.jdbc;

import internetshop.dao.OrderDao;
import internetshop.dao.ShoppingCartDao;
import internetshop.dao.UserDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.lib.Inject;
import internetshop.model.Order;
import internetshop.model.Role;
import internetshop.model.ShoppingCart;
import internetshop.model.User;
import internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;
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

    @Inject
    OrderDao orderDao;

    @Inject
    ShoppingCartDao cartDao;

    private Set<Role> getUserRoles(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Set<Role> roles = new HashSet<>();
            String query = "SELECT * FROM roles JOIN users_roles ON "
                    + "roles.role_id = users_roles.role_id WHERE users_roles.user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("role_id");
                String roleName = resultSet.getString("name");
                Role role = Role.of(roleName);
                role.setId(roleId);
                roles.add(role);
            }

            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Roles aren't found for user " + id, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String name = resultSet.getString("name");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");

        User user = new User(name, login, password);
        user.setId(userId);

        return user;
    }

    @Override
    public Optional<User> getByLogin(String login) {
        Optional<User> optional = Optional.empty();
        String query = "SELECT * FROM users WHERE login = ?";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
                optional = Optional.of(user);
                LOGGER.info("The user " + user.getId() + " is found");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't found", e);
        }

        if (user != null) {
            Set<Role> roles = getUserRoles(user.getId());
            user.setRoles(roles);
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
        String query = "SELECT * FROM users WHERE user_id = ?";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
                optional = Optional.of(user);
                LOGGER.info("The user " + user.getId() + " is found");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("User isn't found", e);
        }

        if (user != null) {
            Set<Role> roles = getUserRoles(user.getId());
            user.setRoles(roles);
        }

        return optional;
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();

        String query = "SELECT * FROM users";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                list.add(user);
                LOGGER.info("The user " + user.getId() + " is gotten");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Users aren't gotten", e);
        }

        return list;
    }

    @Override
    public User update(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Long userId = user.getId();

            connection.setAutoCommit(false);

            String query = "UPDATE users SET name = ?, login = ?, password = ? WHERE user_id = ?";
            PreparedStatement updateUserStatement = connection.prepareStatement(query);
            updateUserStatement.setString(1, user.getName());
            updateUserStatement.setString(2, user.getLogin());
            updateUserStatement.setString(3, user.getPassword());
            updateUserStatement.setLong(4, userId);

            if (updateUserStatement.executeUpdate() > 0) {
                String removeQuery = "DELETE FROM users_roles WHERE user_id = ?";
                PreparedStatement removeUserRoleStatement = connection
                        .prepareStatement(removeQuery);
                removeUserRoleStatement.setLong(1, userId);
                removeUserRoleStatement.executeUpdate();

                String linkQuery = "INSERT INTO users_roles(user_id, role_id) VALUES (?, ?)";
                Set<Role> roles = user.getRoles();
                for (Role role: roles) {
                    PreparedStatement linkUserRoleStatement = connection
                            .prepareStatement(linkQuery);
                    linkUserRoleStatement.setLong(1, userId);
                    linkUserRoleStatement.setLong(2, role.getId());
                    linkUserRoleStatement.executeUpdate();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.info("The user " + user.getId() + " has updated");
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("The user " + user.getId() + " hasn't updated", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        boolean isDeleted = true;

        List<Order> orders = orderDao.getUserOrders(id);
        for (Order order: orders) {
            if (!orderDao.delete(order.getId())) {
                isDeleted = false;
                break;
            }
        }

        Optional<ShoppingCart> optionalCart = cartDao.getByUserId(id);
        if (optionalCart.isPresent()) {
            ShoppingCart shoppingCart = optionalCart.get();
            if (!cartDao.delete(shoppingCart.getId())) {
                isDeleted = false;
            }
        }

        if (isDeleted) {
            String query;
            try (Connection connection = ConnectionUtil.getConnection()) {
                connection.setAutoCommit(false);

                query = "DELETE FROM users_roles WHERE user_id = ?";
                PreparedStatement removeUserRoleStatement = connection
                        .prepareStatement(query);
                removeUserRoleStatement.setLong(1, id);

                if (removeUserRoleStatement.executeUpdate() > 0) {
                    query = "DELETE FROM users WHERE user_id = ?";
                    PreparedStatement deleteUserStatement = connection.prepareStatement(query);
                    deleteUserStatement.setLong(1, id);
                    deleteUserStatement.executeUpdate();
                }
                connection.commit();
                connection.setAutoCommit(true);

                LOGGER.info("The user " + id + " has deleted");
            } catch (SQLException e) {
                LOGGER.info("The user " + id + " hasn't deleted");
                isDeleted = false;
            }
        }

        return isDeleted;
    }
}
