package internetshop.dao.jdbc;

import internetshop.dao.ProductDao;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
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
public class ProductDaoJdbcImpl implements ProductDao {
    private static final Logger LOGGER = Logger.getLogger(ProductDaoJdbcImpl.class);

    @Override
    public Product create(Product product) {
        String query = "INSERT INTO products (name, price) VALUES (?, ?)";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                product.setId(resultSet.getLong(1));
            }

            LOGGER.info("The product " + product.getId() + " created");
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Product isn't created", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        Optional<Product> optional = Optional.empty();
        String query = "SELECT id, name, price FROM products WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                Product product = new Product(name, price);
                product.setId(id);
                optional = Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Product isn't got", e);
        }

        return optional;
    }

    @Override
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT id, name, price FROM products";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");

                Product product = new Product(name, price);
                product.setId(id);
                list.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Products aren't gotten", e);
        }

        return list;
    }

    @Override
    public Product update(Product product) {
        String query = "UPDATE products SET name = ?, price = ? WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(1, product.getId());
            statement.executeUpdate();
            LOGGER.info("The product " + product.getId() + " is updated");
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Product isn't updated", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM products WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            if (statement.execute()) {
                LOGGER.info("The product " + id + " is deleted");
                return true;
            } else {
                LOGGER.info("The product " + id + " isn't deleted");
                return false;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Product isn't deleted", e);
        }
    }
}
