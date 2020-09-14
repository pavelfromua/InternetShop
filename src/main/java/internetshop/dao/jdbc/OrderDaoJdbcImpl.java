package internetshop.dao.jdbc;

import internetshop.dao.OrderDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Order;
import internetshop.model.Product;
import internetshop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    static final Logger LOGGER = Logger.getLogger(OrderDaoJdbcImpl.class);

    private List<Product> getOrderProducts(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            String query = "SELECT * FROM products JOIN orders_products ON "
                    + "products.product_id = orders_products.product_id "
                    + "WHERE orders_products.order_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                Product product = new Product(name, price);
                product.setId(productId);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Products haven't found for order " + id, e);
        }
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        List<Order> orders = new ArrayList<>();

        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "SELECT * FROM orders WHERE user_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                Order order = new Order(new ArrayList<Product>(), userId);
                order.setId(orderId);

                orders.add(order);
            }

            LOGGER.info("Orders for user " + userId + " founded");
        } catch (SQLException e) {
            throw new DataProcessingException("Orders for user " + userId
                    + " didn't found", e);
        }

        return orders;
    }

    @Override
    public Order create(Order order) {
        String query;

        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);

            query = "INSERT INTO orders (user_id) VALUES (?)";
            PreparedStatement createOrderStatement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            createOrderStatement.setLong(1, order.getUserId());
            createOrderStatement.executeUpdate();
            ResultSet resultSetOrder = createOrderStatement.getGeneratedKeys();
            if (resultSetOrder.next()) {
                Long orderId = resultSetOrder.getLong(1);
                order.setId(orderId);

                String linkQuery = "INSERT INTO orders_products(order_id, product_id) "
                        + "VALUES (?, ?)";
                for (Product product: order.getProducts()) {
                    PreparedStatement linkProductStatement = connection
                            .prepareStatement(linkQuery);
                    linkProductStatement.setLong(1, orderId);
                    linkProductStatement.setLong(2, product.getId());
                    linkProductStatement.executeUpdate();
                }
            }
            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.info("The order " + order.getId() + " has created");
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("The order " + order.getId() + " hasn't created", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        Optional<Order> optional = Optional.empty();
        String query = "SELECT * FROM orders WHERE order_id = ?";
        Order order = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                order = new Order(new ArrayList<Product>(), userId);
                order.setId(id);
                optional = Optional.of(order);
                LOGGER.info("The order " + id + " founded");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("The order " + id
                    + " didn't found", e);
        }

        if (order != null) {
            List<Product> products = getOrderProducts(order.getId());
            order.setProducts(products);
        }

        return optional;
    }

    @Override
    public List<Order> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            List<Order> list = new ArrayList<>();

            String query = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long orderId = resultSet.getLong("order_id");
                Long userId = resultSet.getLong("user_id");

                Order order = new Order(new ArrayList<Product>(), userId);
                order.setId(orderId);
                list.add(order);
                LOGGER.info("The order " + orderId + " has gotten");
            }

            return list;
        } catch (SQLException e) {
            throw new DataProcessingException("Orders haven't gotten", e);
        }
    }

    @Override
    public Order update(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Long orderId = order.getId();

            connection.setAutoCommit(false);

            String removeQuery = "DELETE FROM orders_products WHERE order_id = ?";
            PreparedStatement removeProductsStatement = connection
                    .prepareStatement(removeQuery);
            removeProductsStatement.setLong(1, orderId);
            removeProductsStatement.executeUpdate();

            String linkQuery = "INSERT INTO orders_products(order_id, product_id) VALUES (?, ?)";
            for (Product product: order.getProducts()) {
                PreparedStatement linkProductStatement = connection
                        .prepareStatement(linkQuery);
                linkProductStatement.setLong(1, orderId);
                linkProductStatement.setLong(2, product.getId());
                linkProductStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.info("The order " + orderId + " updated");
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("The order " + order.getId()
                    + " didn't update", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        boolean isDeleted = true;

        String query;
        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);

            query = "DELETE FROM orders_products WHERE order_id = ?";
            PreparedStatement removeOrderProductsStatement = connection
                    .prepareStatement(query);
            removeOrderProductsStatement.setLong(1, id);
            removeOrderProductsStatement.executeUpdate();

            query = "DELETE FROM orders WHERE order_id = ?";
            PreparedStatement removeOrderStatement = connection.prepareStatement(query);
            removeOrderStatement.setLong(1, id);
            removeOrderStatement.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.info("The order " + id + " deleted");
        } catch (SQLException e) {
            LOGGER.info("The order " + id + " didn't delete");
            isDeleted = false;
        }

        return isDeleted;
    }
}
