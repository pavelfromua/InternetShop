package internetshop.dao;

import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart); //remove all products from the shoppingCart

    Optional<ShoppingCart> getByUserId(Long userId);

    boolean delete(ShoppingCart shoppingCart);
}
