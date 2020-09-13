package internetshop.dao.jdbc;

import internetshop.dao.ShoppingCartDao;
import internetshop.dao.impl.ShoppingCartDaoImpl;
import internetshop.exceptions.DataProcessingException;
import internetshop.lib.Dao;
import internetshop.model.Product;
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
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    static final Logger LOGGER = Logger.getLogger(ShoppingCartDaoJdbcImpl.class);

    private List<Product> getCartProducts(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            String query = "SELECT * FROM products JOIN shopping_carts_products ON "
                    + "products.product_id = shopping_carts_products.product_id "
                    + "WHERE shopping_carts_products.cart_id = ?";
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
            throw new DataProcessingException("Products haven't found for cart " + id, e);
        }
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "DELETE FROM shopping_carts_products WHERE cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCart.getId());
            statement.executeUpdate();

            LOGGER.info("The shopping cart " + shoppingCart.getId() + " has cleared");
        } catch (SQLException e) {
            LOGGER.info("The shopping cart " + shoppingCart.getId() + " hasn't cleared");
        }
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long userId) {
        Optional<ShoppingCart> optional = Optional.empty();
        String query = "SELECT * FROM shopping_carts WHERE user_id = ?";
        ShoppingCart shoppingCart = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("cart_id");
                shoppingCart = new ShoppingCart(new ArrayList<Product>(), userId);
                shoppingCart.setId(id);
                optional = Optional.of(shoppingCart);
                LOGGER.info("The shopping cart for user " + userId + " has found");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("The shopping cart for user " + userId
                    + " hasn't found", e);
        }

        if (shoppingCart != null) {
            List<Product> products = getCartProducts(userId);
            shoppingCart.setProducts(products);
        }

        return optional;
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            String query = "INSERT INTO shopping_carts (user_id) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long cartId = resultSet.getLong(1);
                shoppingCart.setId(cartId);
                LOGGER.info("The shopping cart " + shoppingCart.getId() + " created");
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("The shopping cart " + shoppingCart.getId()
                    + " didn't create", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        Optional<ShoppingCart> optional = Optional.empty();
        String query = "SELECT * FROM shopping_carts WHERE cart_id = ?";
        ShoppingCart shoppingCart = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                shoppingCart = new ShoppingCart(new ArrayList<Product>(), userId);
                shoppingCart.setId(id);
                optional = Optional.of(shoppingCart);
                LOGGER.info("The shopping cart " + id + " founded");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("The shopping cart " + id
                    + " didn't found", e);
        }

        if (shoppingCart != null) {
            List<Product> products = getCartProducts(shoppingCart.getUserId());
            shoppingCart.setProducts(products);
        }

        return optional;
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            List<ShoppingCart> list = new ArrayList<>();

            String query = "SELECT * FROM shopping_carts";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long cartId = resultSet.getLong("cart_id");
                Long userId = resultSet.getLong("user_id");

                ShoppingCart shoppingCart = new ShoppingCart(new ArrayList<Product>(), userId);
                shoppingCart.setId(cartId);
                list.add(shoppingCart);
                LOGGER.info("The shopping cart " + cartId + " has gotten");
            }

            return list;
        } catch (SQLException e) {
            throw new DataProcessingException("Shopping carts haven't gotten", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            Long cartId = shoppingCart.getId();

            connection.setAutoCommit(false);

            String removeQuery = "DELETE FROM shopping_carts_products WHERE cart_id = ?";
            PreparedStatement removeProductsStatement = connection
                    .prepareStatement(removeQuery);
            removeProductsStatement.setLong(1, cartId);
            removeProductsStatement.executeUpdate();

            String linkQuery = "INSERT INTO shopping_carts_products(cart_id, product_id) VALUES (?, ?)";
            for (Product product: shoppingCart.getProducts()) {
                PreparedStatement linkProductStatement = connection
                        .prepareStatement(linkQuery);
                linkProductStatement.setLong(1, cartId);
                linkProductStatement.setLong(2, product.getId());
                linkProductStatement.executeUpdate();
            }

            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.info("The shopping cart " + cartId + " updated");
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("The shopping cart " + shoppingCart.getId()
                    + " didn't update", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        boolean isDeleted = true;

        String query;
        try (Connection connection = ConnectionUtil.getConnection()) {
            connection.setAutoCommit(false);

            query = "DELETE FROM shopping_carts_products WHERE cart_id = ?";
            PreparedStatement removeCartProductsStatement = connection
                    .prepareStatement(query);
            removeCartProductsStatement.setLong(1, id);

            if (removeCartProductsStatement.executeUpdate() > 0) {
                query = "DELETE FROM shopping_carts WHERE cart_id = ?";
                PreparedStatement removeCartStatement = connection.prepareStatement(query);
                removeCartStatement.setLong(1, id);
                removeCartStatement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);

            LOGGER.info("The shopping cart " + id + " deleted");
        } catch (SQLException e) {
            LOGGER.info("The shopping cart " + id + " didn't delete");
            isDeleted = false;
        }

        return isDeleted;
    }
}
